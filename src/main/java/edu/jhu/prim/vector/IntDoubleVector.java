package edu.jhu.prim.vector;

import java.io.Serializable;

import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;
import edu.jhu.prim.util.Lambda.FnIntDoubleToVoid;

/** 
 * Vector with int indices and double values.
 * 
 * @author mgormley
 */
//TODO: Extend Iterable<IntDoubleEntry>.
public interface IntDoubleVector extends Serializable {

    /** Gets the value at the specified index. */
    double get(int idx);

    /** Sets the value at the specified index and returns the previous value. */
    double set(int idx, double val);

    /** Adds to the current value at the specified index. */
    void add(int idx, double val);

    /** Scales each entry in this vector by the given multiplier. */
    void scale(double multiplier);

    /** Computes the dot product of this vector with the other vector. */
    double dot(double[] other);
    
    /** Computes the dot product of this vector with the other vector. */
    double dot(IntDoubleVector other);

    /**
     * Applies the function to each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void apply(FnIntDoubleToDouble function);

    /**
     * Calls the function on each (explicit) entry in the vector. The caller
     * should make no assumptions about the order in which the entries will be
     * visited.
     */
    void iterate(FnIntDoubleToVoid function);
    
    /** Updates this vector to be the entrywise sum of this vector with the other. */
    void add(IntDoubleVector other);
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    void subtract(IntDoubleVector other);
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    void product(IntDoubleVector other);
    
    /** Gets a deep copy of this vector. */
    IntDoubleVector copy();
    
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
    
    /** Gets a double array representation of this vector. */
    double[] toNativeArray();
    
}