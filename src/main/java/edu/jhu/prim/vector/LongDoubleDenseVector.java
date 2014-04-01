package edu.jhu.prim.vector;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.DoubleArrays;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.vector.LongDoubleHashVector.SparseBinaryOpApplier;

//TODO: Implement Iterable<LongDoubleEntry>.
public class LongDoubleDenseVector implements LongDoubleVector {

    private static final long serialVersionUID = 1L;

    /** The value given to non-explicit entries in the vector. */
    private static final double missingEntries = 0;
    /** The internal array representing this vector. */
    private double[] elements;
    /** The index after the last explicit entry in the vector. */
    private int idxAfterLast;

    public LongDoubleDenseVector() {
        this(8);
    }
    
    public LongDoubleDenseVector(int initialCapacity) {
        elements = new double[initialCapacity];
        idxAfterLast = 0;
    }

    public LongDoubleDenseVector(double[] elements) {
        this.elements = elements;
        idxAfterLast = elements.length;
    }
    
    /** Gets a deep copy of this vector. */
    public LongDoubleDenseVector(LongDoubleDenseVector other) {
        this.elements = DoubleArrays.copyOf(other.elements);
        this.idxAfterLast = other.idxAfterLast;
    }

    /** Copy constructor. */
    public LongDoubleDenseVector(LongDoubleVector other) {
        // TODO: Exploit the number of non-zero entries in other.
        this();
        final LongDoubleDenseVector thisVec = this; 
        other.apply(new FnLongDoubleToDouble() {            
            @Override
            public double call(long idx, double val) {
                thisVec.set(idx, val);
                return val;
            }
        });
    }
    
    /** Copy factory method. */
    @Override
    public LongDoubleVector copy() {
        return new LongDoubleDenseVector(this);
    }
    
    /**
     * Gets the i'th entry in the vector.
     * @param i The index of the element to get.
     * @return The value of the element to get.
     */
    public double get(long i) {
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
    public double set(long idx, double value) {
        int i = SafeCast.safeLongToInt(idx);
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        double old = elements[i];
        elements[i] = value;
        return old;
    }

    public void add(long idx, double value) {
        int i = SafeCast.safeLongToInt(idx);
        idxAfterLast = Math.max(idxAfterLast, i + 1);
        ensureCapacity(idxAfterLast);
        elements[i] += value;
    }

    public void scale(double multiplier) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] *= multiplier;
        }
    }

    @Override
    public double dot(double[] other) {
        int max = Math.min(idxAfterLast, other.length);
        double dot = 0;
        for (int i=0; i<max; i++) {
            dot += elements[i] * other[i];
        }
        return dot;
    }

    @Override
    public double dot(LongDoubleVector y) {
        if (y instanceof LongDoubleDenseVector){
            LongDoubleDenseVector other = (LongDoubleDenseVector) y;
            int max = Math.min(idxAfterLast, other.idxAfterLast);
            double dot = 0;
            for (int i=0; i<max; i++) {
                dot += elements[i] * y.get(i);
            }
            return dot;
        } else {
            return y.dot(this);
        }
    }
    
    @Override
    public void apply(FnLongDoubleToDouble function) {
        for (int i=0; i<idxAfterLast; i++) {
            elements[i] = function.call(i, elements[i]);
        }
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(LongDoubleVector other) {
        // TODO: Add special case for LongDoubleDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.DoubleAdd()));
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(LongDoubleVector other) {
        // TODO: Add special case for LongDoubleDenseVector.
        other.apply(new SparseBinaryOpApplier(this, new Lambda.DoubleSubtract()));
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(LongDoubleVector other) {
        // TODO: Add special case for LongDoubleDenseVector.
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
    public int lookupIndex(double value, double delta) {
        for (int i=0; i<elements.length; i++) {
            if (Primitives.equals(elements[i], value, delta)) {
                return i;
            }
        }
        return -1;
    }

    /** Gets a NEW array containing all the elements in this vector. */
    public double[] toNativeArray() {
        return Arrays.copyOf(elements, idxAfterLast);
    }
    
    /** Gets the INTERNAL representation of this vector. */
    public double[] getInternalElements() {
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
    public long getDimension() {
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
    public static double[] ensureCapacity(double[] elements, int size) {
        if (size > elements.length) {
            double[] tmp = new double[size*2];
            System.arraycopy(elements, 0, tmp, 0, elements.length);
            elements = tmp;
        }
        return elements;
    }

}
