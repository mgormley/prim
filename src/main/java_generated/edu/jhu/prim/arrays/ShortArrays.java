package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.Prng;

/**
 * Utility methods and math for short arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class ShortArrays {

    private ShortArrays() {
        // private constructor
    }

    public static void add(short[] params, short lambda) {
        for (int i = 0; i < params.length; i++) {
            params[i] += lambda;
        }
    }

    public static short sum(short[] vector) {
        short sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i];
        }
        return sum;
    }

    public static short prod(short[] vector) {
        short prod = 1;
        for (int i = 0; i < vector.length; i++) {
            prod *= vector[i];
        }
        return prod;
    }

    public static short max(short[] array) {
        short max = Short.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static short[] copyOf(short[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static short[] copyOf(short[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static short[][] copyOf(short[][] array) {
        short[][] newArray = new short[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static short[][][] copyOf(short[][][] array) {
        short[][][] clone = new short[array.length][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static short[][][][] copyOf(short[][][][] array) {
        short[][][][] clone = new short[array.length][][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static void fill(short[][] array, short value) {
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(short[][][] array, short value) {
        for (int i = 0; i < array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(short[][][][] array, short value) {
        for (int i = 0; i < array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void copy(short[] array, short[] clone) {
        assert (array.length == clone.length);
        System.arraycopy(array, 0, clone, 0, array.length);
    }

    public static void copy(short[][] array, short[][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < array.length; i++) {
            ShortArrays.copy(array[i], clone[i]);
        }
    }

    /**
     * Fisher-Yates shuffle randomly reorders the elements in array. This is
     * O(n) in the length of the array.
     */
    public static void shuffle(short[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = Prng.nextInt(i + 1);
            // Swap array[i] and array[j]
            short tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static int compare(short[] x, short[] y) {
        for (int i = 0; i < Math.min(x.length, y.length); i++) {
            int diff = Short.compare(x[i], y[i]);
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

    public static void scale(short[] array, short alpha) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= alpha;
        }
    }

    /** Counts the number of times value appears in array. */
    public static short count(short[] array, short value) {
        short count = 0;
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
    public static void reorder(short[] array, int[] order) {
        short[] original = copyOf(array);
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
    public static short countUnique(short[] indices1, short[] indices2) {
        short numUniqueIndices = 0;
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
    public static short countCommon(short[] indices1, short[] indices2) {
        short numCommonIndices = 0;
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
    public static short[] removeEntry(short[] a, int idx) {
        short[] b = new short[a.length - 1];
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
    public static short[] insertEntry(short[] a, int idx, short val) {
        short[] b = new short[a.length + 1];
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
    public static boolean contains(short[] array, short value) {
        for (int i=0; i<array.length; i++) {
            if (Primitives.equals(array[i], value)) {
                return true;
            }
        }
        return false;
    }
    
    /** Checks in O(n) by linear search if the array contains the value. */
    public static boolean contains(short[][] array, short value) {
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
    public static short[] range(int length) {
        short[] index = new short[length];
        for (int i=0; i<index.length; i++) {
            // TODO: This should maybe be a safe cast for the benefit of non-ShortDouble classes.
            index[i] = (short) i;
        }
        return index;
    }

}
