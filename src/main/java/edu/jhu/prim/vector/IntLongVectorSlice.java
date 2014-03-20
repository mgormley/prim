package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.vector.IntLongHashVector.SparseBinaryOpApplier;


/**
 * A "slice" of a vector. This gives a read/write view of an IntLongVector
 * vector where the indices have been shifted so that they begin and end within
 * a slice of the given vector.
 * 
 * @author mgormley
 */
public class IntLongVectorSlice implements IntLongVector {

    private static final long serialVersionUID = 1L;

    /** The internal vector backing this slice. */
    private final long[] elements;
    /** The index of the 0'th element of this slice (inclusive). */
    private final int start;
    /** The size of this vector. The last element (exclusive) will be elements[start + size]. */
    private final int size;
    
    public IntLongVectorSlice(long[] elements, int start, int size) {
        if (!(0 <= start && start < elements.length && 0 <= size && size <= elements.length && start + size <= elements.length)) {
            throw new IllegalStateException("Invalid slice indices");
        }
        this.elements = elements;
        this.start = start;
        this.size = size;
    }
    
    public IntLongVectorSlice(IntLongDenseVector vec, int start, int size) {
        // TODO: There's a rather odd case we're dealing with in which the dense
        // vector may actually have a larger internal representation than what
        // it exposes. So we initialize the internal array here.
        this(vec.getInternalElements(), start, size);
    }
    
    /** Shallow copy constructor. */
    // TODO: maybe remove? This doesn't seem useful for anything.
    public IntLongVectorSlice(IntLongVectorSlice other) {
        this.elements = other.elements;
        this.start = other.start;
        this.size = other.size;
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public IntLongVector copy() {
        return new IntLongDenseVector(this);
    }
    
    /**
     * Gets the idx'th entry in the vector.
     * @param idx The index of the element to get.
     * @return The value of the element to get.
     */
    public long get(int idx) {
        if (idx < 0 || idx >= size) {
            return 0;
        }
        return elements[idx + start];
    }

    private final void checkIndex(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IllegalStateException("Invalid index for slice: " + idx);
        }
    }
    
    /**
     * Sets the i'th entry of the vector to the given value.
     * @param i The index to set.
     * @param value The value to set.
     */
    public long set(int idx, long value) {
        checkIndex(idx);
        int i = idx + start;
        long old = elements[i];
        elements[i] = value;
        return old;
    }

    public void add(int idx, long value) {
        checkIndex(idx);
        elements[idx + start] += value;
    }

    public void scale(long multiplier) {
        for (int ii=start; ii<start + size; ii++) {
            elements[ii] *= multiplier;
        }
    }

    @Override
    public long dot(long[] other) {
        int max = Math.min(size, other.length);
        long dot = 0;
        for (int i=0; i<max; i++) {
            dot += elements[i + start] * other[i];
        }
        return dot;
    }

    @Override
    public long dot(IntLongVector y) {
        long dot = 0;
        for (int i=0; i<size; i++) {
            dot += elements[i + start] * y.get(i);
        }
        return dot;
    }
    
    @Override
    public void apply(FnIntLongToLong function) {
        for (int i=0; i< size; i++) {
            elements[i + start] = function.call(i, elements[i + start]);
        }
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(IntLongVector other) {
        // TODO: Add special case for IntLongDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.LongAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntLongVector other) {
        // TODO: Add special case for IntLongDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.LongSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntLongVector other) {
        // TODO: Add special case for IntLongDenseVector.
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
    public int lookupIndex(long value) {
        for (int i=0; i<size; i++) {
            if (Primitives.equals(elements[i + start], value)) {
                return i;
            }
        }
        return -1;
    }
    
    public int getDimension() {
        return size;
    }
    
    /**
     * Gets a NEW array containing all the elements in this array list.
     * @return The new array containing the elements in this list.
     */
    public long[] toNativeArray() {
        return Arrays.copyOfRange(elements, start, start + size);
    }

}
