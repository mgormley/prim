package edu.jhu.prim.arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.math.FastMath;
import edu.jhu.util.Prng;

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
    
    public static void normalizeProps(double[] props) {
        double propSum = DoubleArrays.sum(props);
        if (propSum != 0) {
            for (int d = 0; d < props.length; d++) {
                props[d] /= propSum;
                assert(!Double.isNaN(props[d]));
            }
        } else {
            for (int d = 0; d < props.length; d++) {
                props[d] = 1.0 / (double)props.length;
            }
        }
    }
    
    public static void normalizeLogProps(double[] logProps) {
        double logPropSum = DoubleArrays.logSum(logProps);
        if (logPropSum != Double.NEGATIVE_INFINITY) {
            for (int d = 0; d < logProps.length; d++) {
                logProps[d] -= logPropSum;
                assert(!Double.isNaN(logProps[d]));
            }
        } else {
            double uniform = FastMath.log(1.0 / (double)logProps.length);
            for (int d = 0; d < logProps.length; d++) {
                logProps[d] = uniform;
            }
        }
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
