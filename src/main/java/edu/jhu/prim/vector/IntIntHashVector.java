package edu.jhu.prim.vector;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.map.IntIntHashMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntIntToInt;
import edu.jhu.prim.util.Lambda.FnIntIntToVoid;
import edu.jhu.prim.util.Lambda.LambdaBinOpInt;
import edu.jhu.prim.util.SafeCast;

public class IntIntHashVector extends IntIntHashMap implements IntIntVector {

    private static final long serialVersionUID = 1L;

    public IntIntHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public IntIntHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    /** Copy constructor. */
    public IntIntHashVector(IntIntHashVector other) {
        super(other);
    }
    
    /** Copy constructor. */
    public IntIntHashVector(IntIntVector other) {
        this();
        final IntIntHashVector thisVec = this; 
        other.iterate(new FnIntIntToVoid() {
            @Override
            public void call(int idx, int val) {
                thisVec.set(idx, val);
            }
        });
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public IntIntVector copy() {
        return new IntIntHashVector(this);
    }
        
    @Override
    public int set(int idx, int val) {
        return put(idx, val);
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
                dot += values[i] * other[keys[i]];
            }
        }
        return dot;
    }

    @Override
    public int dot(IntIntVector y) {
        if (y instanceof IntIntHashVector) {
            IntIntHashVector other = ((IntIntHashVector) y);
            if (other.size() < this.size()) {
                return other.dot(this);
            }
            return dotWithoutCaveats(other);
        } else if (y instanceof IntIntSortedVector) {
            // TODO: If the size of the HashVector is much smaller than the size
            // of the SortedVector and neither is very large, we might want to
            // instead loop over the HashVector.
            return y.dot(this);
        } else {
            return dotWithoutCaveats(y);
        }
    }

    private int dotWithoutCaveats(IntIntVector other) {
        int dot = 0;
        for (int i=0; i<this.keys.length; i++) {
            if (this.states[i] == FULL) {
                dot += this.values[i] * other.get(keys[i]);
            }
        }
        return dot;
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(IntIntVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.IntAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntIntVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.IntSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntIntVector other) { 
        // TODO: This is correct, but will create lots of zero entries and not remove any.
        // Also this will be very slow if other is a IntIntSortedVector since it will have to
        // call get() each time.
        this.apply(new SparseBinaryOpApplier2(this, other, new Lambda.IntProd()));
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
    public int[] toNativeArray() {
        final int[] arr = new int[getNumImplicitEntries()];
        iterate(new FnIntIntToVoid() {
            @Override
            public void call(int idx, int val) {
                arr[idx] = val;
            }
        });
        return arr;
    }
    
    public static class SparseBinaryOpApplier implements FnIntIntToVoid {
        
        private IntIntVector modifiedVector;
        private LambdaBinOpInt lambda;
        
        public SparseBinaryOpApplier(IntIntVector modifiedVector, LambdaBinOpInt lambda) {
            this.modifiedVector = modifiedVector;
            this.lambda = lambda;
        }
        
        public void call(int idx, int val) {
            modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
        }
        
    }
    
    private static class SparseBinaryOpApplier2 implements FnIntIntToInt {
        
        private IntIntVector vec1;
        private IntIntVector vec2;
        private LambdaBinOpInt lambda;
        
        public SparseBinaryOpApplier2(IntIntVector vec1, IntIntVector vec2, LambdaBinOpInt lambda) {
            this.vec1 = vec1;
            this.vec2 = vec2;
            this.lambda = lambda;
        }
        
        public int call(int idx, int val) {
            return lambda.call(vec1.get(idx), vec2.get(idx));
        }
        
    }

}
