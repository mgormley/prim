package edu.jhu.prim.vector;

import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;

/** 
 * Vector with int indices and double values.
 * 
 * @author mgormley
 */
public interface IntDoubleVector {

    /** Gets the value at the specified index. */
    double get(int idx);

    /** Sets the value at the specified index. */
    void set(int idx, double val);

    /** Adds to the current value at the specified index. */
    void add(int idx, double val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(double multiplier);

    /** Computes the dot product of this vector with the other vector. */
    double dot(double[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    double dot(IntDoubleVector other);

    /** Applies the function to each entry in the vector. */
    void apply(FnIntDoubleToDouble function);
    
}