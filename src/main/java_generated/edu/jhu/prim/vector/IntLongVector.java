package edu.jhu.prim.vector;

import java.io.Serializable;

import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.Lambda.FnIntLongToVoid;

/** 
 * Vector with int indices and long values.
 * 
 * @author mgormley
 */
//TODO: Extend Iterable<IntLongEntry>.
public interface IntLongVector extends Serializable {

    /** Gets the value at the specified index. */
    long get(int idx);

    /** Sets the value at the specified index and returns the previous value. */
    long set(int idx, long val);

    /** Adds to the current value at the specified index. */
    void add(int idx, long val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(long multiplier);

    /** Computes the dot product of this vector with the other vector. */
    long dot(long[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    long dot(IntLongVector other);

    /**
     * Applies the function to each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void apply(FnIntLongToLong function);

    /**
     * Calls the function on each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void iterate(FnIntLongToVoid function);
    
    /** Updates this vector to be the entrywise sum of this vector with the other. */
    void add(IntLongVector other);
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    void subtract(IntLongVector other);
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    void product(IntLongVector other);
    
    /** Gets a deep copy of this vector. */
    IntLongVector copy();
    
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
    int getNumImplicitEntries();
    
    /** Gets a long array representation of this vector. */
    long[] toNativeArray();
    
    /** Gets the sum of all the explicit entries in this vector. */
    long getSum();

    /** Gets the max of all the explicit entries in this vector. */
    long getMax();

    /** Gets the index of the max of all the explicit entries in this vector. */
    int getArgmax();
    
    /** Gets the min of all the explicit entries in this vector. */
    long getMin();

    /** Gets the index of the min of all the explicit entries in this vector. */
    int getArgmin();

    /** Gets the sum of the squares all the explicit entries in this vector. */
    long getL2Norm();

    /** Gets the sum of all the explicit entries in this vector. */
    long getInfNorm();
    
}