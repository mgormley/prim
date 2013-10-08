package edu.jhu.prim.map;

import java.util.Iterator;

import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;

/**
 * A primitives map from longs to doubles.
 * @author mgormley
 */
public interface LongDoubleMap extends Iterable<LongDoubleEntry> {

    /** Gets the value at the specified index and returns an implementation specific default it's missing. */
    double get(long idx);

    /** Gets the value at the specified index and returns the given default if it's missing. */
    double getWithDefault(long idx, double defaultVal);

    /** Returns true iff the map contains the given index. */
    // TODO: rename to containsKey.
    boolean contains(long idx);
    
    /** Puts the value with the specified index in the map, replacing the current value if it exists. */
    void put(long idx, double val);

    /** Removes the entry with the given index. */
    void remove(long idx);

    /** Removes all entries. */
    void clear();
    
    /** Increments the entry with the given index by the specified increment. */
    // TODO: maybe rename to increment?
    void add(long idx, double incr);

    /** Applies the function to each entry in the map. */
    void apply(FnLongDoubleToDouble lambda);

    /**
     * Returns an iterator over the entries in the map. CAUTION: This tends to
     * be slower than using the equivalent apply().
     */
    Iterator<LongDoubleEntry> iterator();

    /** Returns the number of entries in the map. */
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