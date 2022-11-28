package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.SampleCart.getSampleCart;
import static com.example.demo.SampleItem.getSampleItem;
import static com.example.demo.SampleUser.*;
import static com.example.demo.SampleUser.getSampleUser;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    private CartController cartController;

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        when(userRepository.findByUsername(USERNAME)).thenReturn(getSampleUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(getSampleItem()));
        when(cartRepository.save(getSampleCart())).thenReturn(getSampleCart());
    }

    @Test
    public void addToCart() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(getSampleUser().getUsername());
        modifyCartRequest.setQuantity(2);
        modifyCartRequest.setItemId(getSampleItem().getId());
        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(modifyCartRequest);
        assertNotNull(cartResponseEntity);
        assertEquals(HttpStatus.CREATED, cartResponseEntity.getStatusCode());
        Cart cart = cartResponseEntity.getBody();
        assert cart != null;
        assertEquals(3, cart.getItems().size());
        assertEquals(BigDecimal.valueOf(5.0), cart.getTotal());
    }

    @Test
    public void removeFromCart() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(getSampleUser().getUsername());
        modifyCartRequest.setQuantity(2);
        modifyCartRequest.setItemId(getSampleItem().getId());
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(modifyCartRequest);
        assertNotNull(cartResponseEntity);
        assertEquals(HttpStatus.OK, cartResponseEntity.getStatusCode());
        Cart cart = cartResponseEntity.getBody();
        assert cart != null;
        assertEquals(1, cart.getItems().size());
        assertEquals(BigDecimal.valueOf(-5.0), cart.getTotal());
    }
}