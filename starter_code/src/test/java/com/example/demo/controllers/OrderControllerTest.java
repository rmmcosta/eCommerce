package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.demo.SampleUser.USERNAME;
import static com.example.demo.SampleUser.getSampleUser;
import static com.example.demo.SampleUserOrder.getSampleOrders;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private final UserRepository userRepository=mock(UserRepository.class);

    private final OrderRepository orderRepository=mock(OrderRepository.class);

    OrderController orderController;

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
        when(userRepository.findByUsername(USERNAME)).thenReturn(getSampleUser());
        when(orderRepository.findByUser(getSampleUser())).thenReturn(getSampleOrders());
    }

    @Test
    public void submitWithSuccess() {
        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit(USERNAME);
        assertNotNull(orderResponseEntity);
        assertEquals(HttpStatus.CREATED, orderResponseEntity.getStatusCode());
    }

    @Test
    public void getOrdersForUser() {
        ResponseEntity<List<UserOrder>> ordersResponseEntity = orderController.getOrdersForUser(USERNAME);
        assertNotNull(ordersResponseEntity);
        assertEquals(HttpStatus.OK, ordersResponseEntity.getStatusCode());
    }

    @Test
    public void submitForUnknownUser() {
        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit("");
        assertNotNull(orderResponseEntity);
        assertEquals(HttpStatus.NOT_FOUND, orderResponseEntity.getStatusCode());
    }

    @Test
    public void getOrdersForUnknownUser() {
        ResponseEntity<List<UserOrder>> ordersResponseEntity = orderController.getOrdersForUser("");
        assertNotNull(ordersResponseEntity);
        assertEquals(HttpStatus.NOT_FOUND, ordersResponseEntity.getStatusCode());
    }
}