package edu.jhu.prim.vector;

import edu.jhu.prim.map.IntDoubleHashMap;
import edu.jhu.prim.util.SafeCast;

public class IntDoubleHashVector extends IntDoubleHashMap implements IntDoubleVector {

    private static final long serialVersionUID = 1L;

    public IntDoubleHashVector() {
        super(DEFAULT_EXPECTED_SIZE, 0);
    }
    
    public IntDoubleHashVector(int expectedSize) {
        super(expectedSize, 0);
    }
    
    public IntDoubleHashVector(IntDoubleHashVector other) {
        super(other);
    }
    
    @Override
    public void set(int idx, double val) {
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

}
