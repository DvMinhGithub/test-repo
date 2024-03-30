package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.enums.OrderStatus;
import com.example.demo.model.request.CreateOrderRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductAttributeRepository productAttributeRepository;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository, UserRepository userRepository, ProductAttributeRepository productAttributeRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productAttributeRepository = productAttributeRepository;
    }

    public ResponseEntity<ResponseApi<?>> createOrder(Principal principal, CreateOrderRequest createOrderRequest) {
        User user = userRepository.findByEmail(principal.getName());
        Order order = new Order();

        if (createOrderRequest.getAddress() == null) {
            order.setAddress(user.getAddress());
        } else {
            order.setAddress(createOrderRequest.getAddress());
        }

        if (createOrderRequest.getPhoneNumber() == null) {
            order.setPhoneNumber(user.getPhoneNumber());
        } else {
            order.setPhoneNumber(createOrderRequest.getPhoneNumber());
        }

        order.setUser(user);
        List<CartItem> listCartItem = cartItemRepository.findByUserId(user.getId());
        Set<OrderItem> listOrderItem = new HashSet<>();
        BigDecimal total = new BigDecimal(0);

        for (CartItem cartItem : listCartItem) {
            ProductAttribute productAttribute = productAttributeRepository.findById(cartItem.getProductAttribute().getId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductAttribute(productAttribute);
            orderItem.setOrder(order);
            listOrderItem.add(orderItem);
            total = total.add(productAttribute.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }
        order.setTotalPrice(total);
        order.setListOrderItems(listOrderItem);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        return new ResponseEntity<>(new ResponseApi<>("Get order success", 200), HttpStatus.OK);
    }
}
