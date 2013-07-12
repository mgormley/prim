package edu.jhu.prim;

/**
 * NOTICE: These functions are from FastMath in Apache Commons Math.
 * @author mgormley
 */
public class FastMath {

    /** 2^52 - double numbers this large must be integral (no fraction) or NaN or Infinite */
    private static final double TWO_POWER_52 = 4503599627370496.0;

    /** Get the smallest whole number larger than x.
     * @param x number from which ceil is requested
     * @return a double number c such that c is an integer c - 1.0 < x <= c
     */
    public static double ceil(double x) {
        double y;

        if (x != x) { // NaN
            return x;
        }

        y = floor(x);
        if (y == x) {
            return y;
        }

        y += 1.0;

        if (y == 0) {
            return x*y;
        }

        return y;
    }
    
    /** Get the largest whole number smaller than x.
     * @param x number from which floor is requested
     * @return a double number f such that f is an integer f <= x < f + 1.0
     */
    public static double floor(double x) {
        long y;

        if (x != x) { // NaN
            return x;
        }

        if (x >= TWO_POWER_52 || x <= -TWO_POWER_52) {
            return x;
        }

        y = (long) x;
        if (x < 0 && y != x) {
            y--;
        }

        if (y == 0) {
            return x*y;
        }

        return y;
    }
    
}
