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
    
    public IntLongVectorSlice(long[] elements, int start, int end) {
        this.elements = elements;
        this.start = start;
        this.size = end - start;
        assert start >= 0;
        assert size <= elements.length;
    }
    
    public IntLongVectorSlice(IntLongDenseVector vec, int start, int end) {
        this(vec.getInternalElements(), start, end);
    }
    
    /** Shallow copy constructor. */
    // TODO: maybe remove? This doesn't seem useful for anything.
    public IntLongVectorSlice(IntLongVectorSlice other) {
        this.elements = other.elements;
        this.start = other.start;
        this.size = other.size;
    }
    
    /**
     * Gets the idx'th entry in the vector.
     * @param idx The index of the element to get.
     * @return The value of the element to get.
     */
    public long get(int idx) {
        checkIndex(idx);
        return elements[idx + start];
    }
    
    /**
     * Sets the i'th entry of the vector to the given value.
     * @param i The index to set.
     * @param value The value to set.
     */
    public void set(int idx, long value) {
        checkIndex(idx);
        elements[idx + start] = value;
    }

    private final void checkIndex(int idx) {
        if (idx < 0 || idx > size) {
            throw new IllegalStateException("Invalid index for slice: " + idx);
        }
    }

    public void add(int idx, long value) {
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

    /**
     * Gets a NEW array containing all the elements in this array list.
     * @return The new array containing the elements in this list.
     */
    public long[] toNativeArray() {
        return Arrays.copyOfRange(elements, start, start + size);
    }

}
