package edu.jhu.prim.util;

import org.junit.Assert;

public class ByteJUnitUtils {

    public static void assertArrayEquals(byte[] a1, byte[] a2) {
        Assert.assertEquals(a1.length, a2.length);
        for (int i=0; i<a1.length; i++) {
            Assert.assertEquals(a1[i], a2[i]);
        }
    }

}
