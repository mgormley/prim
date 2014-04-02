package edu.jhu.prim.sort;

import edu.jhu.prim.arrays.ShortArrays;

public class ShortSort {

    public ShortSort() {
        // private constructor
    }

    /* ------------------- Doubles only --------------- */
    
    /**
     * Performs an in-place quick sort on array. Sorts in descending order.
     */
    public static void sortDesc(short[] array) {
        ShortArrays.scale(array, (short) -1);
        sortAsc(array);
        ShortArrays.scale(array, (short) -1);
    }
    
    /**
     * Performs an in-place quick sort on array. Sorts in acscending order.
     */
    public static void sortAsc(short[] array) {
        quicksort(array, 0, array.length-1);
    }
    
    private static void quicksort(short[] array, int left, int right) {
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
    
    private static int partition(short[] array, int left, int right, int pivotIndex) {
        short pivotValue = array[pivotIndex];
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
    
    public static short[] getIndexArray(short[] values) {
        return getIndexArray(values.length);
    }

    /**
     * Gets an array where array[i] = i.
     * @param length The length of the array.
     * @return The new index array.
     */
    public static short[] getIndexArray(int length) {
        short[] index = new short[length];
        for (int i=0; i<index.length; i++) {
            index[i] = (short) i;
        }
        return index;
    }
    
    /**
     * Swaps the elements at positions i and j.
     */
    private static void swap(short[] array, int i, int j) {
        short valAtI = array[i];
        array[i] = array[j];
        array[j] = valAtI;
    }
        
    public static boolean isSortedAsc(short[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] > array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedDesc(short[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] < array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedAscAndUnique(short[] array) {
        for (int i=0; i<array.length-1; i++) {
            if (array[i] >= array[i+1]) {
                return false;
            }
        }
        return true;
    }

}
