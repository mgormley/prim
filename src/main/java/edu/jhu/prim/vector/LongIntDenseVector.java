package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.vector.LongIntHashVector.SparseBinaryOpApplier;

//TODO: Implement Iterable<LongIntEntry>.
public class LongIntDenseVector implements LongIntVector {

    private static final long serialVersionUID = 1L;

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
    
    /** Gets a deep copy of this vector. */
    public LongIntDenseVector(LongIntDenseVector other) {
        this.elements = IntArrays.copyOf(other.elements);
        this.idxAfterLast = other.idxAfterLast;
    }

    /** Copy constructor. */
    public LongIntDenseVector(LongIntVector other) {
        // TODO: Exploit the number of non-zero entries in other.
        this();
        final LongIntDenseVector thisVec = this; 
        other.apply(new FnLongIntToInt() {            
            @Override
            public int call(long idx, int val) {
                thisVec.set(idx, val);
                return val;
            }
        });
    }
    
    /** Copy factory method. */
    @Override
    public LongIntVector copy() {
        return new LongIntDenseVector(this);
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
    public int set(long idx, int value) {
        int i = SafeCast.safeLongToInt(idx);
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        int old = elements[i];
        elements[i] = value;
        return old;
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
        if (y instanceof LongIntDenseVector){
            LongIntDenseVector other = (LongIntDenseVector) y;
            int max = Math.min(idxAfterLast, other.idxAfterLast);
            int dot = 0;
            for (int i=0; i<max; i++) {
                dot += elements[i] * y.get(i);
            }
            return dot;
        } else {
            return y.dot(this);
        }
    }
    
    @Override
    public void apply(FnLongIntToInt function) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] = function.call(i, elements[i]);
        }
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(LongIntVector other) {
        if (other instanceof LongIntUnsortedVector) {
            LongIntUnsortedVector vec = (LongIntUnsortedVector) other;
            for (int i=0; i<vec.top; i++) {
                this.add(vec.idx[i], vec.vals[i]);
            }
        } else {
            //  TODO: Add special case for LongIntDenseVector.
            other.apply(new SparseBinaryOpApplier(this, new Lambda.IntAdd()));
        }
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(LongIntVector other) {
        if (other instanceof LongIntUnsortedVector) {
            LongIntUnsortedVector vec = (LongIntUnsortedVector) other;
            for (int i=0; i<vec.top; i++) {
                this.add(vec.idx[i], -vec.vals[i]);
            }
        } else {
            // TODO: Add special case for LongIntDenseVector.
            other.apply(new SparseBinaryOpApplier(this, new Lambda.IntSubtract()));
        }
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(LongIntVector other) {
        // TODO: Add special case for LongIntDenseVector.
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

    /** Gets a NEW array containing all the elements in this vector. */
    public int[] toNativeArray() {
        return Arrays.copyOf(elements, idxAfterLast);
    }
    
    /** Gets the INTERNAL representation of this vector. */
    public int[] getInternalElements() {
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
    public long getNumImplicitEntries() {
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
    public static int[] ensureCapacity(int[] elements, int size) {
        if (size > elements.length) {
            int[] tmp = new int[size*2];
            System.arraycopy(elements, 0, tmp, 0, elements.length);
            elements = tmp;
        }
        return elements;
    }

}
