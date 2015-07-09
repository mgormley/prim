package edu.jhu.prim.util.math;

/**
 * A port of Tim Vieira's log-add table with linear interpolation, which is itself a port of Matt
 * Gormley's port of Jason Smith's log add table implementation.
 * 
 * This version of Tim's code includes a few additional tweaks to increase precision at the
 * boundaries of the function by backing off to the exact version of the function.
 * 
 * @author mgormley
 */
public class SmoothedLogAddTable {

    // Settings: 
    //  - For precision of 1e-11, use MAX=-4 and TBL=65536*32.
    //  - For precision of 1e-8,  use MAX=-2 and TBL=65536*4.
    
    static final double MIN = -128;
    static final double MAX = -4;
    private static final double WID = MAX-MIN;
    // The number of entries in the table.
    private static final int TBL = 65536*32;
    private static final double INC = TBL*1.0/WID;
    // Log-add table.
    private static final double[] laTbl = new double[TBL+2];
    // Log-subtract table.
    private static final double[] lsTbl = new double[TBL+2];
    
    static {
        for (int i=0; i<TBL+2; i++) {
            laTbl[i] = FastMath.logAddExact(0, i / INC + MIN);
            if (i < TBL+1) {
                lsTbl[i] = FastMath.logSubtractExact(0, i / INC + MIN);
            } else {
                // This case is only hit when (x - i) == 0 and so we won't.
                lsTbl[i] = 0.0;
            }
        }
    }

    public static double logAdd(double a, double b) {
        if (Double.NEGATIVE_INFINITY == a) {
            return b;
        } else if (Double.NEGATIVE_INFINITY == b) {
            return a;
        } else if (a == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        } else if (b == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        
        if (b > a) {
            // Swap a and b.
            double temp = a;
            a = b;
            b = temp;
        }

        double negDiff = b - a;
        if (negDiff > MAX) {
            return FastMath.logAddExact(a, b);
        } else if (negDiff < MIN) {
            // The function behaves linearly below this point.
            return a;
        }
        double x = (((negDiff - MIN) * INC));
        int i = (int) x; // Round down.
        
        //System.out.printf("TBL-i=%d i=%d negDiff=%g ", TBL-i, i, negDiff);
        return a + laTbl[i] + (x - i)*(laTbl[i+1] - laTbl[i]);
    }

    public static double logSubtract(double a, double b) {
        if (b > a) {            
            throw new IllegalStateException("a must be >= b. a=" + a + " b=" + b);
        }
        if (Double.NEGATIVE_INFINITY == b) {
            return a;
        } else if (Double.NEGATIVE_INFINITY == a) {
            return b;
        } else if (a == b) {
            return Double.NEGATIVE_INFINITY;
        }
        
        double negDiff = b - a;
        if (negDiff > MAX) {
            return FastMath.logSubtractExact(a, b);
        } else if (negDiff < MIN) {
            // The function behaves linearly below this point.
            return a;
        }
        double x = (((negDiff - MIN) * INC));
        int i = (int) x; // Round down.            
        //System.out.printf("TBL-i=%d i=%d negDiff=%g ", TBL-i, i, negDiff);
        if (x == i) {
            // This case is for when we don't use any logSubtractExact calls.
            // It ensures that lsTbl[i+1] won't be out of bounds.
            return a + lsTbl[i];
        } else {
            return a + lsTbl[i] + (x - i)*(lsTbl[i+1] - lsTbl[i]);
        }
    }

}
