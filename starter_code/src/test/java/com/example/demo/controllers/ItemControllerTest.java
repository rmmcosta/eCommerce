package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.example.demo.SampleItem.getSampleItem;
import static com.example.demo.SampleItem.getSampleItems;
import static com.example.demo.SampleUser.USERNAME;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private final ItemRepository itemRepository = mock(ItemRepository.class);

    ItemController itemController;

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
        when(itemRepository.findAll()).thenReturn(getSampleItems());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(getSampleItem()));
        when(itemRepository.findByName("xpto")).thenReturn(getSampleItems());
    }

    @Test
    public void getItems() {
        ResponseEntity<List<Item>> itemsResponseEntity = itemController.getItems();
        assertNotNull(itemsResponseEntity);
        assertEquals(HttpStatus.OK, itemsResponseEntity.getStatusCode());
    }

    @Test
    public void getItemById() {
        ResponseEntity<Item> itemResponseEntity = itemController.getItemById(1L);
        assertNotNull(itemResponseEntity);
        assertEquals(HttpStatus.OK, itemResponseEntity.getStatusCode());
    }

    @Test
    public void getItemsByName() {
        ResponseEntity<List<Item>> itemsResponseEntity = itemController.getItemsByName("xpto");
        assertNotNull(itemsResponseEntity);
        assertEquals(HttpStatus.OK, itemsResponseEntity.getStatusCode());
    }
}