package edu.jhu.prim;

import java.io.Serializable;

import edu.jhu.prim.util.SafeCast;


/**
 * Methods and constants for primitive collections.
 * @author mgormley
 */
public class Primitives {
    
    /** The default value for missing entries. */
    public static final double DEFAULT_MISSING_ENTRY_DOUBLE = Double.NaN;
    public static final float DEFAULT_MISSING_ENTRY_FLOAT = Float.NaN;
    public static final long DEFAULT_MISSING_ENTRY_LONG = 0;
    public static final int DEFAULT_MISSING_ENTRY_INT = 0;
    
    public static int LONG_NUM_BITS = 64;
    public static int INT_NUM_BITS = 32;
    public static final double DEFAULT_DOUBLE_DELTA = 1e-13;
    public static final float DEFAULT_FLOAT_DELTA = 1e-13f;
    

    /** Bit masks. */
    public static long LONG_MAX_UBYTE = 0xffL;
    public static long LONG_MAX_USHORT = 0xffffL;
    public static long LONG_MAX_UINT = 0xffffffffL;
    public static long LONG_MAX_ULONG = 0xffffffffffffffffL;
    //
    public static int INT_MAX_UBYTE = 0xff;
    public static int INT_MAX_USHORT = 0xffff;
    public static int INT_MAX_UINT = 0xffffffff;
    //
    public static short SHORT_MAX_UBYTE = (short) 0xff;
    public static short SHORT_MAX_USHORT = (short) 0xffff;
    //
    public static byte BYTE_MAX_UBYTE = (byte) 0xff;
    
    private Primitives() {
        // Private constructor.
    }
    
    /* ------------------- Algorithms ---------------------- */
        
    /**
     * Compute the hash value of a key
     * @param key key to hash
     * @return hash value of the key
     */
    public static int hashOfLong(final long key) {
        // Gets the exclusive-or of the hash values of the first and second 32
        // bits of the long.
        return (int) (hashOfInt((int)key) ^ hashOfInt((int) (key >>> 32)));
    }
    
    /**
     * Compute the hash value of a key
     * @param key key to hash
     * @return hash value of the key
     */
    public static int hashOfInt(final int key) {
        final int h = key ^ ((key >>> 20) ^ (key >>> 12));
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    
    public static int hashOfShort(final short key) {
        return hashOfInt(key);
    }
    
    public static int hashOfByte(final byte key) {
        return hashOfInt(key);
    }

    public static int hashOfDouble(final double key) {
        return hashOfLong(Double.doubleToLongBits(key));
    }

    public static int hashOfFloat(final float key) {
        return hashOfInt(Float.floatToIntBits(key));
    }
    
    /* ------------------- Tests ---------------------- */
    
    public static boolean isZero(int val) {
        return val == 0;
    }
    
    public static boolean isZero(long val) {
        return val == 0;
    }
    
    public static boolean isZero(double val, double zeroThreshold) {
        zeroThreshold = Math.abs(zeroThreshold);
        return -zeroThreshold <= val && val <= zeroThreshold;
    }
    
    public static boolean isZero(float val, float zeroThreshold) {
        zeroThreshold = Math.abs(zeroThreshold);
        return -zeroThreshold <= val && val <= zeroThreshold;
    }
    
    /* --------------------- Long Form Casting ------------------- */

    public static long toLong(int d) {
        return (long)d;
    }

    public static double toDouble(int i) {
        return (double)i;
    }

    public static float toFloat(int i) {
        return (float)i;
    }

    public static int toInt(long d) {
        return (int)d;
    }
    
    public static int toInt(double d) {
        return (int)d;
    }
    
    public static int[] toInts(int... b) {
        int[] a = new int[b.length];
        for (int i=0; i<b.length; i++) {
            a[i] = b[i];
        }
        return a;
    }
    
    public static double[] toDoubles(int... b) {
        double[] a = new double[b.length];
        for (int i=0; i<b.length; i++) {
            a[i] = b[i];
        }
        return a;
    }
    
    public static float[] toFloats(int... b) {
        float[] a = new float[b.length];
        for (int i=0; i<b.length; i++) {
            a[i] = b[i];
        }
        return a;
    }

    /* --------------------- Equality and Comparison ------------------- */

    public static boolean equals(int a, int b) {
        return a == b;
    }

    public static boolean equals(long a, long b) {
        return a == b;
    }

    public static boolean equals(double a, double b, double delta) {
        if (a == b) {
            // This case is needed for infinity equality.
            return true;
        }
        return Math.abs(a - b) < delta;
    }
    
    public static boolean equals(float a, float b, float delta) {
        if (a == b) {
            // This case is needed for infinity equality.
            return true;
        }
        return Math.abs(a - b) < delta;
    }

    /**
     * Compares two double values up to some delta.
     * @return The value 0 if a equals b, a value greater than 0 if if a > b, and a value less than 0 if a < b.  
     */
    public static int compare(double a, double b, double delta) {
        if (equals(a, b, delta)) {
            return 0;
        }
        return Double.compare(a, b);
    }
    
    /**
     * Compares two float values up to some delta.
     * @return The value 0 if a equals b, a value greater than 0 if if a > b, and a value less than 0 if a < b.  
     */
    public static int compare(float a, float b, float delta) {
        if (equals(a, b, delta)) {
            return 0;
        }
        return Float.compare(a, b);
    }

    public static int compare(int a, int b) {
        return a - b;
    }

    public static boolean lte(double a, double b) {
        return a <= b + Primitives.DEFAULT_DOUBLE_DELTA;
    }

    public static boolean lte(double a, double b, double delta) {
        return a <= b + delta;
    }

    public static boolean gte(double a, double b) {
        return a + Primitives.DEFAULT_DOUBLE_DELTA >= b;
    }

    public static boolean gte(double a, double b, double delta) {
        return a + delta >= b;
    }

    public static void assertDoubleEquals(double a, double b) {
        if (a == b) {
            // This check is needed to ensure infinity equality.
            return;
        }
        assert(Math.abs(a - b) < 0.000000000001);
    }
    
    public static boolean lte(float a, float b) {
        return a <= b + Primitives.DEFAULT_FLOAT_DELTA;
    }

    public static boolean lte(float a, float b, float delta) {
        return a <= b + delta;
    }

    public static boolean gte(float a, float b) {
        return a + Primitives.DEFAULT_FLOAT_DELTA >= b;
    }

    public static boolean gte(float a, float b, float delta) {
        return a + delta >= b;
    }

    public static void assertFloatEquals(float a, float b) {
        if (a == b) {
            // This check is needed to ensure infinity equality.
            return;
        }
        assert(Math.abs(a - b) < 0.000000000001);
    }
    
    public static class MutableLong implements Serializable {
        private static final long serialVersionUID = 1L;
        public long v;
        public MutableLong() { }
        public MutableLong(long v) { this.v = v; }
        public long sqrt() {
            return SafeCast.safeDoubleToLong(Math.sqrt(v));
        }
    }

    public static class MutableInt implements Serializable {
        private static final long serialVersionUID = 1L;
        public int v;
        public MutableInt() { }
        public MutableInt(int v) { this.v = v; }
        public int sqrt() {
            return SafeCast.safeDoubleToInt(Math.sqrt(v));
        }
    }
    
    public static class MutableShort implements Serializable {
        private static final long serialVersionUID = 1L;
        public short v;
        public MutableShort() { }
        public MutableShort(short v) { this.v = v; }
        public short sqrt() {
            return SafeCast.safeIntToShort(SafeCast.safeDoubleToInt(Math.sqrt(v)));
        }
    }
    
    public static class MutableDouble implements Serializable {
        private static final long serialVersionUID = 1L;
        public double v;
        public MutableDouble() { }
        public MutableDouble(double v) { this.v = v; }
        public double sqrt() {
            return Math.sqrt(v);
        }
    }

    public static class MutableFloat implements Serializable {
        private static final long serialVersionUID = 1L;
        public float v;
        public MutableFloat() { }
        public MutableFloat(float v) { this.v = v; }
        public float sqrt() {
            return SafeCast.safeDoubleToFloat(Math.sqrt(v));
        }
    }
    
 }
