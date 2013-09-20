package edu.jhu.prim.map;

import java.util.Iterator;

import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;

/**
 * A primitives map from longs to doubles.
 * @author mgormley
 */
public interface LongDoubleMap extends Iterable<LongDoubleEntry> {

    void clear();

    // TODO: rename to containsKey.
    boolean contains(long idx);

    double get(long idx);

    double getWithDefault(long idx, double defaultVal);

    void remove(long idx);

    void put(long idx, double val);

    void add(long idx, double val);

    void apply(FnLongDoubleToDouble lambda);

    Iterator<LongDoubleEntry> iterator();

    int size();

    /**
     * Returns the indices.
     */
    long[] getIndices();

    /**
     * Returns the values.
     */
    double[] getValues();

}