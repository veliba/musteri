package com.example.musteri;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.musteri.type.Util;

class UtilTests {

    @Test
    void bubleSortSortsArrayWithPositiveNumbers() {
        int[] input = {5, 3, 8, 6, 2};
        int[] expected = {2, 3, 5, 6, 8};
        assertArrayEquals(expected, Util.bubleSort(input));
    }

    @Test
    void bubleSortHandlesEmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, Util.bubleSort(input));
    }

    @Test
    void bubleSortHandlesSingleElementArray() {
        int[] input = {42};
        int[] expected = {42};
        assertArrayEquals(expected, Util.bubleSort(input));
    }

    @Test
    void bubleSortHandlesArrayWithNegativeNumbers() {
        int[] input = {-3, -1, -7, -4};
        int[] expected = {-7, -4, -3, -1};
        assertArrayEquals(expected, Util.bubleSort(input));
    }

    @Test
    void bubleSortHandlesArrayWithDuplicates() {
        int[] input = {4, 2, 4, 2, 1};
        int[] expected = {1, 2, 2, 4, 4};
        assertArrayEquals(expected, Util.bubleSort(input));
    }

    @Test
    void treeSortSortsArrayWithPositiveNumbers() {
        int[] input = {5, 3, 8, 6, 2};
        List<Integer> expected = List.of(2, 3, 5, 6, 8);
        assertEquals(expected, Util.treeSort(input));
    }

    @Test
    void treeSortHandlesEmptyArray() {
        int[] input = {};
        List<Integer> expected = List.of();
        assertEquals(expected, Util.treeSort(input));
    }

    @Test
    void treeSortHandlesSingleElementArray() {
        int[] input = {42};
        List<Integer> expected = List.of(42);
        assertEquals(expected, Util.treeSort(input));
    }

    @Test
    void treeSortHandlesArrayWithNegativeNumbers() {
        int[] input = {-3, -1, -7, -4};
        List<Integer> expected = List.of(-7, -4, -3, -1);
        assertEquals(expected, Util.treeSort(input));
    }

    @Test
    void treeSortHandlesArrayWithDuplicates() {
        int[] input = {4, 2, 4, 2, 1};
        List<Integer> expected = List.of(1, 2, 4);
        assertEquals(expected, Util.treeSort(input));
    }
}
