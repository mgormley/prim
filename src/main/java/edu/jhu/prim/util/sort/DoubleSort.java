package edu.jhu.prim.util.sort;

import edu.jhu.prim.util.Utilities;
import edu.jhu.prim.util.math.Vectors;

public class DoubleSort {

    public DoubleSort() {
        // private constructor
    }

    /* ------------------- Doubles only --------------- */
    
    /**
     * Performs an in-place quick sort on array. Sorts in descending order.
     */
    public static void sortDesc(double[] array) {
        Vectors.scale(array, -1);
        sortAsc(array);
        Vectors.scale(array, -1);
    }
    
    /**
     * Performs an in-place quick sort on array. Sorts in acscending order.
     */
    public static void sortAsc(double[] array) {
        quicksort(array, 0, array.length-1);
    }
    
    private static void quicksort(double[] array, int left, int right) {
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
    
    private static int partition(double[] array, int left, int right, int pivotIndex) {
        double pivotValue = array[pivotIndex];
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
    
    public static int[] getIndexArray(double[] values) {
        return Utilities.getIndexArray(values.length);
    }

    /**
     * Swaps the elements at positions i and j.
     */
    private static void swap(double[] array, int i, int j) {
        double valAtI = array[i];
        array[i] = array[j];
        array[j] = valAtI;
    }
        
    public static boolean isSortedAsc(double[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] > array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedDesc(double[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] < array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedAscAndUnique(double[] array) {
        for (int i=0; i<array.length-1; i++) {
            if (array[i] >= array[i+1]) {
                return false;
            }
        }
        return true;
    }

}
