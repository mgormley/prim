package edu.jhu.prim.arrays;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrimitivesTest {


    @Test
    public void testJavaDoubleRanges() {        
        assertEquals(1.7976931348623157e308, Double.MAX_VALUE, 1e-13);
        assertEquals(4.9e-324, Double.MIN_VALUE, 1e-13);
        
        assertEquals(1023, Double.MAX_EXPONENT);
        assertEquals(-1022, Double.MIN_EXPONENT);
        
        assertEquals(64, Double.SIZE);
        
        assertEquals(4.49423283715579e307, Math.pow(2, 1022), 1e-13);
        // The maximum value for x such that exp(x) != Double.POSITIVE_INFINITY.
        assertEquals(709.78, Math.log(Double.MAX_VALUE), 1e-2);
        assertEquals(Double.POSITIVE_INFINITY, Math.exp(709.79), 1e-2);
    }
    
    @Test
    public void testJavaAddSubtractWithInfs() {
        assertEquals(Double.POSITIVE_INFINITY, 0 + Double.POSITIVE_INFINITY, 1e-13);
        assertEquals(Double.POSITIVE_INFINITY, 2 + Double.POSITIVE_INFINITY, 1e-13);
        assertEquals(Double.POSITIVE_INFINITY, -2 + Double.POSITIVE_INFINITY, 1e-13);
        assertEquals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY + Double.POSITIVE_INFINITY, 1e-13);
        assertEquals(Double.NaN, Double.NEGATIVE_INFINITY + Double.POSITIVE_INFINITY, 1e-13);
        
        assertEquals(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY - Double.POSITIVE_INFINITY, 1e-13);
        assertEquals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY - Double.NEGATIVE_INFINITY, 1e-13);
        assertEquals(Double.NaN, Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY, 1e-13);
        assertEquals(Double.NaN, Double.NEGATIVE_INFINITY - Double.NEGATIVE_INFINITY, 1e-13);
    }

}
