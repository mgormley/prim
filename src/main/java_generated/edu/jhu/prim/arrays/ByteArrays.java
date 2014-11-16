package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.util.Prng;

/**
 * Utility methods and math for byte arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class ByteArrays {

    private ByteArrays() {
        // private constructor
    }

    public static void add(byte[] params, byte lambda) {
        for (int i = 0; i < params.length; i++) {
            params[i] += lambda;
        }
    }

    public static byte sum(byte[] vector) {
        byte sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i];
        }
        return sum;
    }

    public static byte prod(byte[] vector) {
        byte prod = 1;
        for (int i = 0; i < vector.length; i++) {
            prod *= vector[i];
        }
        return prod;
    }

    public static byte max(byte[] array) {
        byte max = Byte.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static byte[] copyOf(byte[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static byte[][] copyOf(byte[][] array) {
        byte[][] newArray = new byte[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static byte[][][] copyOf(byte[][][] array) {
        byte[][][] clone = new byte[array.length][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static byte[][][][] copyOf(byte[][][][] array) {
        byte[][][][] clone = new byte[array.length][][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static void fill(byte[][] array, byte value) {
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(byte[][][] array, byte value) {
        for (int i = 0; i < array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(byte[][][][] array, byte value) {
        for (int i = 0; i < array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void copy(byte[] array, byte[] clone) {
        assert (array.length == clone.length);
        System.arraycopy(array, 0, clone, 0, array.length);
    }

    public static void copy(byte[][] array, byte[][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < array.length; i++) {
            ByteArrays.copy(array[i], clone[i]);
        }
    }

    /**
     * Fisher-Yates shuffle randomly reorders the elements in array. This is
     * O(n) in the length of the array.
     */
    public static void shuffle(byte[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = Prng.nextInt(i + 1);
            // Swap array[i] and array[j]
            byte tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static int compare(byte[] x, byte[] y) {
        for (int i = 0; i < Math.min(x.length, y.length); i++) {
            int diff = Byte.compare(x[i], y[i]);
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

    public static void scale(byte[] array, byte alpha) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= alpha;
        }
    }

    /** Counts the number of times value appears in array. */
    public static byte count(byte[] array, byte value) {
        byte count = 0;
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
    public static void reorder(byte[] array, int[] order) {
        byte[] original = copyOf(array);
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
    public static byte countUnique(byte[] indices1, byte[] indices2) {
        byte numUniqueIndices = 0;
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
    public static byte countCommon(byte[] indices1, byte[] indices2) {
        byte numCommonIndices = 0;
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
    public static byte[] removeEntry(byte[] a, int idx) {
        byte[] b = new byte[a.length - 1];
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
    public static byte[] insertEntry(byte[] a, int idx, byte val) {
        byte[] b = new byte[a.length + 1];
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

    /**
     * Gets an array where array[i] = i.
     * @param length The length of the array.
     * @return The new index array.
     */
    public static byte[] range(int length) {
        byte[] index = new byte[length];
        for (int i=0; i<index.length; i++) {
            // TODO: This should maybe be a safe cast for the benefit of non-ByteDouble classes.
            index[i] = (byte) i;
        }
        return index;
    }

}
