package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import java.util.ArrayList;
import java.util.List;

public class SampleUser {
    public static final String USERNAME = "rmmcosta";
    public static final String PASSWORD = "1234567";
    public static final String HASHED_PASSWORD = "cenasEcoisoEtal23434";
    public static final long USER_ID = 1L;

    public static User getSampleUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>(List.of(new Item())));
        user.setCart(cart);
        return user;
    }

    public static class LoginUser {
        public String username;
        public String password;
    }
}
