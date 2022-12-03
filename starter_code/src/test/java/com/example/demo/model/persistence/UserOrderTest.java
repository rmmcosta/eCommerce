package com.example.demo.model.persistence;

import com.example.demo.SampleCart;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class UserOrderTest {

    @Test
    public void createFromCart() {
        Cart cart = SampleCart.getSampleCart();
        UserOrder order = UserOrder.createFromCart(cart);
        assertTrue(order.getItems().containsAll(cart.getItems()));
        assertEquals(cart.getTotal(), order.getTotal());
        assertEquals(cart.getUser(), order.getUser());
    }
}
