package edu.jhu.prim.sample;

import static org.junit.Assert.*;

import org.junit.Test;

public class SampleTest {

    @Test
    public void testSampleWithoutReplacement() {
        int[] indices = Sample.sampleWithoutReplacement(1, 3);
        assertEquals(1, indices.length);
    }
    

    @Test
    public void testSampleWithoutReplacementBooleans() {
        boolean[] indices = Sample.sampleWithoutReplacementBooleans(2, 3);
        assertEquals(3, indices.length);
        int numTrue = 0;
        for (int i=0; i<indices.length; i++) {
            if (indices[i]) {
                numTrue++;
            }
        }
        assertEquals(2, numTrue);
    }

}
