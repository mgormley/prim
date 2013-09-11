package edu.jhu.prim.map;

import java.util.Iterator;

/**
 * A primitives map from ints to ints.
 * @author mgormley
 */
public interface IntIntMap extends Iterable<IntIntEntry> {

    void clear();

    // TODO: rename to containsKey.
    boolean contains(int idx);

    int get(int idx);

    int getWithDefault(int idx, int defaultVal);

    void remove(int idx);

    void put(int idx, int val);

    Iterator<IntIntEntry> iterator();

    int size();

    /**
     * Returns the indices.
     */
    int[] getIndices();

    /**
     * Returns the values.
     */
    int[] getValues();

}