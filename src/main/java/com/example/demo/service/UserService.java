package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.model.request.UserRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<ResponseApi<?>> updateUser(Principal principal, UserRequest userRequest) {
        User user = userRepository.findByEmail(principal.getName());
        modelMapper.map(userRequest, user);
        userRepository.save(user);
        return new ResponseEntity<>(new ResponseApi<>("Update profile success", 200), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<User>> getProfile(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        return new ResponseEntity<>(new ResponseApi<>("Get profile success", 200, user), HttpStatus.OK);
    }
}
