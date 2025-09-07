package ru.nsu.shadrina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {

    @Test
    void testEmptyArray() {
        int[] arr = {};
        HeapSort.heapsort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        HeapSort.heapsort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        HeapSort.heapsort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort.heapsort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testUnsortedArray() {
        int[] arr = {5, 3, 8, 4, 1};
        HeapSort.heapsort(arr);
        assertArrayEquals(new int[]{1, 3, 4, 5, 8}, arr);
    }

    @Test
    void testWithDuplicates() {
        int[] arr = {4, 2, 5, 2, 3};
        HeapSort.heapsort(arr);
        assertArrayEquals(new int[]{2, 2, 3, 4, 5}, arr);
    }

    @Test
    void testNegativeNumbers() {
        int[] arr = {3, -1, 4, -5, 0};
        HeapSort.heapsort(arr);
        assertArrayEquals(new int[]{-5, -1, 0, 3, 4}, arr);
    }

    @Test
    void testMainDoesNotThrow() {
        Main.main(new String[]{});
    }
}
