package edu.jhu.prim.sort;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.list.IntStack;

public class IntIntSort {

    public static int numSwaps = 0;

    public IntIntSort() {
        // private constructor
    }

    /* ------------------- Ints and Ints --------------- */
    
    /**
     * Performs an in-place quick sort on values. All the sorting operations on values
     * are mirrored in index. Sorts in descending order.
     */
    public static void sortValuesDesc(int[] values, int[] index) {
        quicksortValues(values, index, 0, index.length - 1, false);
    }
    
    /**
     * Performs an in-place quick sort on values. All the sorting operations on values
     * are mirrored in index. Sorts in ascending order.
     */
    public static void sortValuesAsc(int[] values, int[] index) {
        quicksortValues(values, index, 0, index.length - 1, true);
    }

    private static void quicksortValues(int[] array, int[] index, int left, int right, boolean asc) {
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
                int pivotNewIndex = partitionValues(array, index, left, right, pivotIndex, asc);
                // "Recurse" on the left side.
                leftStack.push(left);
                rightStack.push(pivotNewIndex - 1);
                // "Recurse" on the right side.
                leftStack.push(pivotNewIndex + 1);
                rightStack.push(right);
            }
        }
    }

    static void quicksortValuesRecursive(int[] array, int[] index, int left, int right, boolean asc) {
        if (left < right) {
            // Choose a pivot index.
            // --> Here we choose the rightmost element which does the least
            // amount of work if the array is already sorted.
            int pivotIndex = right;
            // Partition the array so that everything less than
            // values[pivotIndex] is on the left of pivotNewIndex and everything
            // greater than or equal is on the right.
            int pivotNewIndex = partitionValues(array, index, left, right, pivotIndex, asc);
            // Recurse on the left and right sides.
            quicksortValuesRecursive(array, index, left, pivotNewIndex - 1, asc);
            quicksortValuesRecursive(array, index, pivotNewIndex + 1, right, asc);
        }
    }
    
    private static int partitionValues(int[] array, int[] index, int left, int right, int pivotIndex, boolean asc) {
        int pivotValue = array[pivotIndex];
        // Move the pivot value to the rightmost position.
        swap(array, index, pivotIndex, right);
        // For each position between left and right, swap all the values less
        // than or equal to the pivot value to the left side.
        int storeIndex = left;
        for (int i=left; i<right; i++) {
            if (lte(array[i], pivotValue, asc)) {
                swap(array, index, i, storeIndex);
                storeIndex++;
            }
        }
        // Move the pivot value back to the split point.
        swap(array, index, storeIndex, right);
        return storeIndex;
    }

    /**
     * Performs an in-place quick sort on {@code index}. All the sorting operations on {@code index}
     * are mirrored in {@code values}.
     * Sorts in descending order.
     */
    public static void sortIndexDesc(int[] index, int[] values) {
        quicksortIndex(index, values, 0, index.length - 1, false);
    }
    
    /**
     * Performs an in-place quick sort on {@code index} on the positions up to but not
     * including {@code top}. All the sorting operations on {@code index}
     * are mirrored in {@code values}.
     * Sorts in descending order.
     */
    public static void sortIndexDesc(int[] index, int[] values, int top) {
        quicksortIndex(index, values, 0, top - 1, false);
    }
    
    /**
     * Performs an in-place quick sort on {@code index}. All the sorting operations on {@code index}
     * are mirrored in {@code values}.
     * Sorts in ascending order.
     */
    public static void sortIndexAsc(int[] index, int[] values) {
        quicksortIndex(index, values, 0, index.length - 1, true);
    }

    /**
     * Performs an in-place quick sort on {@code index} on the positions up to but not
     * including {@code top}. All the sorting operations on {@code index} are mirrored in {@code values}.
     * Sorts in ascending order.
     * @return {@code index} - sorted.
     */
    public static void sortIndexAsc(int[] index, int[] values, int top) {
        assert top <= index.length;
        quicksortIndex(index, values, 0, top - 1, true);
    }
    
    private static void quicksortIndex(int[] array, int[] values, int left, int right, boolean asc) {
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
                // Partition the array  so that everything less than
                // values[pivotIndex] is on the left of pivotNewIndex and everything
                // greater than or equal is on the right.
                int pivotNewIndex = partitionIndex(array, values, left, right, pivotIndex, asc);
                // "Recurse" on the left side.
                leftStack.push(left);
                rightStack.push(pivotNewIndex - 1);
                // "Recurse" on the right side.
                leftStack.push(pivotNewIndex + 1);
                rightStack.push(right);
            }
        }
    }
    
    static void quicksortIndexRecursive(int[] array, int[] values, int left, int right, boolean asc) {
        if (left < right) {
            // Choose a pivot index.
            // --> Here we choose the rightmost element which does the least
            // amount of work if the array is already sorted.
            int pivotIndex = right;
            // Partition the array so that everything less than
            // values[pivotIndex] is on the left of pivotNewIndex and everything
            // greater than or equal is on the right.
            int pivotNewIndex = partitionIndex(array, values, left, right, pivotIndex, asc);
            // Recurse on the left and right sides.
            quicksortIndexRecursive(array, values, left, pivotNewIndex - 1, asc);
            quicksortIndexRecursive(array, values, pivotNewIndex + 1, right, asc);
        }
    }
    private static int partitionIndex(int[] array, int[] values, int left, int right, int pivotIndex, boolean asc) {
        int pivotValue = array[pivotIndex];
        // Move the pivot value to the rightmost position.
        swap(values, array, pivotIndex, right);
        // For each position between left and right, swap all the values less
        // than or equal to the pivot value to the left side.
        int storeIndex = left;
        for (int i=left; i<right; i++) {
            if (lte(array[i], pivotValue, asc)) {
                swap(values, array, i, storeIndex);
                storeIndex++;
            }
        }
        // Move the pivot value back to the split point.
        swap(values, array, storeIndex, right);
        return storeIndex;
    }
        
    /**
     * Swaps the elements at positions i and j in both the values and index array, which must be the same length.
     * @param values An array of values.
     * @param index An array of indices.
     * @param i The position of the first element to swap.
     * @param j The position of the second element to swap.
     */
    private static void swap(int[] values, int[] index, int i, int j) {
        if (i != j) {
            swap(values, i, j);
            swap(index, i, j);
            numSwaps ++;
        }
    }
    
    /* ----------------------------------------------------- */

    /**
     * Swaps the elements at positions i and j.
     */
    private static void swap(int[] array, int i, int j) {
        int valAtI = array[i];
        array[i] = array[j];
        array[j] = valAtI;
    }

    /** Abstract "less than or equal" for either ascending or descending orders. */
    private static boolean lte(int v1, int v2, boolean asc) {
        if (asc) {
            return v1 <= v2;
        } else {
            return v2 <= v1;
        }
    }

    /*  */

}
