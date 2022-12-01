package com.example.demo.model.persistence;

import com.example.demo.SampleItem;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testEquals() {
        Item item1 = SampleItem.getSampleItem();
        Item item2 = SampleItem.getSampleItem();
        assertEquals(item1, item2);
    }

    @Test
    public void getName() {
        final String ITEM_NAME = "MyName";
        Item item = SampleItem.getSampleItem();
        item.setName(ITEM_NAME);
        assertEquals(ITEM_NAME, item.getName());
    }

    @Test
    public void getDescription() {
        final String ITEM_DESC = "My description is awesome";
        Item item = SampleItem.getSampleItem();
        item.setDescription(ITEM_DESC);
        assertEquals(ITEM_DESC, item.getDescription());
    }

    @Test
    public void testHashCode() {
        Item item1 = SampleItem.getSampleItem();
        Item item2 = SampleItem.getSampleItem();
        assertEquals(item1.hashCode(), item2.hashCode());
    }
}