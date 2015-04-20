package edu.jhu.prim.util;

import java.util.Arrays;

import org.junit.Assert;

public class FloatJUnitUtils {

    private FloatJUnitUtils() {
        // private constructor
    }

    public static void assertArrayEquals(float[][][] a1, float[][][] a2, float delta) {
        assertSameSize(a1, a2);
        for (int i=0; i<a1.length; i++) {
            assertArrayEquals(a1[i], a2[i], delta);
        }
    }
    
    public static void assertArrayEquals(float[][] a1, float[][] a2, float delta) {
        assertSameSize(a1, a2);
        for (int i=0; i<a1.length; i++) {
            assertArrayEquals(a1[i], a2[i], delta);
        }
    }
    
    public static void assertArrayEquals(float[] a1, float[] a2, float delta) {
        Assert.assertEquals(a1.length, a2.length);
        for (int i=0; i<a1.length; i++) {
            String msg = String.format("expected:<%s> but was:<%s>", Arrays.toString(a1), Arrays.toString(a2));
            Assert.assertEquals(msg, a1[i], a2[i], delta);
        }
    }

    public static void assertSameSize(float[][][] newLogPhi, float[][][] logPhi) {
        Assert.assertEquals(newLogPhi.length, logPhi.length);
        for (int k=0; k<logPhi.length; k++) {
            Assert.assertEquals(newLogPhi[k].length, logPhi[k].length); 
        }
    }
    
    public static void assertSameSize(float[][] newLogPhi, float[][] logPhi) {
        Assert.assertEquals(newLogPhi.length, logPhi.length);
        for (int k=0; k<logPhi.length; k++) {
            Assert.assertEquals(newLogPhi[k].length, logPhi[k].length); 
        }
    }
    
}
