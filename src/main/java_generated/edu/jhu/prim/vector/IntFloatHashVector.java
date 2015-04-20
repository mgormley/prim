package edu.jhu.prim.vector;

import edu.jhu.prim.map.IntFloatHashMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntFloatToFloat;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;
import edu.jhu.prim.util.Lambda.LambdaBinOpFloat;
import edu.jhu.prim.util.SafeCast;

public class IntFloatHashVector extends IntFloatHashMap implements IntFloatVector {

    private static final long serialVersionUID = 1L;

    public IntFloatHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public IntFloatHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    /** Copy constructor. */
    public IntFloatHashVector(IntFloatHashVector other) {
        super(other);
    }
    
    /** Copy constructor. */
    public IntFloatHashVector(IntFloatVector other) {
        this();
        final IntFloatHashVector thisVec = this; 
        other.iterate(new FnIntFloatToVoid() {
            @Override
            public void call(int idx, float val) {
                thisVec.set(idx, val);
            }
        });
    }

    /** Builds a vector with the given keys and values. */
    public IntFloatHashVector(int[] keys, float[] vals) {
        super(keys, vals);
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public IntFloatVector copy() {
        return new IntFloatHashVector(this);
    }
        
    @Override
    public float set(int idx, float val) {
        return put(idx, val);
    }

    @Override
    public void scale(float multiplier) {
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                values[i] = multiplier * values[i];
            }
        }
    }

    @Override
    public float dot(float[] other) {
        float dot = 0;
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL && keys[i] <= Integer.MAX_VALUE) {
                dot += values[i] * other[keys[i]];
            }
        }
        return dot;
    }

    @Override
    public float dot(IntFloatVector y) {
        if (y instanceof IntFloatHashVector) {
            IntFloatHashVector other = ((IntFloatHashVector) y);
            if (other.size() < this.size()) {
                return other.dot(this);
            }
            return dotWithoutCaveats(other);
        } else if (y instanceof IntFloatSortedVector) {
            // TODO: If the size of the HashVector is much smaller than the size
            // of the SortedVector and neither is very large, we might want to
            // instead loop over the HashVector.
            return y.dot(this);
        } else {
            return dotWithoutCaveats(y);
        }
    }

    private float dotWithoutCaveats(IntFloatVector other) {
        float dot = 0;
        for (int i=0; i<this.keys.length; i++) {
            if (this.states[i] == FULL) {
                dot += this.values[i] * other.get(keys[i]);
            }
        }
        return dot;
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(IntFloatVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.FloatAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntFloatVector other) {
        other.iterate(new SparseBinaryOpApplier(this, new Lambda.FloatSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntFloatVector other) { 
        // TODO: This is correct, but will create lots of zero entries and not remove any.
        // Also this will be very slow if other is a IntFloatSortedVector since it will have to
        // call get() each time.
        this.apply(new SparseBinaryOpApplier2(this, other, new Lambda.FloatProd()));
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
    public float[] toNativeArray() {
        final float[] arr = new float[getNumImplicitEntries()];
        iterate(new FnIntFloatToVoid() {
            @Override
            public void call(int idx, float val) {
                arr[idx] = val;
            }
        });
        return arr;
    }
    
    public static class SparseBinaryOpApplier implements FnIntFloatToVoid {
        
        private IntFloatVector modifiedVector;
        private LambdaBinOpFloat lambda;
        
        public SparseBinaryOpApplier(IntFloatVector modifiedVector, LambdaBinOpFloat lambda) {
            this.modifiedVector = modifiedVector;
            this.lambda = lambda;
        }
        
        public void call(int idx, float val) {
            modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
        }
        
    }
    
    private static class SparseBinaryOpApplier2 implements FnIntFloatToFloat {
        
        private IntFloatVector vec1;
        private IntFloatVector vec2;
        private LambdaBinOpFloat lambda;
        
        public SparseBinaryOpApplier2(IntFloatVector vec1, IntFloatVector vec2, LambdaBinOpFloat lambda) {
            this.vec1 = vec1;
            this.vec2 = vec2;
            this.lambda = lambda;
        }
        
        public float call(int idx, float val) {
            return lambda.call(vec1.get(idx), vec2.get(idx));
        }
        
    }

    @Override
    public float getProd() {
        throw new RuntimeException("not supported");
    }

}
