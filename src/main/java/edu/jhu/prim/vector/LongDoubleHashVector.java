package edu.jhu.prim.vector;

import edu.jhu.prim.map.LongDoubleHashMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;
import edu.jhu.prim.util.Lambda.LambdaBinOpDouble;
import edu.jhu.prim.util.SafeCast;

public class LongDoubleHashVector extends LongDoubleHashMap implements LongDoubleVector {

    private static final long serialVersionUID = 1L;

    public LongDoubleHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public LongDoubleHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    /** Copy constructor. */
    public LongDoubleHashVector(LongDoubleHashVector other) {
        super(other);
    }
    
    /** Copy constructor. */
    public LongDoubleHashVector(LongDoubleVector other) {
        this();
        final LongDoubleHashVector thisVec = this; 
        other.apply(new FnLongDoubleToDouble() {            
            @Override
            public double call(long idx, double val) {
                thisVec.set(idx, val);
                return val;
            }
        });
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public LongDoubleVector copy() {
        return new LongDoubleHashVector(this);
    }
        
    @Override
    public void set(long idx, double val) {
        put(idx, val);
    }

    @Override
    public void scale(double multiplier) {
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                values[i] = multiplier * values[i];
            }
        }
    }

    @Override
    public double dot(double[] other) {
        double dot = 0;
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL && keys[i] <= Integer.MAX_VALUE) {
                dot += values[i] * other[SafeCast.safeLongToInt(keys[i])];
            }
        }
        return dot;
    }

    @Override
    public double dot(LongDoubleVector y) {
        if (y instanceof LongDoubleHashVector) {
            LongDoubleHashVector other = ((LongDoubleHashVector) y);
            if (other.size() < this.size()) {
                return other.dot(this);
            }
            return dotWithoutCaveats(other);
        } else if (y instanceof LongDoubleSortedVector) {
            // TODO: If the size of the HashVector is much smaller than the size
            // of the SortedVector and neither is very large, we might want to
            // instead loop over the HashVector.
            return y.dot(this);
        } else {
            return dotWithoutCaveats(y);
        }
    }

    private double dotWithoutCaveats(LongDoubleVector other) {
        double dot = 0;
        for (int i=0; i<this.keys.length; i++) {
            if (this.states[i] == FULL) {
                dot += this.values[i] * other.get(keys[i]);
            }
        }
        return dot;
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(LongDoubleVector other) {
        other.apply(new SparseBinaryOpApplier(this, new Lambda.DoubleAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(LongDoubleVector other) {
        other.apply(new SparseBinaryOpApplier(this, new Lambda.DoubleSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(LongDoubleVector other) { 
        // TODO: This is correct, but will create lots of zero entries and not remove any.
        // Also this will be very slow if other is a LongDoubleSortedVector since it will have to
        // call get() each time.
        this.apply(new SparseBinaryOpApplier2(this, other, new Lambda.DoubleProd()));
    }
    
    public static class SparseBinaryOpApplier implements FnLongDoubleToDouble {
        
        private LongDoubleVector modifiedVector;
        private LambdaBinOpDouble lambda;
        
        public SparseBinaryOpApplier(LongDoubleVector modifiedVector, LambdaBinOpDouble lambda) {
            this.modifiedVector = modifiedVector;
            this.lambda = lambda;
        }
        
        public double call(long idx, double val) {
            modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
            return val;
        }
        
    }
    
    private static class SparseBinaryOpApplier2 implements FnLongDoubleToDouble {
        
        private LongDoubleVector vec1;
        private LongDoubleVector vec2;
        private LambdaBinOpDouble lambda;
        
        public SparseBinaryOpApplier2(LongDoubleVector vec1, LongDoubleVector vec2, LambdaBinOpDouble lambda) {
            this.vec1 = vec1;
            this.vec2 = vec2;
            this.lambda = lambda;
        }
        
        public double call(long idx, double val) {
            return lambda.call(vec1.get(idx), vec2.get(idx));
        }
        
    }

}
