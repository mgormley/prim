package edu.jhu.prim.vector;

import edu.jhu.prim.map.LongIntHashMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.Lambda.LambdaBinOpInt;
import edu.jhu.prim.util.SafeCast;

public class LongIntHashVector extends LongIntHashMap implements LongIntVector {

    private static final long serialVersionUID = 1L;

    public LongIntHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public LongIntHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    /** Copy constructor. */
    public LongIntHashVector(LongIntHashVector other) {
        super(other);
    }
    
    /** Copy constructor. */
    public LongIntHashVector(LongIntVector other) {
        this();
        final LongIntHashVector thisVec = this; 
        other.apply(new FnLongIntToInt() {            
            @Override
            public int call(long idx, int val) {
                thisVec.set(idx, val);
                return val;
            }
        });
    }
        
    @Override
    public void set(long idx, int val) {
        put(idx, val);
    }

    @Override
    public void scale(int multiplier) {
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                values[i] = multiplier * values[i];
            }
        }
    }

    @Override
    public int dot(int[] other) {
        int dot = 0;
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL && keys[i] <= Integer.MAX_VALUE) {
                dot += values[i] * other[SafeCast.safeLongToInt(keys[i])];
            }
        }
        return dot;
    }

    @Override
    public int dot(LongIntVector y) {
        if (y instanceof LongIntHashVector) {
            LongIntHashVector other = ((LongIntHashVector) y);
            if (other.size() < this.size()) {
                return other.dot(this);
            }
            return dotWithoutCaveats(other);
        } else if (y instanceof LongIntSortedVector) {
            // TODO: If the size of the HashVector is much smaller than the size
            // of the SortedVector and neither is very large, we might want to
            // instead loop over the HashVector.
            return y.dot(this);
        } else {
            return dotWithoutCaveats(y);
        }
    }

    private int dotWithoutCaveats(LongIntVector other) {
        int dot = 0;
        for (int i=0; i<this.keys.length; i++) {
            if (this.states[i] == FULL) {
                dot += this.values[i] * other.get(keys[i]);
            }
        }
        return dot;
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(LongIntVector other) {
        other.apply(new SparseBinaryOpApplier(this, new Lambda.IntAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(LongIntVector other) {
        other.apply(new SparseBinaryOpApplier(this, new Lambda.IntSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(LongIntVector other) { 
        // TODO: This is correct, but will create lots of zero entries and not remove any.
        // Also this will be very slow if other is a LongIntSortedVector since it will have to
        // call get() each time.
        this.apply(new SparseBinaryOpApplier2(this, other, new Lambda.IntProd()));
    }
    
    public static class SparseBinaryOpApplier implements FnLongIntToInt {
        
        private LongIntVector modifiedVector;
        private LambdaBinOpInt lambda;
        
        public SparseBinaryOpApplier(LongIntVector modifiedVector, LambdaBinOpInt lambda) {
            this.modifiedVector = modifiedVector;
            this.lambda = lambda;
        }
        
        public int call(long idx, int val) {
            modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
            return val;
        }
        
    }
    
    private static class SparseBinaryOpApplier2 implements FnLongIntToInt {
        
        private LongIntVector vec1;
        private LongIntVector vec2;
        private LambdaBinOpInt lambda;
        
        public SparseBinaryOpApplier2(LongIntVector vec1, LongIntVector vec2, LambdaBinOpInt lambda) {
            this.vec1 = vec1;
            this.vec2 = vec2;
            this.lambda = lambda;
        }
        
        public int call(long idx, int val) {
            return lambda.call(vec1.get(idx), vec2.get(idx));
        }
        
    }

}
