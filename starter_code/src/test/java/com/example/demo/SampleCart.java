package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;

import java.math.BigDecimal;

import static com.example.demo.SampleItem.getSampleItems;

public class SampleCart {
    public static Cart getSampleCart() {
        Cart cart = new Cart();
        cart.setItems(getSampleItems());
        cart.setTotal(BigDecimal.valueOf(2.5));
        cart.setUser(new User());
        return cart;
    }
}
