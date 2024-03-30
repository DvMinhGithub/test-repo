package com.example.demo.service;

import com.example.demo.model.dto.ShopDto;
import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import com.example.demo.model.request.ShopRequest;
import com.example.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.response.ResponseApi;
import com.example.demo.repository.ShopRepository;

import java.security.Principal;
import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public ShopService(ShopRepository shopRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<ResponseApi<List<ShopDto>>> getListShops() {
        List<Shop> listShop = shopRepository.findAll();
        List<ShopDto> listShopDto = listShop
                .stream()
                .map(shop -> modelMapper.map(shop, ShopDto.class))
                .toList();
        return new ResponseEntity<>(new ResponseApi<>("Get shops success", 200, listShopDto), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<?>> createShop(Principal principal, ShopRequest shopRequest) {
        User user = userRepository.findByEmail(principal.getName());
        Shop shop = modelMapper.map(shopRequest, Shop.class);
        shop.setUser(user);
        shopRepository.save(shop);
        return new ResponseEntity<>(new ResponseApi<>("Create shops success", 201), HttpStatus.CREATED);
    }


    public ResponseEntity<ResponseApi<ShopDto>> getShopInformation(Principal principal) {
        Shop shop = shopRepository.getShopFromUserEmail(principal.getName());
        ShopDto shopDto = modelMapper.map(shop, ShopDto.class);
        return new ResponseEntity<>(new ResponseApi<>("Get shop information success", 200, shopDto), HttpStatus.OK);
    }
}
