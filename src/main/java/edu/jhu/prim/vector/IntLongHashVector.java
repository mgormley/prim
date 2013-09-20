package edu.jhu.prim.vector;

import edu.jhu.prim.map.IntLongHashMap;
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

}
