package com.example.demo;

import com.example.demo.model.persistence.UserOrder;

import java.util.List;

public class SampleUserOrder {
    public static List<UserOrder> getSampleOrders() {
        return List.of(new UserOrder());
    }
}
