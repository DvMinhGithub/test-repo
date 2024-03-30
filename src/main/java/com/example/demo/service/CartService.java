package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.dto.CartItemDto;
import com.example.demo.model.request.AddToCartRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.repository.*;
import com.example.demo.utils.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductAttributeRepository productAttributeRepository;

    private final Jedis jedis;

    private final ModelMapper modelMapper;

    public CartService(UserRepository userRepository, CartItemRepository cartItemRepository, ProductAttributeRepository productAttributeRepository, Jedis jedis, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.jedis = jedis;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<ResponseApi<?>> addToCart(Principal principal, AddToCartRequest addToCartRequest) {
        try {
            Optional<ProductAttribute> optionalProductAttribute = productAttributeRepository.findById(addToCartRequest.getIdProductAttribute());

            if (optionalProductAttribute.isEmpty()) {
                return new ResponseEntity<>(new ResponseApi<>("This product attribute does not exist", 404), HttpStatus.NOT_FOUND);
            }

            User user = userRepository.findByEmail(principal.getName());
            List<CartItem> listCartItem = cartItemRepository.findByUserId(user.getId());

            for (CartItem cartItem : listCartItem) {
                if (addToCartRequest.getIdProductAttribute().equals(cartItem.getProductAttribute().getId())) {
                    cartItem.setQuantity(cartItem.getQuantity() + addToCartRequest.getQuantity());
                    cartItemRepository.save(cartItem);
                    List<CartItemDto> listCartItemDto = listCartItem
                            .stream()
                            .map(cartItem1 -> {
                                CartItemDto cartItemDto = modelMapper.map(cartItem1, CartItemDto.class);
                                cartItemDto.setProductAttributeId(addToCartRequest.getIdProductAttribute());
                                return cartItemDto;
                            })
                            .toList();
                    jedis.set("User#" + user.getId(), Convert.convertToJson(listCartItemDto));
                    jedis.expire("User#" + user.getId(), 12000);
                    return new ResponseEntity<>(new ResponseApi<>("Add to cart success", 200), HttpStatus.OK);
                }
            }

            ProductAttribute productAttribute = optionalProductAttribute.get();
            CartItem cartItem = new CartItem();
            cartItem.setProductAttribute(productAttribute);
            cartItem.setQuantity(addToCartRequest.getQuantity());
            cartItem.setUser(user);
            cartItemRepository.save(cartItem);
            listCartItem.add(cartItem);
            List<CartItemDto> listCartItemDto = listCartItem
                    .stream()
                    .map(cartItem1 -> {
                        CartItemDto cartItemDto = modelMapper.map(cartItem1, CartItemDto.class);
                        cartItemDto.setProductAttributeId(addToCartRequest.getIdProductAttribute());
                        return cartItemDto;
                    })
                    .toList();
            jedis.set("User#" + user.getId(), Convert.convertToJson(listCartItemDto));
            jedis.expire("User#" + user.getId(), 12000);
            return new ResponseEntity<>(new ResponseApi<>("Add to cart success", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi<>("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseApi<List<CartItem>>> getCartInformation(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        return new ResponseEntity<>(new ResponseApi<>("Get cart success", 200, cartItems), HttpStatus.OK);
    }
}
