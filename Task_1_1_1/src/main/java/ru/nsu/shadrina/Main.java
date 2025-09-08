package ru.nsu.shadrina;

public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 1};
        HeapSort.heapsort(arr);

        int n = arr.length;
        System.out.print("[" + arr[0]);
        for (int i = 1; i < n; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.print("]");
    }
}
