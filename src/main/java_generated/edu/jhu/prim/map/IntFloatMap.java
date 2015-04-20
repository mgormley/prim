package edu.jhu.prim.map;

import java.io.Serializable;
import java.util.Iterator;

import edu.jhu.prim.util.Lambda.FnIntFloatToFloat;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;

/**
 * A primitives map from ints to floats.
 * @author mgormley
 */
public interface IntFloatMap extends Iterable<IntFloatEntry>, Serializable {

    /** Gets the value at the specified index and returns an implementation specific default it's missing. */
    float get(int idx);

    /** Gets the value at the specified index and returns the given default if it's missing. */
    float getWithDefault(int idx, float defaultVal);

    /** Returns true iff the map contains the given index. */
    // TODO: rename to containsKey.
    boolean contains(int idx);
    
    /** Puts the value with the specified index in the map, replacing the current value if it exists, and returning the previous value. */
    float put(int idx, float val);

    /** Removes the entry with the given index. */
    void remove(int idx);

    /** Removes all entries. */
    void clear();
    
    /** Increments the entry with the given index by the specified increment. */
    // TODO: maybe rename to increment?
    void add(int idx, float incr);

    /** Applies the function to each entry in the map. */
    void apply(FnIntFloatToFloat lambda);

    /** Calls the function on each entry in the map. */
    void iterate(FnIntFloatToVoid function);
    
    /**
     * Returns an iterator over the entries in the map. CAUTION: This tends to
     * be slower than using the equivalent apply().
     */
    Iterator<IntFloatEntry> iterator();

    /** Returns the number of entries in the map. */
    int size();

    /**
     * Returns the indices.
     */
    int[] getIndices();

    /**
     * Returns the values.
     */
    float[] getValues();

}