package edu.jhu.prim.sample;

import java.util.Arrays;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.sort.IntSort;
import edu.jhu.prim.util.random.Prng;

public class Sample {

    private Sample() {
        // private constructor.
    }

    /**
     * Samples a set of sampleSize integers without replacement from the range [0,...,n-1]. 
     * @param sampleSize The number of integers to return.
     * @param n The number of integers from which to sample.
     * @return The sample as an unsorted integer array.
     */
    public static int[] sampleWithoutReplacement(int sampleSize, int n) {
        // This implements a modified form of the genshuf() function from
        // Programming Pearls pg. 129.
        
        // TODO: Design a faster method that only generates min(m, n-m) integers.
        
        int[] array = IntSort.getIndexArray(n);
        for (int i=0; i<sampleSize; i++) {
            int j = Prng.nextInt(n - i) + i;
            // Swap array[i] and array[j]
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
        return Arrays.copyOf(array, sampleSize);
    }

    /**
     * Samples a set of sampleSize integers without replacement from the range [0,...,n-1]. 
     * @param sampleSize The number of integers to return.
     * @param n The number of integers from which to sample.
     * @return A boolean array, where sampled indices i have bools[i] = true.
     */
    public static boolean[] sampleWithoutReplacementBooleans(int sampleSize, int n) {
        int[] indices = sampleWithoutReplacement(sampleSize, n);
        Arrays.sort(indices);
        assert indices.length == sampleSize;
        boolean[] bools = new boolean[n];
        int j=0;
        for (int i=0; i<bools.length; i++) {
            if (j < indices.length && i==indices[j]) {
                bools[i] = true;
                j++;
            }
        }
        return bools;
    }
    
}
