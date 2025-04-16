package com.example.musteri.type;

import java.util.List;

public class Util {

	private Util() {
        // Prevent instantiation
    }
	
	public static int[] bubleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        return arr;
	}
	
	public static List<Integer> treeSort(int[] arr) {
        BinaryTree<Integer> tree = new BinaryTree<>();
        for (int i : arr) {
            tree.add(i);
        }
        
        return tree.toSortedList();
    }
}
