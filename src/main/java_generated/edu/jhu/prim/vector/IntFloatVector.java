package edu.jhu.prim.vector;

import java.io.Serializable;

import edu.jhu.prim.util.Lambda.FnIntFloatToFloat;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;

/** 
 * Vector with int indices and float values.
 * 
 * @author mgormley
 */
//TODO: Extend Iterable<IntFloatEntry>.
public interface IntFloatVector extends Serializable {

    /** Gets the value at the specified index. */
    float get(int idx);

    /** Sets the value at the specified index and returns the previous value. */
    float set(int idx, float val);

    /** Adds to the current value at the specified index. */
    void add(int idx, float val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(float multiplier);

    /** Computes the dot product of this vector with the other vector. */
    float dot(float[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    float dot(IntFloatVector other);

    /**
     * Applies the function to each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void apply(FnIntFloatToFloat function);

    /**
     * Calls the function on each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void iterate(FnIntFloatToVoid function);
    
    /** Updates this vector to be the entrywise sum of this vector with the other. */
    void add(IntFloatVector other);
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    void subtract(IntFloatVector other);
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    void product(IntFloatVector other);
    
    /** Gets a deep copy of this vector. */
    IntFloatVector copy();
    
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
    
    /** Gets a float array representation of this vector. */
    float[] toNativeArray();
    
    /** Gets the sum of all the explicit entries in this vector. */
    float getSum();

    /** Gets the product of all the explicit entries in this vector. */
    float getProd();

    /** Gets the max of all the explicit entries in this vector. */
    float getMax();

    /** Gets the index of the max of all the explicit entries in this vector. */
    int getArgmax();
    
    /** Gets the min of all the explicit entries in this vector. */
    float getMin();

    /** Gets the index of the min of all the explicit entries in this vector. */
    int getArgmin();

    /** Gets the sum of the squares all the explicit entries in this vector. */
    float getL2Norm();

    /** Gets the sum of all the explicit entries in this vector. */
    float getInfNorm();
    
}