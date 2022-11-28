package com.example.demo;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.UserOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleItem {
    public static List<Item> getSampleItems() {
        return new ArrayList<>(List.of(new Item()));
    }
    public static Item getSampleItem() {
        Item item = new Item();
        item.setId(1L);
        item.setDescription("xc e tal");
        item.setName("The Item");
        item.setPrice(BigDecimal.valueOf(2.5));
        return item;
    }
}
