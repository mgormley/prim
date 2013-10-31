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

    /**
     * Counts the number of unique indices in two arrays.
     * @param indices1 Sorted array of indices.
     * @param indices2 Sorted array of indices.
     */
    public static int countUnique(long[] indices1, long[] indices2) {
        int numUniqueIndices = 0;
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
     * @param indices1 Sorted array of indices.
     * @param indices2 Sorted array of indices.
     */
    public static int countCommon(long[] indices1, long[] indices2) {
        int numCommonIndices = 0;
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

}
