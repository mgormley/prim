package edu.jhu.prim.map;

import java.util.Iterator;

import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;

/**
 * A primitives map from ints to doubles.
 * @author mgormley
 */
public interface IntDoubleMap extends Iterable<IntDoubleEntry> {

    void clear();

    // TODO: rename to containsKey.
    boolean contains(int idx);

    double get(int idx);

    double getWithDefault(int idx, double defaultVal);

    void remove(int idx);

    void put(int idx, double val);

    void add(int idx, double val);

    void apply(FnIntDoubleToDouble lambda);

    Iterator<IntDoubleEntry> iterator();

    int size();

    /**
     * Returns the indices.
     */
    int[] getIndices();

    /**
     * Returns the values.
     */
    double[] getValues();

}