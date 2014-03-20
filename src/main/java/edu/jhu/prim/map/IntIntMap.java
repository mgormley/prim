package edu.jhu.prim.map;

import java.io.Serializable;
import java.util.Iterator;

import edu.jhu.prim.util.Lambda.FnIntIntToInt;

/**
 * A primitives map from ints to ints.
 * @author mgormley
 */
public interface IntIntMap extends Iterable<IntIntEntry>, Serializable {

    /** Gets the value at the specified index and returns an implementation specific default it's missing. */
    int get(int idx);

    /** Gets the value at the specified index and returns the given default if it's missing. */
    int getWithDefault(int idx, int defaultVal);

    /** Returns true iff the map contains the given index. */
    // TODO: rename to containsKey.
    boolean contains(int idx);
    
    /** Puts the value with the specified index in the map, replacing the current value if it exists, and returning the previous value. */
    int put(int idx, int val);

    /** Removes the entry with the given index. */
    void remove(int idx);

    /** Removes all entries. */
    void clear();
    
    /** Increments the entry with the given index by the specified increment. */
    // TODO: maybe rename to increment?
    void add(int idx, int incr);

    /** Applies the function to each entry in the map. */
    void apply(FnIntIntToInt lambda);

    /**
     * Returns an iterator over the entries in the map. CAUTION: This tends to
     * be slower than using the equivalent apply().
     */
    Iterator<IntIntEntry> iterator();

    /** Returns the number of entries in the map. */
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