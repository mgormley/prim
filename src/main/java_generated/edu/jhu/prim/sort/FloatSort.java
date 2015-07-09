package edu.jhu.prim.sort;

import edu.jhu.prim.arrays.FloatArrays;
import edu.jhu.prim.list.IntStack;

public class FloatSort {

    public static int numSwaps = 0;

    public FloatSort() {
        // private constructor
    }

    /* ------------------- Floats only --------------- */
    
    /**
     * Performs an in-place quick sort on array. Sorts in descending order.
     */
    public static void sortDesc(float[] array) {
        quicksort(array, 0, array.length-1, false);
    }
    
    /**
     * Performs an in-place quick sort on array. Sorts in acscending order.
     */
    public static void sortAsc(float[] array) {
        quicksort(array, 0, array.length-1, true);
    }
    
    private static void quicksort(float[] array, int left, int right, boolean asc) {
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
                int pivotNewIndex = partition(array, left, right, pivotIndex, asc);
                // "Recurse" on the left side.
                leftStack.push(left);
                rightStack.push(pivotNewIndex - 1);
                // "Recurse" on the right side.
                leftStack.push(pivotNewIndex + 1);
                rightStack.push(right);
            }
        }
    }
        
    private static int partition(float[] array, int left, int right, int pivotIndex, boolean asc) {
        float pivotValue = array[pivotIndex];
        // Move the pivot value to the rightmost position.
        swap(array, pivotIndex, right);
        // For each position between left and right, swap all the values less
        // than or equal to the pivot value to the left side.
        int storeIndex = left;
        for (int i=left; i<right; i++) {
            if (lte(array[i], pivotValue, asc)) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }
        // Move the pivot value back to the split point.
        swap(array, storeIndex, right);
        return storeIndex;
    }
    
    public static int[] getIndexArray(float[] values) {
        return IntSort.getIndexArray(values.length);
    }

    /**
     * Swaps the elements at positions i and j.
     */
    private static void swap(float[] array, int i, int j) {
        if (i != j) {
            float valAtI = array[i];
            array[i] = array[j];
            array[j] = valAtI;
            numSwaps++;
        }        
    }
        
    public static boolean isSortedAsc(float[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] > array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedDesc(float[] array) {
    	for (int i=0; i<array.length-1; i++) {
    		if (array[i] < array[i+1]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isSortedAscAndUnique(float[] array) {
        for (int i=0; i<array.length-1; i++) {
            if (array[i] >= array[i+1]) {
                return false;
            }
        }
        return true;
    }

    /** Abstract "less than or equal" for either ascending or descending orders. */
    private static boolean lte(float v1, float v2, boolean asc) {
        if (asc) {
            return v1 <= v2;
        } else {
            return v2 <= v1;
        }
    }
}
