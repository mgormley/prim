package edu.jhu.prim.vector;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.map.IntDoubleHashMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;
import edu.jhu.prim.util.Lambda.FnIntDoubleToVoid;
import edu.jhu.prim.util.Lambda.LambdaBinOpDouble;
import edu.jhu.prim.util.SafeCast;

public class IntDoubleHashVector extends IntDoubleHashMap implements IntDoubleVector {

    private static final long serialVersionUID = 1L;

    public IntDoubleHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public IntDoubleHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    /** Copy constructor. */
    public IntDoubleHashVector(IntDoubleHashVector other) {
        super(other);
    }
    
    /** Copy constructor. */
    public IntDoubleHashVector(IntDoubleVector other) {
        this();
        final IntDoubleHashVector thisVec = this; 
        other.iterate(new FnIntDoubleToVoid() {
            @Override
            public void call(int idx, double val) {
                thisVec.set(idx, val);
            }
        });
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public IntDoubleVector copy() {
        return new IntDoubleHashVector(this);
    }
        
    @Override
    public double set(int idx, double val) {
        return put(idx, val);
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
                dot += values[i] * other[keys[i]];
            }
        }
        return dot;
    }

    @Override
    public double dot(IntDoubleVector y) {
        if (y instanceof IntDoubleHashVector) {
            IntDoubleHashVector other = ((IntDoubleHashVector) y);
            if (other.size() < this.size()) {
                return other.dot(this);
            }
            return dotWithoutCaveats(other);
        } else if (y instanceof IntDoubleSortedVector) {
            // TODO: If the size of the HashVector is much smaller than the size
            // of the SortedVector and neither is very large, we might want to
            // instead loop over the HashVector.
            return y.dot(this);
        } else {
            return dotWithoutCaveats(y);
        }
    }

    private double dotWithoutCaveats(IntDoubleVector other) {
        double dot = 0;
        for (int i=0; i<this.keys.length; i++) {
            if (this.states[i] == FULL) {
                dot += this.values[i] * other.get(keys[i]);
            }
        }
        return dot;
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(IntDoubleVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.DoubleAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntDoubleVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.DoubleSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntDoubleVector other) { 
        // TODO: This is correct, but will create lots of zero entries and not remove any.
        // Also this will be very slow if other is a IntDoubleSortedVector since it will have to
        // call get() each time.
        this.apply(new SparseBinaryOpApplier2(this, other, new Lambda.DoubleProd()));
    }

    public int getNumImplicitEntries() {
        int max = Integer.MIN_VALUE;
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL && keys[i] > max) {
                 max = keys[i];
            }
        }        
        return Math.max(0, max + 1);
    }
    
    /** Gets a NEW array containing all the elements in this vector. */
    public double[] toNativeArray() {
        final double[] arr = new double[getNumImplicitEntries()];
        iterate(new FnIntDoubleToVoid() {
            @Override
            public void call(int idx, double val) {
                arr[idx] = val;
            }
        });
        return arr;
    }
    
    public static class SparseBinaryOpApplier implements FnIntDoubleToVoid {
        
        private IntDoubleVector modifiedVector;
        private LambdaBinOpDouble lambda;
        
        public SparseBinaryOpApplier(IntDoubleVector modifiedVector, LambdaBinOpDouble lambda) {
            this.modifiedVector = modifiedVector;
            this.lambda = lambda;
        }
        
        public void call(int idx, double val) {
            modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
        }
        
    }
    
    private static class SparseBinaryOpApplier2 implements FnIntDoubleToDouble {
        
        private IntDoubleVector vec1;
        private IntDoubleVector vec2;
        private LambdaBinOpDouble lambda;
        
        public SparseBinaryOpApplier2(IntDoubleVector vec1, IntDoubleVector vec2, LambdaBinOpDouble lambda) {
            this.vec1 = vec1;
            this.vec2 = vec2;
            this.lambda = lambda;
        }
        
        public double call(int idx, double val) {
            return lambda.call(vec1.get(idx), vec2.get(idx));
        }
        
    }

}
