package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.prim.Primitives;

/**
 * Utility methods and math for boolean arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class BoolArrays {

    private BoolArrays() {
        // private constructor
    }

    public static boolean[] copyOf(boolean[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static boolean[] copyOf(boolean[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static boolean[][] copyOf(boolean[][] array) {
        boolean[][] newArray = new boolean[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static boolean[][][] copyOf(boolean[][][] array) {
        boolean[][][] clone = new boolean[array.length][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static boolean[][][][] copyOf(boolean[][][][] array) {
        boolean[][][][] clone = new boolean[array.length][][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }
    
    public static void fill(boolean[][] array, boolean value) {
        for (int i=0; i<array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(boolean[][][] array, boolean value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(boolean[][][][] array, boolean value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

    /**
     * Faster version of Arrays.fill(). That standard version does NOT use
     * memset, and only iterates over the array filling each value. This method
     * works out to be much faster and seems to be using memset as appropriate.
     */
    public static void fill(final boolean[] array, final boolean value) {
        //        final int n = array.length;
        //        if (n > 0) {
        //            array[0] = value;
        //        }
        //        for (int i = 1; i < n; i += i) {
        //           System.arraycopy(array, 0, array, i, ((n - i) < i) ? (n - i) : i);
        //        }
        for (int i=0; i<array.length; i++) {
            array[i] = value;
        }
    }

    /** Checks in O(n) by linear search if the array contains the value. */
    public static boolean contains(boolean[] array, boolean value) {
        for (int i=0; i<array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }
    
    /** Checks in O(n) by linear search if the array contains the value. */
    public static boolean contains(boolean[][] array, boolean value) {
        for (int i=0; i<array.length; i++) {
            if (contains(array[i], value)) {
                return true;
            }
        }
        return false;
    }

}
