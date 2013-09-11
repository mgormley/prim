package edu.jhu.prim;

/**
 * Methods and constants for primitive collections.
 * @author mgormley
 */
public class Primitives {
    
    /** The default value for missing entries. */
    public static final double DEFAULT_MISSING_ENTRY_DOUBLE = Double.NaN;
    public static final long DEFAULT_MISSING_ENTRY_LONG = 0;
    public static final int DEFAULT_MISSING_ENTRY_INT = 0;
    
    public static int LONG_NUM_BITS = 64;
    public static int INT_NUM_BITS = 32;
    
    private Primitives() {
        // Private constructor.
    }
    
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
    
    /* --------------------- Long Form Casting ------------------- */

    public static long toLong(int d) {
        return (long)d;
    }

    public static double toDouble(int i) {
        return (double)i;
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
 }
