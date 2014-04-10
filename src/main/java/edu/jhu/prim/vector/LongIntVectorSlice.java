package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.vector.LongIntHashVector.SparseBinaryOpApplier;


/**
 * A "slice" of a vector. This gives a read/write view of an LongIntVector
 * vector where the indices have been shifted so that they begin and end within
 * a slice of the given vector.
 * 
 * @author mgormley
 */
//TODO: Implement Iterable<LongIntEntry>.
public class LongIntVectorSlice implements LongIntVector {

    private static final long serialVersionUID = 1L;

    /** The internal vector backing this slice. */
    private final int[] elements;
    /** The index of the 0'th element of this slice (inclusive). */
    private final int start;
    /** The size of this vector. The last element (exclusive) will be elements[start + size]. */
    private final int size;
    
    public LongIntVectorSlice(int[] elements, int start, int size) {
        if (!(0 <= start && start < elements.length && 0 <= size && size <= elements.length && start + size <= elements.length)) {
            throw new IllegalStateException("Invalid slice indices");
        }
        this.elements = elements;
        this.start = start;
        this.size = size;
    }
    
    /**
     * The current implementation assumes NO CHANGES will be made to the
     * underlying dense vector except through this slice for the duration of
     * this slice's existence.
     */
    public LongIntVectorSlice(LongIntDenseVector vec, int start, int size) {
        // TODO: There's a rather odd case we're dealing with in which the dense
        // vector may actually have a larger internal representation than what
        // it exposes. So we initialize the internal array here.
        this(vec.getInternalElements(), start, size);
    }
    
    /** Shallow copy constructor. */
    // TODO: maybe remove? This doesn't seem useful for anything.
    public LongIntVectorSlice(LongIntVectorSlice other) {
        this.elements = other.elements;
        this.start = other.start;
        this.size = other.size;
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public LongIntVector copy() {
        return new LongIntDenseVector(this);
    }
    
    /**
     * Gets the idx'th entry in the vector.
     * @param idx The index of the element to get.
     * @return The value of the element to get.
     */
    public int get(long idx) {
        if (idx < 0 || idx >= size) {
            return 0;
        }
        return elements[SafeCast.safeLongToInt(idx + start)];
    }

    private final void checkIndex(long idx) {
        if (idx < 0 || idx >= size) {
            throw new IllegalStateException("Invalid index for slice: " + idx);
        }
    }
    
    /**
     * Sets the i'th entry of the vector to the given value.
     * @param i The index to set.
     * @param value The value to set.
     */
    public int set(long idx, int value) {
        checkIndex(idx);
        int i = SafeCast.safeLongToInt(idx + start);
        int old = elements[i];
        elements[i] = value;
        return old;
    }

    public void add(long idx, int value) {
        checkIndex(idx);
        elements[SafeCast.safeLongToInt(idx + start)] += value;
    }

    public void scale(int multiplier) {
        for (int ii=start; ii<start + size; ii++) {
            elements[ii] *= multiplier;
        }
    }

    @Override
    public int dot(int[] other) {
        int max = Math.min(size, other.length);
        int dot = 0;
        for (int i=0; i<max; i++) {
            dot += elements[i + start] * other[i];
        }
        return dot;
    }

    @Override
    public int dot(LongIntVector y) {
        int dot = 0;
        for (int i=0; i<size; i++) {
            dot += elements[i + start] * y.get(i);
        }
        return dot;
    }
    
    @Override
    public void apply(FnLongIntToInt function) {
        for (int i=0; i< size; i++) {
            elements[i + start] = function.call(i, elements[i + start]);
        }
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(LongIntVector other) {
        // TODO: Add special case for LongIntDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.IntAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(LongIntVector other) {
        // TODO: Add special case for LongIntDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.IntSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(LongIntVector other) {
        // TODO: Add special case for LongIntDenseVector.
        for (int i=0; i<size; i++) {
            elements[i + start] *= other.get(i);
        }
    }
    
    /**
     * Gets the index of the first element in this vector with the specified
     * value, or -1 if it is not present.
     * 
     * @param value The value to search for.
     * @param delta The delta with which to evaluate equality.
     * @return The index or -1 if not present.
     */
    public int lookupIndex(int value) {
        for (int i=0; i<size; i++) {
            if (Primitives.equals(elements[i + start], value)) {
                return i;
            }
        }
        return -1;
    }
    
    public long getNumImplicitEntries() {        
        return size;
    }
    
    /**
     * Gets a NEW array containing all the elements in this array list.
     * @return The new array containing the elements in this list.
     */
    public int[] toNativeArray() {
        return Arrays.copyOfRange(elements, start, start + size);
    }

}
