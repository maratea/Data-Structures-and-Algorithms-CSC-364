package com.company;
public class InPlaceIntHeapSort {

    public static void heapSort(int[] array) {
        for (int i = (array.length - 1) / 2; i >= 0; i--) {
            createHeap(array, i, array.length - 1);
        }

        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i);
            createHeap(array, 0, i - 1);
        }
    }

    private static void createHeap(int[] array, int index, int maxIndex) {
        int lChild = 2 * index + 1;
        int rChild = 2 * index + 2;
        int topIndex = index;

        if (lChild <= maxIndex && array[lChild] > array[topIndex]) {
            topIndex = lChild;
        }

        if (rChild <= maxIndex && array[rChild] > array[topIndex]) {
            topIndex = rChild;
        }

        if (topIndex != index) {
            swap(array, index, topIndex);
            createHeap(array, topIndex, maxIndex);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
