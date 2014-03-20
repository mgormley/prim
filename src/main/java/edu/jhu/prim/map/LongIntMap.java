package edu.jhu.prim.map;

import java.io.Serializable;
import java.util.Iterator;

import edu.jhu.prim.util.Lambda.FnLongIntToInt;

/**
 * A primitives map from longs to ints.
 * @author mgormley
 */
public interface LongIntMap extends Iterable<LongIntEntry>, Serializable {

    /** Gets the value at the specified index and returns an implementation specific default it's missing. */
    int get(long idx);

    /** Gets the value at the specified index and returns the given default if it's missing. */
    int getWithDefault(long idx, int defaultVal);

    /** Returns true iff the map contains the given index. */
    // TODO: rename to containsKey.
    boolean contains(long idx);
    
    /** Puts the value with the specified index in the map, replacing the current value if it exists, and returning the previous value. */
    int put(long idx, int val);

    /** Removes the entry with the given index. */
    void remove(long idx);

    /** Removes all entries. */
    void clear();
    
    /** Increments the entry with the given index by the specified increment. */
    // TODO: maybe rename to increment?
    void add(long idx, int incr);

    /** Applies the function to each entry in the map. */
    void apply(FnLongIntToInt lambda);

    /**
     * Returns an iterator over the entries in the map. CAUTION: This tends to
     * be slower than using the equivalent apply().
     */
    Iterator<LongIntEntry> iterator();

    /** Returns the number of entries in the map. */
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