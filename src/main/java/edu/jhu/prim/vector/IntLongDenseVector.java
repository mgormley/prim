package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.util.Utilities;
import edu.jhu.prim.vector.IntLongHashVector.SparseBinaryOpApplier;


public class IntLongDenseVector implements IntLongVector {

    private static final long serialVersionUID = 1L;

    /** The value given to non-explicit entries in the vector. */
    private static final long missingEntries = 0;
    /** The internal array representing this vector. */
    private long[] elements;
    /** The index after the last explicit entry in the vector. */
    private int idxAfterLast;

    public IntLongDenseVector() {
        this(8);
    }
    
    public IntLongDenseVector(int initialCapacity) {
        elements = new long[initialCapacity];
        idxAfterLast = 0;
    }

    public IntLongDenseVector(long[] elements) {
        this.elements = elements;
        idxAfterLast = elements.length;
    }
    
    /** Copy constructor. */
    public IntLongDenseVector(IntLongDenseVector other) {
        this.elements = Utilities.copyOf(other.elements);
        this.idxAfterLast = other.idxAfterLast;
    }
    
    /**
     * Gets the i'th entry in the vector.
     * @param i The index of the element to get.
     * @return The value of the element to get.
     */
    public long get(int i) {
        if (i < 0 || i >= idxAfterLast) {
            return missingEntries;
        }
        return elements[i];
    }
    
    /**
     * Sets the i'th entry of the vector to the given value.
     * @param i The index to set.
     * @param value The value to set.
     */
    public void set(int idx, long value) {
        int i = idx;
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        elements[i] = value;
    }

    public void add(int idx, long value) {
        int i = idx;
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        elements[i] += value;
    }

    public void scale(long multiplier) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] *= multiplier;
        }
    }

    @Override
    public long dot(long[] other) {
        int max = Math.min(idxAfterLast, other.length);
        long dot = 0;
        for (int i=0; i<max; i++) {
            dot += elements[i] * other[i];
        }
        return dot;
    }

    @Override
    public long dot(IntLongVector y) {
        if (y instanceof IntLongSortedVector || y instanceof IntLongHashVector) {
            return y.dot(this);
        } else if (y instanceof IntLongDenseVector){
            IntLongDenseVector other = (IntLongDenseVector) y;
            int max = Math.min(idxAfterLast, other.idxAfterLast);
            long dot = 0;
            for (int i=0; i<max; i++) {
                dot += elements[i] * y.get(i);
            }
            return dot;
        } else {
            long dot = 0;
            for (int i=0; i<idxAfterLast; i++) {
                dot += elements[i] * y.get(i);
            }
            return dot;
        }
    }
    
    @Override
    public void apply(FnIntLongToLong function) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] = function.call(i, elements[i]);
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
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] *= other.get(i);
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
        for (int i=0; i<elements.length; i++) {
            if (Utilities.equals(elements[i], value)) {
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
        return Arrays.copyOf(elements, idxAfterLast);
    }
    
    /**
     * Trims the internal array to exactly the size of the list.
     */
    public void trimToSize() {
        if (idxAfterLast != elements.length) { 
            elements = Arrays.copyOf(elements, idxAfterLast);
        }
    }

    /**
     * Ensure that the internal array has space to contain the specified number of elements.
     * @param size The number of elements. 
     */
    private void ensureCapacity(int size) {
        elements = ensureCapacity(elements, size);
    }
    
    /**
     * Ensure that the array has space to contain the specified number of elements.
     * @param elements The array.
     * @param size The number of elements. 
     */
    public static long[] ensureCapacity(long[] elements, int size) {
        if (size > elements.length) {
            long[] tmp = new long[size*2];
            System.arraycopy(elements, 0, tmp, 0, elements.length);
            elements = tmp;
        }
        return elements;
    }

}
