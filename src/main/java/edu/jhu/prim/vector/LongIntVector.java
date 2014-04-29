package edu.jhu.prim.vector;

import java.io.Serializable;

import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.Lambda.FnLongIntToVoid;

/** 
 * Vector with long indices and int values.
 * 
 * @author mgormley
 */
//TODO: Extend Iterable<LongIntEntry>.
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

    /**
     * Calls the function on each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void iterate(FnLongIntToVoid function);
    
    /** Updates this vector to be the entrywise sum of this vector with the other. */
    void add(LongIntVector other);
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    void subtract(LongIntVector other);
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    void product(LongIntVector other);
    
    /** Gets a deep copy of this vector. */
    LongIntVector copy();
    
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
    long getNumImplicitEntries();
    
    /** Gets a int array representation of this vector. */
    int[] toNativeArray();
    
}