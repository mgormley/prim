package edu.jhu.prim.util;

import edu.jhu.prim.Primitives;

public class SafeCast {
    
    private SafeCast() {
        // Private constructor.
    }
    
    /* ---- Convert 4-byte signed int to fewer bytes. ----- */

    public static byte safeIntToByte(int i) {
        if (i > (int)Byte.MAX_VALUE || i < (int) Byte.MIN_VALUE) {
            throw new IllegalStateException("Cannot convert int to byte: " + i);
        }
        return (byte)i;
    }
    
    public static short safeIntToShort(int i) {
        if (i > (int)Short.MAX_VALUE || i < (int) Short.MIN_VALUE) {
            throw new IllegalStateException("Cannot convert int to short: " + i);
        }
        return (short)i;
    }
    
    /* ---- Convert 8-byte signed long to fewer bytes. ----- */
    
    public static int safeLongToInt(long l) {
        if (l > (long)Integer.MAX_VALUE || l < (long) Integer.MIN_VALUE) {
            throw new IllegalStateException("Cannot convert long to int: " + l);
        }
        return (int)l;
    }
    
    public static int safeDoubleToInt(double d) {
        if (d > (double)Integer.MAX_VALUE || d < (double) Integer.MIN_VALUE) {
            throw new IllegalStateException("Cannot convert double to int: " + d);
        }
        return (int)d;
    }

    public static float safeDoubleToFloat(double d) {
        if (Math.abs(d) > (double)Float.MAX_VALUE || Math.abs(d) < (double)Float.MIN_VALUE) {
            throw new IllegalStateException("Cannot convert double to float: " + d);
        }
        return (float)d;
    }

    public static byte safeIntToUnsignedByte(int i) {
        if (i > Primitives.INT_MAX_UBYTE || i < (int) 0) {
            throw new IllegalStateException("Cannot convert int to unsigned byte: " + i);
        }
        return (byte)i;
    }
    
    public static short safeIntToUnsignedShort(int i) {
        if (i > Primitives.INT_MAX_USHORT || i < (int) 0) {
            throw new IllegalStateException("Cannot convert int to unsigned short: " + i);
        }
        return (short)i;
    }

    public static int safeUnsignedByteToInt(byte b) {
        return b & Primitives.INT_MAX_UBYTE;
    }
    
    public static int safeUnsignedShortToInt(short s) {
        return s & Primitives.INT_MAX_USHORT;
    }
    
    public static long safeUnsignedIntToLong(int i) {
        return i & Primitives.LONG_MAX_UINT;
    }
        
    public static int[] safeLongToInt(long[] longArray) {
        int[] intArray = new int[longArray.length];
        for (int i=0; i<longArray.length; i++) {
            intArray[i] = safeLongToInt(longArray[i]);
        }
        return intArray;
    }
    
    public static long[] toLong(int[] intArray) {
        long[] longArray = new long[intArray.length];
        for (int i=0; i<intArray.length; i++) {
            longArray[i] = intArray[i];
        }
        return longArray;
    }
    
}
