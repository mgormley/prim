package edu.jhu.prim.sample;

import java.util.Arrays;

import edu.jhu.prim.sort.IntSort;
import edu.jhu.util.Prng;

public class Sample {

    private Sample() {
        // private constructor.
    }

    /**
     * Samples a set of m integers without replacement from the range [0,...,n-1]. 
     * @param m The number of integers to return.
     * @param n The number of integers from which to sample.
     * @return The sample as an unsorted integer array.
     */
    public static int[] sampleWithoutReplacement(int m, int n) {
        // This implements a modified form of the genshuf() function from
        // Programming Pearls pg. 129.
        
        // TODO: Design a faster method that only generates min(m, n-m) integers.
        
        int[] array = IntSort.getIndexArray(n);
        for (int i=0; i<m; i++) {
            int j = Prng.nextInt(n - i) + i;
            // Swap array[i] and array[j]
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
        return Arrays.copyOf(array, m);
    }

}
