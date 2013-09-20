package edu.jhu.prim.vector;

import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;

/** 
 * Vector with long indices and double values.
 * 
 * @author mgormley
 */
public interface LongDoubleVector {

    /** Gets the value at the specified index. */
    double get(long idx);

    /** Sets the value at the specified index. */
    void set(long idx, double val);

    /** Adds to the current value at the specified index. */
    void add(long idx, double val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(double multiplier);

    /** Computes the dot product of this vector with the other vector. */
    double dot(double[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    double dot(LongDoubleVector other);

    /** Applies the function to each entry in the vector. */
    void apply(FnLongDoubleToDouble function);
    
}