package edu.jhu.prim.arrays;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultinomialsTest {

    @Test
    public void testKLDivergence() {
        double[] p = new double[]{0.2, 0.3, 0.5};
        double[] q = new double[]{0.1, 0.4, 0.5};
        
        assertEquals(0.05232481437645474, Multinomials.klDivergence(p, q), 1e-10);
        assertEquals(0.2 * Math.log(0.2 / 0.1) + 0.3 * Math.log(0.3 / 0.4) + 0.5 * Math.log(0.5 / 0.5), 
                Multinomials.klDivergence(p, q), 1e-10);
    }

}
