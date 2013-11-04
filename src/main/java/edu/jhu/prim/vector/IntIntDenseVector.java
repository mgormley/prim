package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntIntToInt;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.vector.IntIntHashVector.SparseBinaryOpApplier;


public class IntIntDenseVector implements IntIntVector {

    private static final long serialVersionUID = 1L;

    /** The value given to non-explicit entries in the vector. */
    private static final int missingEntries = 0;
    /** The internal array representing this vector. */
    private int[] elements;
    /** The index after the last explicit entry in the vector. */
    private int idxAfterLast;

    public IntIntDenseVector() {
        this(8);
    }
    
    public IntIntDenseVector(int initialCapacity) {
        elements = new int[initialCapacity];
        idxAfterLast = 0;
    }

    public IntIntDenseVector(int[] elements) {
        this.elements = elements;
        idxAfterLast = elements.length;
    }
    
    /** Copy constructor. */
    public IntIntDenseVector(IntIntDenseVector other) {
        this.elements = IntArrays.copyOf(other.elements);
        this.idxAfterLast = other.idxAfterLast;
    }

    /** Copy constructor. */
    public IntIntDenseVector(IntIntVector other) {
        // TODO: Exploit the number of non-zero entries in other.
        this();
        final IntIntDenseVector thisVec = this; 
        other.apply(new FnIntIntToInt() {            
            @Override
            public int call(int idx, int val) {
                thisVec.set(idx, val);
                return val;
            }
        });
    }
    
    /**
     * Gets the i'th entry in the vector.
     * @param i The index of the element to get.
     * @return The value of the element to get.
     */
    public int get(int i) {
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
    public void set(int idx, int value) {
        int i = idx;
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        elements[i] = value;
    }

    public void add(int idx, int value) {
        int i = idx;
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        elements[i] += value;
    }

    public void scale(int multiplier) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] *= multiplier;
        }
    }

    @Override
    public int dot(int[] other) {
        int max = Math.min(idxAfterLast, other.length);
        int dot = 0;
        for (int i=0; i<max; i++) {
            dot += elements[i] * other[i];
        }
        return dot;
    }

    @Override
    public int dot(IntIntVector y) {
        if (y instanceof IntIntSortedVector || y instanceof IntIntHashVector) {
            return y.dot(this);
        } else if (y instanceof IntIntDenseVector){
            IntIntDenseVector other = (IntIntDenseVector) y;
            int max = Math.min(idxAfterLast, other.idxAfterLast);
            int dot = 0;
            for (int i=0; i<max; i++) {
                dot += elements[i] * y.get(i);
            }
            return dot;
        } else {
            int dot = 0;
            for (int i=0; i<idxAfterLast; i++) {
                dot += elements[i] * y.get(i);
            }
            return dot;
        }
    }
    
    @Override
    public void apply(FnIntIntToInt function) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] = function.call(i, elements[i]);
        }
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(IntIntVector other) {
        // TODO: Add special case for IntIntDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.IntAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntIntVector other) {
        // TODO: Add special case for IntIntDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.IntSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntIntVector other) {
        // TODO: Add special case for IntIntDenseVector.
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
    public int lookupIndex(int value) {
        for (int i=0; i<elements.length; i++) {
            if (Primitives.equals(elements[i], value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets a NEW array containing all the elements in this array list.
     * @return The new array containing the elements in this list.
     */
    public int[] toNativeArray() {
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
    public static int[] ensureCapacity(int[] elements, int size) {
        if (size > elements.length) {
            int[] tmp = new int[size*2];
            System.arraycopy(elements, 0, tmp, 0, elements.length);
            elements = tmp;
        }
        return elements;
    }

}
