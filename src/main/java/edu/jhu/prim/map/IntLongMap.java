package edu.jhu.prim.map;

import java.util.Iterator;

/**
 * A primitives map from ints to longs.
 * @author mgormley
 */
public interface IntLongMap extends Iterable<IntLongEntry> {

    void clear();

    // TODO: rename to containsKey.
    boolean contains(int idx);

    long get(int idx);

    long getWithDefault(int idx, long defaultVal);

    void remove(int idx);

    void put(int idx, long val);

    Iterator<IntLongEntry> iterator();

    int size();

    /**
     * Returns the indices.
     */
    int[] getIndices();

    /**
     * Returns the values.
     */
    long[] getValues();

}