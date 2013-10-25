package edu.jhu.prim.map;

import java.io.Serializable;
import java.util.Iterator;

import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;

/**
 * A primitives map from ints to doubles.
 * @author mgormley
 */
public interface IntDoubleMap extends Iterable<IntDoubleEntry>, Serializable {

    /** Gets the value at the specified index and returns an implementation specific default it's missing. */
    double get(int idx);

    /** Gets the value at the specified index and returns the given default if it's missing. */
    double getWithDefault(int idx, double defaultVal);

    /** Returns true iff the map contains the given index. */
    // TODO: rename to containsKey.
    boolean contains(int idx);
    
    /** Puts the value with the specified index in the map, replacing the current value if it exists. */
    void put(int idx, double val);

    /** Removes the entry with the given index. */
    void remove(int idx);

    /** Removes all entries. */
    void clear();
    
    /** Increments the entry with the given index by the specified increment. */
    // TODO: maybe rename to increment?
    void add(int idx, double incr);

    /** Applies the function to each entry in the map. */
    void apply(FnIntDoubleToDouble lambda);

    /**
     * Returns an iterator over the entries in the map. CAUTION: This tends to
     * be slower than using the equivalent apply().
     */
    Iterator<IntDoubleEntry> iterator();

    /** Returns the number of entries in the map. */
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