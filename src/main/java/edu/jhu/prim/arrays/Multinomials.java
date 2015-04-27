package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.Prng;
import edu.jhu.prim.util.math.FastMath;

/**
 * Utility class for treating double arrays as multinomial distributions.
 * 
 * @author mgormley
 */
public class Multinomials {

    private Multinomials() {
        // private constructor
    }

    public static double[] randomMultinomial(int length) {
        double[] props = new double[length];
        for (int i=0; i<props.length; i++) {
            props[i] = Prng.nextDouble();
        }
        normalizeProps(props);
        return props;
    }
    
    /** Normalize the proportions and return their sum. */
    public static double normalizeProps(double[] props) {
        double propSum = DoubleArrays.sum(props);
        if (propSum == 0) {
            for (int d = 0; d < props.length; d++) {
                props[d] = 1.0 / (double)props.length;
            }
        } else if (propSum == Double.POSITIVE_INFINITY) {
            int count = DoubleArrays.count(props, Double.POSITIVE_INFINITY);
            if (count == 0) {
                throw new RuntimeException("Unable to normalize since sum is infinite but contains no infinities: " + Arrays.toString(props));
            }
            double constant = 1.0 / (double) count;
            for (int d=0; d<props.length; d++) {
                if (props[d] == Double.POSITIVE_INFINITY) {
                    props[d] = constant;
                } else {
                    props[d] = 0.0;
                }
            }
        } else {
            for (int d = 0; d < props.length; d++) {
                props[d] /= propSum;
                assert(!Double.isNaN(props[d]));
            }
        }
        return propSum;
    }
    
    /** In the log-semiring, normalize the proportions and return their sum. */
    public static double normalizeLogProps(double[] logProps) {
        double logPropSum = DoubleArrays.logSum(logProps);
        if (logPropSum == Double.NEGATIVE_INFINITY) {
            double uniform = FastMath.log(1.0 / (double)logProps.length);
            for (int d = 0; d < logProps.length; d++) {
                logProps[d] = uniform;
            }
        } else if (logPropSum == Double.POSITIVE_INFINITY) {
            int count = DoubleArrays.count(logProps, Double.POSITIVE_INFINITY);
            if (count == 0) {
                throw new RuntimeException("Unable to normalize since sum is infinite but contains no infinities: " + Arrays.toString(logProps));
            }
            double constant = FastMath.log(1.0 / (double) count);
            for (int d=0; d<logProps.length; d++) {
                if (logProps[d] == Double.POSITIVE_INFINITY) {
                    logProps[d] = constant;
                } else {
                    logProps[d] = Double.NEGATIVE_INFINITY;
                }
            }
        } else {
            for (int d = 0; d < logProps.length; d++) {
                logProps[d] -= logPropSum;
                assert(!Double.isNaN(logProps[d]));
            }            
        }
        return logPropSum;
    }

    /**
     * Asserts that the parameters are log-normalized within some delta.
     */
    public static void assertLogNormalized(double[] logProps, double delta) {
        double logPropSum = DoubleArrays.logSum(logProps);
        assert(Primitives.equals(0.0, logPropSum, delta));
    }

    public static int sampleFromProportions(double[] dProp) {
        double dPropSum = 0;
        for (int d = 0; d < dProp.length; d++) {
            dPropSum += dProp[d];
        }
        int d;
        double rand = Prng.nextDouble() * dPropSum;
        double partialSum = 0;
        for (d = 0; d < dProp.length; d++) {
            partialSum += dProp[d];
            if (rand <= partialSum && dProp[d] != 0.0) {
                break;
            }
        }
    
        assert (d < dProp.length);
        return d;
    }

    public static boolean isMultinomial(double[] p, double delta) {
        return Primitives.equals(DoubleArrays.sum(p), 1.0, delta) && DoubleArrays.isInRange(p, 0.0, 1.0);
    }

    public static boolean isLogMultinomial(double[] p, double delta) {
        return Primitives.equals(DoubleArrays.logSum(p), 0.0, delta)
                && DoubleArrays.isInRange(p, Double.NEGATIVE_INFINITY, 0.0);
    }
    
    /**
     * Gets the KL divergence between two multinomial distributions p and q.
     * 
     * @param p Array representing a multinomial distribution, p.
     * @param q Array representing a multinomial distribution, q.
     * @return KL(p || q)
     */
    public static double klDivergence(double[] p, double[] q) {
        if (p.length != q.length) {
            throw new IllegalStateException("The length of p and q must be the same.");
        }
        double delta = 1e-8;
        if (!Multinomials.isMultinomial(p, delta)) {
            throw new IllegalStateException("p is not a multinomial");
        }
        if (!Multinomials.isMultinomial(q, delta)) {
            throw new IllegalStateException("q is not a multinomial");
        }
        
        double kl = 0.0;
        for (int i=0; i<p.length; i++) {
            if (p[i] == 0.0 || q[i] == 0.0) {
                continue;
            }
            kl += p[i] * FastMath.log(p[i] / q[i]);
        }
        return kl;
    }
        
}
