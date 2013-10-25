package edu.jhu.prim.vector;

import edu.jhu.prim.map.IntLongHashMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
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
    
    public IntLongHashVector(IntLongHashVector other) {
        super(other);
    }
    
    @Override
    public void set(int idx, long val) {
        put(idx, val);
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
        other.apply(new SparseBinaryOpApplier(this, new Lambda.LongAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntLongVector other) {
        other.apply(new SparseBinaryOpApplier(this, new Lambda.LongSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntLongVector other) { 
        // TODO: This is correct, but will create lots of zero entries and not remove any.
        // Also this will be very slow if other is a IntLongSortedVector since it will have to
        // call get() each time.
        this.apply(new SparseBinaryOpApplier2(this, other, new Lambda.LongProd()));
    }
    
    public static class SparseBinaryOpApplier implements FnIntLongToLong {
        
        private IntLongVector modifiedVector;
        private LambdaBinOpLong lambda;
        
        public SparseBinaryOpApplier(IntLongVector modifiedVector, LambdaBinOpLong lambda) {
            this.modifiedVector = modifiedVector;
            this.lambda = lambda;
        }
        
        public long call(int idx, long val) {
            modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
            return val;
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
