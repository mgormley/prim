package edu.jhu.prim.vector;

import java.io.Serializable;

import edu.jhu.prim.util.Lambda.FnIntIntToInt;

/** 
 * Vector with int indices and int values.
 * 
 * @author mgormley
 */
public interface IntIntVector extends Serializable {

    /** Gets the value at the specified index. */
    int get(int idx);

    /** Sets the value at the specified index. */
    void set(int idx, int val);

    /** Adds to the current value at the specified index. */
    void add(int idx, int val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(int multiplier);

    /** Computes the dot product of this vector with the other vector. */
    int dot(int[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    int dot(IntIntVector other);

    /** Applies the function to each (explicit) entry in the vector. */
    void apply(FnIntIntToInt function);
    
    /** Updates this vector to be the entrywise sum of this vector with the other. */
    void add(IntIntVector other);
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    void subtract(IntIntVector other);
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    void product(IntIntVector other);
    
}