package edu.jhu.prim.vector;

import edu.jhu.prim.map.LongDoubleHashMap;
import edu.jhu.prim.util.SafeCast;

public class LongDoubleHashVector extends LongDoubleHashMap implements LongDoubleVector {

    private static final long serialVersionUID = 1L;

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
        double dot = 0.0;
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

}
