package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.util.Prng;

/**
 * Utility methods and math for int arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class IntArrays {

    private IntArrays() {
        // private constructor
    }

    public static void add(int[] params, int lambda) {
        for (int i=0; i<params.length; i++) {
            params[i] += lambda;
        }
    }
    
    public static double sum(int[] vector) {
        int sum = 0;
        for(int i=0; i<vector.length; i++) {
            sum += vector[i];
        }
        return sum;
    }

    public static int[] copyOf(int[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static int[] copyOf(int[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static int[][] copyOf(int[][] array) {
        int[][] newArray = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static int[][][] copyOf(int[][][] array) {
        int[][][] clone = new int[array.length][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }
    
    public static int[][][][] copyOf(int[][][][] array) {
        int[][][][] clone = new int[array.length][][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static void fill(int[][] array, int value) {
        for (int i=0; i<array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(int[][][] array, int value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(int[][][][] array, int value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void copy(int[] array, int[] clone) {
        assert (array.length == clone.length);
        System.arraycopy(array, 0, clone, 0, array.length);
    }
    
    public static void copy(int[][] array, int[][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < array.length; i++) {
            IntArrays.copy(array[i], clone[i]);
        }
    }

    /**
     * Fisher-Yates shuffle randomly reorders the elements in array. This
     * is O(n) in the length of the array.
     */
    public static void shuffle(int[] array) {
      for (int i=array.length-1; i > 0; i--) {
        int j = Prng.nextInt(i+1);
        // Swap array[i] and array[j]
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
      }
    }

    public static int compare(int[] x, int[] y) {
        for (int i=0; i<Math.min(x.length, y.length); i++) {
            int diff = x[i] - y[i];
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

    public static void scale(int[] array, int alpha) {
        for (int i=0; i<array.length; i++) {
            array[i] *= alpha;
        }
    }

}
