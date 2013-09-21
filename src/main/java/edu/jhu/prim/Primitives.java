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


    /**
     * Counts the number of indices that appear in both arrays.
     * @param indices1 Sorted array of indices.
     * @param indices2 Sorted array of indices.
     */
    public static int countCommon(long[] indices1, long[] indices2) {
        int numCommonIndices = 0;
        int i = 0;
        int j = 0;
        while (i < indices1.length && j < indices2.length) {
            if (indices1[i] < indices2[j]) {
                i++;
            } else if (indices2[j] < indices1[i]) {
                j++;
            } else {
                numCommonIndices++;
                // Equal indices.
                i++;
                j++;
            }
        }
        for (; i < indices1.length; i++) {
            numCommonIndices++;
        }
        for (; j < indices2.length; j++) {
            numCommonIndices++;
        }
        return numCommonIndices;
    }
    
    /**
     * Counts the number of unique indices in two arrays.
     * @param indices1 Sorted array of indices.
     * @param indices2 Sorted array of indices.
     */
    public static int countUnique(long[] indices1, long[] indices2) {
        int numUniqueIndices = 0;
        int i = 0;
        int j = 0;
        while (i < indices1.length && j < indices2.length) {
            if (indices1[i] < indices2[j]) {
                numUniqueIndices++;
                i++;
            } else if (indices2[j] < indices1[i]) {
                numUniqueIndices++;
                j++;
            } else {
                // Equal indices.
                i++;
                j++;
            }
        }
        for (; i < indices1.length; i++) {
            numUniqueIndices++;
        }
        for (; j < indices2.length; j++) {
            numUniqueIndices++;
        }
        return numUniqueIndices;
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
