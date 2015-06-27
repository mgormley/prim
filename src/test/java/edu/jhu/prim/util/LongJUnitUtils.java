package edu.jhu.prim.util;

import java.util.Arrays;

import org.junit.Assert;

public class LongJUnitUtils {

    public static void assertArrayEquals(long[][][] a1, long[][][] a2) {
        assertSameSize(a1, a2);
        for (int i=0; i<a1.length; i++) {
            assertArrayEquals(a1[i], a2[i]);
        }
    }
    
    public static void assertArrayEquals(long[][] a1, long[][] a2) {
        assertSameSize(a1, a2);
        for (int i=0; i<a1.length; i++) {
            assertArrayEquals(a1[i], a2[i]);
        }
    }
    
    public static void assertArrayEquals(long[] a1, long[] a2) {
        Assert.assertEquals(a1.length, a2.length);
        for (int i=0; i<a1.length; i++) {
            String msg = String.format("expected:<%s> but was:<%s>", Arrays.toString(a1), Arrays.toString(a2));
            Assert.assertEquals(msg, a1[i], a2[i]);
        }
    }

    public static void assertSameSize(long[][][] newLogPhi, long[][][] logPhi) {
        Assert.assertEquals(newLogPhi.length, logPhi.length);
        for (int k=0; k<logPhi.length; k++) {
            Assert.assertEquals(newLogPhi[k].length, logPhi[k].length); 
        }
    }
    
    public static void assertSameSize(long[][] newLogPhi, long[][] logPhi) {
        Assert.assertEquals(newLogPhi.length, logPhi.length);
        for (int k=0; k<logPhi.length; k++) {
            Assert.assertEquals(newLogPhi[k].length, logPhi[k].length); 
        }
    }

}
