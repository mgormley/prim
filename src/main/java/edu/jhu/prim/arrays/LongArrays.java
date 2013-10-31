package edu.jhu.prim.arrays;

import java.util.Arrays;

/**
 * Utility methods and math for long arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class LongArrays {

    private LongArrays() {
        // private constructor
    }

    public static void scale(long[] array, int alpha) {
        for (int i=0; i<array.length; i++) {
            array[i] *= alpha;
        }
    }

    public static long[] copyOf(long[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static long[] copyOf(long[] original) {
        return Arrays.copyOf(original, original.length);
    }

}
