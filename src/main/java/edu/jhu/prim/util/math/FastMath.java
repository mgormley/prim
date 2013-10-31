package edu.jhu.prim.util.math;


public class FastMath {

    public static int factorial(int n)
    {
        if( n <= 1 ) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    /**
     * Adds two probabilities that are stored as log probabilities.
     * @param x log(p)
     * @param y log(q)
     * @return log(p + q) = log(exp(x) + exp(y))
     */
    public static double logAdd(double x, double y) {
        if (FastMath.useLogAddTable) {
            return LogAddTable.logAdd(x,y);
        } else {
            return FastMath.logAddExact(x,y);
        }
    }

    /**
     * Subtracts two probabilities that are stored as log probabilities. 
     * Note that x >= y.
     * 
     * @param x log(p)
     * @param y log(q)
     * @return log(p - q) = log(exp(x) - exp(y))
     * @throws IllegalStateException if x < y
     */
    public static double logSubtract(double x, double y) {
        if (FastMath.useLogAddTable) {
            return LogAddTable.logSubtract(x,y);
        } else {
            return FastMath.logSubtractExact(x,y);
        }
    }

    public static double logAddExact(double x, double y) {
    
        // p = 0 or q = 0, where x = log(p), y = log(q)
        if (Double.NEGATIVE_INFINITY == x) {
            return y;
        } else if (Double.NEGATIVE_INFINITY == y) {
            return x;
        }
    
        // p != 0 && q != 0
        if (y <= x) {
            return x + Math.log1p(FastMath.exp(y - x));
        } else {
            return y + Math.log1p(FastMath.exp(x - y));
        }
    }

    /**
     * Subtracts two probabilities that are stored as log probabilities. 
     * Note that x >= y.
     * 
     * @param x log(p)
     * @param y log(q)
     * @return log(p - q) = log(exp(p) + exp(q))
     * @throws IllegalStateException if x < y
     */
    public static double logSubtractExact(double x, double y) {
        if (x < y) {
            throw new IllegalStateException("x must be >= y. x=" + x + " y=" + y);
        }
        
        // p = 0 or q = 0, where x = log(p), y = log(q)
        if (Double.NEGATIVE_INFINITY == y) {
            return x;
        } else if (Double.NEGATIVE_INFINITY == x) {
            return y;
        }
    
        // p != 0 && q != 0
        return x + Math.log1p(-FastMath.exp(y - x));
    }

    public static double log(double d) {
        return Math.log(d);
    }

    public static double exp(double d) {
        return Math.exp(d);
    }

    public static double log2(double d) {
        return log(d) / FastMath.LOG2;
    }

    public static double logForIlp(double weight) {
        if (weight == 0.0 || weight == -0.0) {
            // CPLEX doesn't accept exponents larger than 37 -- it seems to be
            // cutting off at something close to the 32-bit float limit of
            // 3.4E38.
            // 
            // Before, we used -1E25 since we could add 1 trillion of these
            // together and stay in in the coefficient limit.
            //
            // Now, we use -1E10 because -1E25 causes numerical stability issues
            // for the simplex algorithm
            return -1E10;
        }
        return log(weight);
    }

    public static final double LOG2 = log(2);
    //@Opt(hasArg = true, description = "Whether to use a log-add table or log-add exact.")
    public static boolean useLogAddTable = false;

}
