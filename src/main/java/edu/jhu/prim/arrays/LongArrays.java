package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.Prng;

/**
 * Utility methods and math for long arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class LongArrays {

    private LongArrays() {
        // private constructor
    }

    public static void add(long[] params, long lambda) {
        for (int i = 0; i < params.length; i++) {
            params[i] += lambda;
        }
    }

    public static long sum(long[] vector) {
        long sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i];
        }
        return sum;
    }

    public static long prod(long[] vector) {
        long prod = 1;
        for (int i = 0; i < vector.length; i++) {
            prod *= vector[i];
        }
        return prod;
    }

    public static long max(long[] array) {
        long max = Long.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static long[] copyOf(long[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static long[] copyOf(long[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static long[][] copyOf(long[][] array) {
        long[][] newArray = new long[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static long[][][] copyOf(long[][][] array) {
        long[][][] clone = new long[array.length][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static long[][][][] copyOf(long[][][][] array) {
        long[][][][] clone = new long[array.length][][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static void fill(long[][] array, long value) {
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(long[][][] array, long value) {
        for (int i = 0; i < array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(long[][][][] array, long value) {
        for (int i = 0; i < array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void copy(long[] array, long[] clone) {
        assert (array.length == clone.length);
        System.arraycopy(array, 0, clone, 0, array.length);
    }

    public static void copy(long[][] array, long[][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < array.length; i++) {
            LongArrays.copy(array[i], clone[i]);
        }
    }

    /**
     * Fisher-Yates shuffle randomly reorders the elements in array. This is
     * O(n) in the length of the array.
     */
    public static void shuffle(long[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = Prng.nextInt(i + 1);
            // Swap array[i] and array[j]
            long tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static int compare(long[] x, long[] y) {
        for (int i = 0; i < Math.min(x.length, y.length); i++) {
            int diff = Long.compare(x[i], y[i]);
            if (diff != 0) {
                return diff;
            }
        }

        if (x.length < y.length) {
            return -1;
        } else if (x.length > y.length) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void scale(long[] array, long alpha) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= alpha;
        }
    }

    /** Counts the number of times value appears in array. */
    public static long count(long[] array, long value) {
        long count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                count++;
            }
        }
        return count;
    }

    /**
     * Reorder array in place.
     * 
     * Letting A denote the original array and B the reordered version, we will
     * have B[i] = A[order[i]].
     * 
     * @param array The array to reorder.
     * @param order The order to apply.
     */
    public static void reorder(long[] array, int[] order) {
        long[] original = copyOf(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = original[order[i]];
        }
    }

    /**
     * Counts the number of unique indices in two arrays.
     * 
     * @param indices1 Sorted array of indices.
     * @param indices2 Sorted array of indices.
     */
    public static long countUnique(long[] indices1, long[] indices2) {
        long numUniqueIndices = 0;
        int i = 0;
        int j = 0;
        while (i < indices1.length && j < indices2.length) {
            if (indices1[i] < indices2[j]) {
                numUniqueIndices++;
                i++;
            } else if (indices2[j] < indices1[i]) {
                numUniqueIndices++;
                j++;
            } else {
                // Equal indices.
                i++;
                j++;
            }
        }
        for (; i < indices1.length; i++) {
            numUniqueIndices++;
        }
        for (; j < indices2.length; j++) {
            numUniqueIndices++;
        }
        return numUniqueIndices;
    }

    /**
     * Counts the number of indices that appear in both arrays.
     * 
     * @param indices1 Sorted array of indices.
     * @param indices2 Sorted array of indices.
     */
    public static long countCommon(long[] indices1, long[] indices2) {
        long numCommonIndices = 0;
        int i = 0;
        int j = 0;
        while (i < indices1.length && j < indices2.length) {
            if (indices1[i] < indices2[j]) {
                i++;
            } else if (indices2[j] < indices1[i]) {
                j++;
            } else {
                numCommonIndices++;
                // Equal indices.
                i++;
                j++;
            }
        }
        for (; i < indices1.length; i++) {
            numCommonIndices++;
        }
        for (; j < indices2.length; j++) {
            numCommonIndices++;
        }
        return numCommonIndices;
    }

    /**
     * Gets a copy of the array with the specified entry removed.
     * 
     * @param a The input array.
     * @param idx The entry to remove.
     * @return A new array with the entry removed.
     */
    public static long[] removeEntry(long[] a, int idx) {
        long[] b = new long[a.length - 1];
        for (int i = 0; i < b.length; i++) {
            if (i < idx) {
                b[i] = a[i];
            } else {
                b[i] = a[i + 1];
            }
        }
        return b;
    }

    /**
     * Gets a copy of the array with an entry inserted.
     * 
     * @param a The input array.
     * @param idx The position at which to insert.
     * @param val The value to insert.
     * @return A new array with the inserted value.
     */
    public static long[] insertEntry(long[] a, int idx, long val) {
        long[] b = new long[a.length + 1];
        for (int i = 0; i < b.length; i++) {
            if (i < idx) {
                b[i] = a[i];
            } else if (i == idx) {
                b[idx] = val;
            } else {
                b[i] = a[i - 1];
            }
        }
        return b;
    }
    
    /** Checks in O(n) by linear search if the array contains the value. */
    public static boolean contains(long[] array, long value) {
        for (int i=0; i<array.length; i++) {
            if (Primitives.equals(array[i], value)) {
                return true;
            }
        }
        return false;
    }
    
    /** Checks in O(n) by linear search if the array contains the value. */
    public static boolean contains(long[][] array, long value) {
        for (int i=0; i<array.length; i++) {
            if (contains(array[i], value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets an array where array[i] = i.
     * @param length The length of the array.
     * @return The new index array.
     */
    public static long[] range(int length) {
        long[] index = new long[length];
        for (int i=0; i<index.length; i++) {
            // TODO: This should maybe be a safe cast for the benefit of non-LongDouble classes.
            index[i] = (long) i;
        }
        return index;
    }

}
