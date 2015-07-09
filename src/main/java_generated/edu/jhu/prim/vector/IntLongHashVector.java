package edu.jhu.prim.vector;

import edu.jhu.prim.map.IntLongHashMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.Lambda.FnIntLongToVoid;
import edu.jhu.prim.util.Lambda.LambdaBinOpLong;
import edu.jhu.prim.util.SafeCast;

public class IntLongHashVector extends IntLongHashMap implements IntLongVector {

    private static final long serialVersionUID = 1L;

    public IntLongHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public IntLongHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    /** Copy constructor. */
    public IntLongHashVector(IntLongHashVector other) {
        super(other);
    }
    
    /** Copy constructor. */
    public IntLongHashVector(IntLongVector other) {
        this();
        final IntLongHashVector thisVec = this; 
        other.iterate(new FnIntLongToVoid() {
            @Override
            public void call(int idx, long val) {
                thisVec.set(idx, val);
            }
        });
    }

    /** Builds a vector with the given keys and values. */
    public IntLongHashVector(int[] keys, long[] vals) {
        super(keys, vals);
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public IntLongVector copy() {
        return new IntLongHashVector(this);
    }
        
    @Override
    public long set(int idx, long val) {
        return put(idx, val);
    }

    @Override
    public void scale(long multiplier) {
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                values[i] = multiplier * values[i];
            }
        }
    }

    @Override
    public long dot(long[] other) {
        long dot = 0;
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL && keys[i] <= Integer.MAX_VALUE) {
                dot += values[i] * other[keys[i]];
            }
        }
        return dot;
    }

    @Override
    public long dot(IntLongVector y) {
        if (y instanceof IntLongHashVector) {
            IntLongHashVector other = ((IntLongHashVector) y);
            if (other.size() < this.size()) {
                return other.dot(this);
            }
            return dotWithoutCaveats(other);
        } else if (y instanceof IntLongSortedVector) {
            // TODO: If the size of the HashVector is much smaller than the size
            // of the SortedVector and neither is very large, we might want to
            // instead loop over the HashVector.
            return y.dot(this);
        } else {
            return dotWithoutCaveats(y);
        }
    }

    private long dotWithoutCaveats(IntLongVector other) {
        long dot = 0;
        for (int i=0; i<this.keys.length; i++) {
            if (this.states[i] == FULL) {
                dot += this.values[i] * other.get(keys[i]);
            }
        }
        return dot;
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(IntLongVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.LongAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntLongVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.LongSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntLongVector other) { 
        // TODO: This is correct, but will create lots of zero entries and not remove any.
        // Also this will be very slow if other is a IntLongSortedVector since it will have to
        // call get() each time.
        this.apply(new SparseBinaryOpApplier2(this, other, new Lambda.LongProd()));
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
    public long[] toNativeArray() {
        final long[] arr = new long[getNumImplicitEntries()];
        iterate(new FnIntLongToVoid() {
            @Override
            public void call(int idx, long val) {
                arr[idx] = val;
            }
        });
        return arr;
    }
    
    public static class SparseBinaryOpApplier implements FnIntLongToVoid {
        
        private IntLongVector modifiedVector;
        private LambdaBinOpLong lambda;
        
        public SparseBinaryOpApplier(IntLongVector modifiedVector, LambdaBinOpLong lambda) {
            this.modifiedVector = modifiedVector;
            this.lambda = lambda;
        }
        
        public void call(int idx, long val) {
            modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
        }
        
    }
    
    private static class SparseBinaryOpApplier2 implements FnIntLongToLong {
        
        private IntLongVector vec1;
        private IntLongVector vec2;
        private LambdaBinOpLong lambda;
        
        public SparseBinaryOpApplier2(IntLongVector vec1, IntLongVector vec2, LambdaBinOpLong lambda) {
            this.vec1 = vec1;
            this.vec2 = vec2;
            this.lambda = lambda;
        }
        
        public long call(int idx, long val) {
            return lambda.call(vec1.get(idx), vec2.get(idx));
        }
        
    }

}
