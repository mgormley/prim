package edu.jhu.prim.vector;

import edu.jhu.prim.util.Lambda.FnIntLongToLong;

/** 
 * Vector with int indices and long values.
 * 
 * @author mgormley
 */
public interface IntLongVector {

    /** Gets the value at the specified index. */
    long get(int idx);

    /** Sets the value at the specified index. */
    void set(int idx, long val);

    /** Adds to the current value at the specified index. */
    void add(int idx, long val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(long multiplier);

    /** Computes the dot product of this vector with the other vector. */
    long dot(long[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    long dot(IntLongVector other);

    /** Applies the function to each entry in the vector. */
    void apply(FnIntLongToLong function);
    
}