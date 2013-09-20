package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.util.Utilities;


public class LongIntDenseVector implements LongIntVector {

    /** The value given to non-explicit entries in the vector. */
    private static final int missingEntries = 0;
    /** The internal array representing this vector. */
    private int[] elements;
    /** The index after the last explicit entry in the vector. */
    private int idxAfterLast;

    public LongIntDenseVector() {
        this(8);
    }    
    
    public LongIntDenseVector(int initialCapacity) {
        elements = new int[initialCapacity];
        idxAfterLast = 0;
    }

    public LongIntDenseVector(int[] elements) {
        this.elements = elements;
        idxAfterLast = elements.length;
    }
    
    /** Copy constructor. */
    public LongIntDenseVector(LongIntDenseVector other) {
        this.elements = Utilities.copyOf(other.elements);
        this.idxAfterLast = other.idxAfterLast;
    }
    
    /**
     * Gets the i'th entry in the vector.
     * @param i The index of the element to get.
     * @return The value of the element to get.
     */
    public int get(long i) {
        if (i < 0 || i >= idxAfterLast) {
            return missingEntries;
        }
        return elements[SafeCast.safeLongToInt(i)];
    }
    
    /**
     * Sets the i'th entry of the vector to the given value.
     * @param i The index to set.
     * @param value The value to set.
     */
    public void set(long idx, int value) {
        int i = SafeCast.safeLongToInt(idx);
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        elements[i] = value;
    }

    public void add(long idx, int value) {
        int i = SafeCast.safeLongToInt(idx);
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
    public int dot(LongIntVector y) {
        if (y instanceof LongIntSortedVector || y instanceof LongIntHashVector) {
            return y.dot(this);
        } else if (y instanceof LongIntDenseVector){
            LongIntDenseVector other = (LongIntDenseVector) y;
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
    public void apply(FnLongIntToInt function) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] = function.call(i, elements[i]);
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
    public int[] toNativeArray() {
        return Arrays.copyOf(elements, idxAfterLast);
    }
    
    /**
     * Trims the internal array to the size of the array list and then return
     * the internal array backing this array list. CAUTION: this should not be
     * called without carefully handling the result.
     * 
     * @return The internal array representing this array list, trimmed to the
     *         correct size.
     */
    // TODO: rename to getElements.
    public int[] elements() {
        this.trimToSize();
        return elements;
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
