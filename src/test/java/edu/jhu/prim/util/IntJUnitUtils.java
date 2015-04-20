package edu.jhu.prim.util;

import org.junit.Assert;

public class IntJUnitUtils {

    public static void assertArrayEquals(int[] a1, int[] a2) {
        Assert.assertEquals(a1.length, a2.length);
        for (int i=0; i<a1.length; i++) {
            Assert.assertEquals(a1[i], a2[i]);
        }
    }

}
