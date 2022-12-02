package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username) throws JsonProcessingException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info(format("User with the username %s not found", username));
            return ResponseEntity.notFound().build();
        }
        UserOrder order = UserOrder.createFromCart(user.getCart());
        orderRepository.save(order);
        //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        //String orderJson = ow.writeValueAsString(order);
        //logger.info(format("Order %s submitted with success for the user %s", orderJson, username));
        logger.info(format("Order with total %s submitted with success for the user %s. {\"orderTotal\":\"%.2f\"}", order.getTotal(), username, order.getTotal()));
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) throws JsonProcessingException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info(format("User with the username %s not found", username));
            return ResponseEntity.notFound().build();
        }
        List<UserOrder> orders = orderRepository.findByUser(user);
        //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        //String ordersJson = ow.writeValueAsString(orders);
        //logger.info(format("User with the username %s has the following orders %s", username, ordersJson));
        //logger.info(format("Order with total %s submitted with success for the user %s", orders.stream()., username));
        return ResponseEntity.ok(orders);
    }
}
