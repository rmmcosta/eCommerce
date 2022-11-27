package com.udacity.examples.Testing;

import org.junit.Test;

import java.util.IntSummaryStatistics;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelperTest {

    @Test
    public void getCount() {
        assertEquals(2, Helper.getCount(List.of("abs", "", "coisio", "", "cenas")));
    }

    @Test
    public void getStats() {
        List<Integer> ints = List.of(1, 2, 3, 4, 5);
        IntSummaryStatistics intSummaryStatistics = Helper.getStats(ints);
        assertEquals(15, intSummaryStatistics.getSum());
        assertEquals(5, intSummaryStatistics.getMax());
        assertEquals(1, intSummaryStatistics.getMin());
        assertEquals(3.0, intSummaryStatistics.getAverage(), 0.0);
    }

    @Test
    public void getStringsOfLength3() {
        assertEquals(2, Helper.getStringsOfLength3(List.of("", "cenas", "abc", "efg", "e", "ab")));
    }

    @Test
    public void getSquareList() {
        List<Integer> ints = List.of(1, 2, 3, 4, 5);
        List<Integer> squares = Helper.getSquareList(ints);
        assertEquals(ints.size(), squares.size());
        for (int i = 0; i < squares.size(); i++) {
            int currIntValue = ints.get(i);
            assertEquals(currIntValue * currIntValue, squares.get(i).intValue());
        }
    }

    @Test
    public void getMergedList() {
        assertEquals("cenas, coisas, e tal", Helper.getMergedList(List.of("", "cenas", "coisas", "", "e tal")));
    }

    @Test
    public void getFilteredList() {
        assertEquals(2, Helper.getFilteredList(List.of("", "adg", "gaar", "", "")).size());
    }
}
