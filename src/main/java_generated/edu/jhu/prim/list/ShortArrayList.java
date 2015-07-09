package edu.jhu.prim.list;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Array list of short primitives.
 * @author mgormley
 */
public class ShortArrayList implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** The internal array representing this list. */
    protected short[] elements;
    /** The number of elements in the list. */
    protected int size;
    
    public ShortArrayList() {
        this(8);
    }
    
    public ShortArrayList(int initialCapacity) {
        elements = new short[initialCapacity];
        size = 0;
    }
    
    /** Copy constructor. */
    public ShortArrayList(ShortArrayList other) {
        this(other.size);
        add(other);
    }
    
    /**
     * Adds the value to the end of the list.
     * @param value The value to add.
     */
    public void add(short value) {
        ensureCapacity(size + 1);
        elements[size] = value;
        size++;
    }
    
    /**
     * Gets the i'th element of the array list.
     * @param i The index of the element to get.
     * @return The value of the element to get.
     */
    public short get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        return elements[i];
    }
    
    /**
     * Sets the i'th element of the array list to the given value.
     * @param i The index to set.
     * @param value The value to set.
     */
    public void set(int i, short value) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        elements[i] = value;
    }

    /**
     * Adds all the elements in the given array to the array list.
     * @param values The values to add to the array list.
     */
    public void add(short[] values) {
        ensureCapacity(size + values.length);
        for (short element : values) {
            this.add(element);
        }
    }
    
    /**
     * Adds all the elements in the given array list to the array list.
     * @param values The values to add to the array list.
     */
    public void add(ShortArrayList values) {
        ensureCapacity(size + values.size);
        for (int i=0; i<values.size; i++) {
            this.add(values.elements[i]);
        }
    }
    
    /**
     * Gets the index of the first element in this list with the specified
     * value, or -1 if it is not present.
     * 
     * @param value The value to search for.
     * @return The index or -1 if not present.
     */
    public int lookupIndex(short value) {
        for (int i=0; i<elements.length; i++) {
            if (elements[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets a NEW array containing all the elements in this array list.
     * @return The new array containing the elements in this list.
     */
    public short[] toNativeArray() {
        return Arrays.copyOf(elements, size);
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
    public short[] elements() {
        this.trimToSize();
        return elements;
    }

    /**
     * Gets the internal representation of this list. CAUTION: this should not
     * be called without carefully handling the result.
     */
    public short[] getInternalElements() {
        return elements;
    }
    
    /**
     * Trims the internal array to exactly the size of the list.
     */
    public void trimToSize() {
        if (size != elements.length) { 
            elements = Arrays.copyOf(elements, size);
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
    public static short[] ensureCapacity(short[] elements, int size) {
        if (size > elements.length) {
            short[] tmp = new short[size*2];
            System.arraycopy(elements, 0, tmp, 0, elements.length);
            elements = tmp;
        }
        return elements;
    }

    /**
     * Gets the number of elements in the list.
     * @return The size of the list.
     */
    public int size() {
        return size;
    }
    
    /**
     * Removes all elements from this array list.
     */
    public void clear() {
        size = 0;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShortArrayList [");
        for (int i=0; i<size; i++) {
            sb.append(i);
            if (i != size-1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
}
