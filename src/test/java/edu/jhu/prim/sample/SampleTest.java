package edu.jhu.prim.sample;

import static org.junit.Assert.*;

import org.junit.Test;

public class SampleTest {

    @Test
    public void testSampleWithoutReplacement() {
        int[] indices = Sample.sampleWithoutReplacement(15, 30);
        assertEquals(15, indices.length);
    }
    

    @Test
    public void testSampleWithoutReplacementBooleans() {
        boolean[] indices = Sample.sampleWithoutReplacementBooleans(20, 30);
        assertEquals(30, indices.length);
        int numTrue = 0;
        for (int i=0; i<indices.length; i++) {
            if (indices[i]) {
                numTrue++;
            }
        }
        assertEquals(20, numTrue);
    }

}
