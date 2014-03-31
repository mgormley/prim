package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.SafeCast;
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
    
    /** Gets a deep copy of this vector. */
    public IntLongDenseVector(IntLongDenseVector other) {
        this.elements = LongArrays.copyOf(other.elements);
        this.idxAfterLast = other.idxAfterLast;
    }

    /** Copy constructor. */
    public IntLongDenseVector(IntLongVector other) {
        // TODO: Exploit the number of non-zero entries in other.
        this();
        final IntLongDenseVector thisVec = this; 
        other.apply(new FnIntLongToLong() {            
            @Override
            public long call(int idx, long val) {
                thisVec.set(idx, val);
                return val;
            }
        });
    }
    
    /** Copy factory method. */
    @Override
    public IntLongVector copy() {
        return new IntLongDenseVector(this);
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
    public long set(int idx, long value) {
        int i = idx;
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        long old = elements[i];
        elements[i] = value;
        return old;
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
        if (y instanceof IntLongDenseVector){
            IntLongDenseVector other = (IntLongDenseVector) y;
            int max = Math.min(idxAfterLast, other.idxAfterLast);
            long dot = 0;
            for (int i=0; i<max; i++) {
                dot += elements[i] * y.get(i);
            }
            return dot;
        } else {
            return y.dot(this);
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
            if (Primitives.equals(elements[i], value)) {
                return i;
            }
        }
        return -1;
    }

    /** Gets a NEW array containing all the elements in this vector. */
    public long[] toNativeArray() {
        return Arrays.copyOf(elements, idxAfterLast);
    }
    
    /** Gets the INTERNAL representation of this vector. */
    public long[] getInternalElements() {
        return elements;
    }

    /**
     * Gets the number of explicit entries in this vector.
     * 
     * For a dense vector, this is just the size of the vector.
     * 
     * For a sparse vector, this is the number of entries which are explicitly
     * represented in the vector.
     * 
     * The contract of this method is that a call to this.apply(fn) will visit n
     * entries where n = this.getNumExplicitEntries().
     * 
     * @return The number of explicit entries.
     */
    public int getNumExplicitEntries() {
        return idxAfterLast;
    }
    
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
    public int getDimension() {
        return idxAfterLast;
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
