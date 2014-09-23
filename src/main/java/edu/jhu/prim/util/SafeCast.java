package edu.jhu.prim.util;

public class SafeCast {

    private static long MAX_UNSIGNED_BYTE = 0xff;
    private static long MAX_UNSIGNED_SHORT = 0xffff;
    private static long MAX_UNSIGNED_INT = 0xffffffff;
    private static long MAX_UNSIGNED_LONG = 0xffffffffffffffffl;
    
    private SafeCast() {
        // Private constructor.
    }

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
    
    public static byte safeIntToUnsignedByte(int i) {
        if (i > (int) MAX_UNSIGNED_BYTE || i < (int) 0) {
            throw new IllegalStateException("Cannot convert int to unsigned byte: " + i);
        }
        return (byte)i;
    }
    
    public static short safeIntToUnsignedShort(int i) {
        if (i > (int) MAX_UNSIGNED_SHORT || i < (int) 0) {
            throw new IllegalStateException("Cannot convert int to unsigned short: " + i);
        }
        return (short)i;
    }

    public static int safeUnsignedByteToInt(byte b) {
        return b & 0xff;
    }
    
    public static int safeUnsignedShortToInt(short s) {
        return s & 0xffff;
    }
    
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
