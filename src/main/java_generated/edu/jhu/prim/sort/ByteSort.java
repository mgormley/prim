package edu.jhu.prim.sort;

import edu.jhu.prim.arrays.ByteArrays;
import edu.jhu.prim.list.IntStack;

public class ByteSort {

    public ByteSort() {
        // private constructor
    }

    /* ------------------- Doubles only --------------- */
    
    /**
     * Performs an in-place quick sort on array. Sorts in descending order.
     */
    public static void sortDesc(byte[] array) {
        ByteArrays.scale(array, (byte) -1);
        sortAsc(array);
        ByteArrays.scale(array, (byte) -1);
    }
    
    /**
     * Performs an in-place quick sort on array. Sorts in acscending order.
     */
    public static void sortAsc(byte[] array) {
        quicksort(array, 0, array.length-1);
    }
    
    private static void quicksort(byte[] array, int left, int right) {
        IntStack leftStack = new IntStack();
        IntStack rightStack = new IntStack();
        leftStack.add(left);
        rightStack.add(right);
        while (leftStack.size() > 0) {
            left = leftStack.pop();
            right = rightStack.pop();
            if (left < right) {
                // Choose a pivot index.
                // --> Here we choose the rightmost element which does the least
                // amount of work if the array is already sorted.
                int pivotIndex = right;
                // Partition the array so that everything less than
                // values[pivotIndex] is on the left of pivotNewIndex and everything
                // greater than or equal is on the right.
                int pivotNewIndex = partition(array, left, right, pivotIndex);
                // "Recurse" on the left side.
                leftStack.push(left);
                rightStack.push(pivotNewIndex - 1);
                // "Recurse" on the right side.
                leftStack.push(pivotNewIndex + 1);
                rightStack.push(right);
            }
        }
    }
    
    static void quicksortRecursive(byte[] array, int left, int right) {
        if (left < right) {
            // Choose a pivot index.
            // --> Here we choose the rightmost element which does the least
            // amount of work if the array is already sorted.
            int pivotIndex = right;
            // Partition the array so that everything less than
            // values[pivotIndex] is on the left of pivotNewIndex and everything
            // greater than or equal is on the right.
            int pivotNewIndex = partition(array, left, right, pivotIndex);
            // Recurse on the left and right sides.
            quicksort(array, left, pivotNewIndex - 1);
            quicksort(array, pivotNewIndex + 1, right);
        }
    }
    
    private static int partition(byte[] array, int left, int right, int pivotIndex) {
        byte pivotValue = array[pivotIndex];
        // Move the pivot value to the rightmost position.
        swap(array, pivotIndex, right);
        // For each position between left and right, swap all the values less
        // than or equal to the pivot value to the left side.
        int storeIndex = left;
        for (int i=left; i<right; i++) {
            if (array[i] <= pivotValue) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }
        // Move the pivot value back to the split point.
        swap(array, storeIndex, right);
        return storeIndex;
    }
    
    public static byte[] getIndexArray(byte[] values) {
        return getIndexArray(values.length);
    }

    /**
     * Gets an array where array[i] = i.
     * @param length The length of the array.
     * @return The new index array.
     */
    public static byte[] getIndexArray(int length) {
        byte[] index = new byte[length];
        for (int i=0; i<index.length; i++) {
            index[i] = (byte) i;
        }
        return index;
    }
    
    /**
     * Swaps the elements at positions i and j.
     */
    private static void swap(byte[] array, int i, int j) {
        byte valAtI = array[i];
        array[i] = array[j];
        array[j] = valAtI;
    }
        
    public static boolean isSortedAsc(byte[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] > array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedDesc(byte[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] < array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedAscAndUnique(byte[] array) {
        for (int i=0; i<array.length-1; i++) {
            if (array[i] >= array[i+1]) {
                return false;
            }
        }
        return true;
    }

}
