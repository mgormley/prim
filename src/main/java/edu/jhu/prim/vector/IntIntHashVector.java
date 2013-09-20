package edu.jhu.prim.vector;

import edu.jhu.prim.map.IntIntHashMap;
import edu.jhu.prim.util.SafeCast;

public class IntIntHashVector extends IntIntHashMap implements IntIntVector {

    private static final long serialVersionUID = 1L;

    public IntIntHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public IntIntHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    public IntIntHashVector(IntIntHashVector other) {
        super(other);
    }
    
    @Override
    public void set(int idx, int val) {
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

}
