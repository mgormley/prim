package edu.jhu.prim.util;

import org.junit.Assert;

public class ShortJUnitUtils {

    public static void assertArrayEquals(short[] a1, short[] a2) {
        Assert.assertEquals(a1.length, a2.length);
        for (int i=0; i<a1.length; i++) {
            Assert.assertEquals(a1[i], a2[i]);
        }
    }

}
