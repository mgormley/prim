package edu.jhu.prim.map;

import java.util.Iterator;

/**
 * A primitives map from longs to ints.
 * @author mgormley
 */
public interface LongIntMap extends Iterable<LongIntEntry> {

    void clear();

    // TODO: rename to containsKey.
    boolean contains(long idx);

    int get(long idx);

    int getWithDefault(long idx, int defaultVal);

    void remove(long idx);

    void put(long idx, int val);

    Iterator<LongIntEntry> iterator();

    int size();

    /**
     * Returns the indices.
     */
    long[] getIndices();

    /**
     * Returns the values.
     */
    int[] getValues();

}