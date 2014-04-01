package edu.jhu.prim.vector;

import java.io.Serializable;

import edu.jhu.prim.util.Lambda.FnIntIntToInt;

/** 
 * Vector with int indices and int values.
 * 
 * @author mgormley
 */
//TODO: Extend Iterable<IntIntEntry>.
public interface IntIntVector extends Serializable {

    /** Gets the value at the specified index. */
    int get(int idx);

    /** Sets the value at the specified index and returns the previous value. */
    int set(int idx, int val);

    /** Adds to the current value at the specified index. */
    void add(int idx, int val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(int multiplier);

    /** Computes the dot product of this vector with the other vector. */
    int dot(int[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    int dot(IntIntVector other);

    /**
     * Applies the function to each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void apply(FnIntIntToInt function);
    
    /** Updates this vector to be the entrywise sum of this vector with the other. */
    void add(IntIntVector other);
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    void subtract(IntIntVector other);
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    void product(IntIntVector other);
    
    /** Gets a deep copy of this vector. */
    IntIntVector copy();
    
    /**
     * Gets the number of implicit entries.
     * 
     * For a dense vector, this is just the size of the vector.
     * 
     * For a sparse vector, this is index after the last explicit entry in the
     * vector. This corresponds to (1 + i) where i is the highest index
     * explicitly represented.
     * 
     * The contract of this method is that for any j >=
     * this.getNumImplicitEntries(), this.get(j) will return 0.
     * 
     * @return The number of implicit entries.
     */
    int getDimension();
    
    /** Gets a int array representation of this vector. */
    int[] toNativeArray();
    
}