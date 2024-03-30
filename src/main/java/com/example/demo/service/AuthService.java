package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.entity.User;
import com.example.demo.model.request.MailRequest;
import com.example.demo.model.request.VerifyUserAccountRequest;
import com.example.demo.utils.Convert;
import com.example.demo.utils.OtpGenerate;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.config.JwtUtility;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtUtility jwtUtility;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final Jedis jedis;

    private final MailService mailService;

    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtility jwtUtility, PasswordEncoder passwordEncoder, ModelMapper modelMapper, Jedis jedis, MailService mailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jedis = jedis;
        this.mailService = mailService;
    }

    public ResponseEntity<ResponseApi<?>> register(RegisterRequest registerRequest) {
        try {
            if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
                return new ResponseEntity<>(new ResponseApi<>("Email already exist", 400), HttpStatus.BAD_REQUEST);
            }

            if (userRepository.findByPhoneNumber(registerRequest.getPhoneNumber()) != null) {
                return new ResponseEntity<>(new ResponseApi<>("Phone number already exist", 400), HttpStatus.BAD_REQUEST);
            }

            String otp = OtpGenerate.generateNumberOtp(6);
            registerRequest.setOtp(otp);
            registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            MailRequest mailRequest = new MailRequest(registerRequest.getEmail(), "OTP for verification", otp);
            jedis.set(registerRequest.getEmail(), Convert.convertToJson(registerRequest));
            jedis.expire(registerRequest.getEmail(), 1200);
            mailService.sendTextMessage(mailRequest);
            return new ResponseEntity<>(new ResponseApi<>("Register success", 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi<>("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<ResponseApi<?>> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtility.generateAccessToken(loginRequest.getEmail());
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            return new ResponseEntity<>(new ResponseApi<>("Login success", 200, result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi<>("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseApi<?>> verifyUserAccount(VerifyUserAccountRequest verifyUserAccountRequest) {
        try {
            String userString = jedis.get(verifyUserAccountRequest.getEmail());
            if (userString == null) {
                return new ResponseEntity<>(new ResponseApi<>("Cannot find user register information", 410), HttpStatus.GONE);
            }
            RegisterRequest registerRequest = Convert.convertJsonToObject(userString, RegisterRequest.class);
            if (!registerRequest.getOtp().equals(verifyUserAccountRequest.getOtp())) {
                return new ResponseEntity<>(new ResponseApi<>("Your otp is incorrect", 400), HttpStatus.BAD_REQUEST);
            }
            User user = modelMapper.map(registerRequest, User.class);
            userRepository.save(user);
            jedis.del(verifyUserAccountRequest.getEmail());
            return new ResponseEntity<>(new ResponseApi<>("Verify success", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi<>("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
