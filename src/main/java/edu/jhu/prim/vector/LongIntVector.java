package edu.jhu.prim.vector;

import java.io.Serializable;

import edu.jhu.prim.util.Lambda.FnLongIntToInt;

/** 
 * Vector with long indices and int values.
 * 
 * @author mgormley
 */
public interface LongIntVector extends Serializable {

    /** Gets the value at the specified index. */
    int get(long idx);

    /** Sets the value at the specified index and returns the previous value. */
    int set(long idx, int val);

    /** Adds to the current value at the specified index. */
    void add(long idx, int val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(int multiplier);

    /** Computes the dot product of this vector with the other vector. */
    int dot(int[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    int dot(LongIntVector other);

    /**
     * Applies the function to each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void apply(FnLongIntToInt function);
    
    /** Updates this vector to be the entrywise sum of this vector with the other. */
    void add(LongIntVector other);
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    void subtract(LongIntVector other);
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    void product(LongIntVector other);
    
    /** Gets a deep copy of this vector. */
    LongIntVector copy();
    
}