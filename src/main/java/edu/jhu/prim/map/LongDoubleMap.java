package edu.jhu.prim.map;

import java.util.Iterator;

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