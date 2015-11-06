package edu.jhu.prim.list;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Array list of int primitives.
 * @author mgormley
 */
public class IntArrayList implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** The internal array representing this list. */
    protected int[] elements;
    /** The number of elements in the list. */
    protected int size;
    
    public IntArrayList() {
        this(8);
    }

    public IntArrayList(int[] elements) {
        this.elements = elements;
        this.size = elements.length;
    }
    
    public IntArrayList(int initialCapacity) {
        elements = new int[initialCapacity];
        size = 0;
    }
    
    /** Copy constructor. */
    public IntArrayList(IntArrayList other) {
        this(other.size);
        add(other);
    }
    
    /**
     * Adds the value to the end of the list.
     * @param value The value to add.
     */
    public void add(int value) {
        ensureCapacity(size + 1);
        elements[size] = value;
        size++;
    }
    
    /**
     * Gets the i'th element of the array list.
     * @param i The index of the element to get.
     * @return The value of the element to get.
     */
    public int get(int i) {
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
    public void set(int i, int value) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        elements[i] = value;
    }

    /**
     * Adds all the elements in the given array to the array list.
     * @param values The values to add to the array list.
     */
    public void add(int[] values) {
        ensureCapacity(size + values.length);
        for (int element : values) {
            this.add(element);
        }
    }
    
    /**
     * Adds all the elements in the given array list to the array list.
     * @param values The values to add to the array list.
     */
    public void add(IntArrayList values) {
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
    public int lookupIndex(int value) {
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
    public int[] toNativeArray() {
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
    public int[] elements() {
        this.trimToSize();
        return elements;
    }

    /**
     * Gets the internal representation of this list. CAUTION: this should not
     * be called without carefully handling the result.
     */
    public int[] getInternalElements() {
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
    public static int[] ensureCapacity(int[] elements, int size) {
        if (size > elements.length) {
            int[] tmp = new int[size*2];
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
    
    /** Returns true iff the list is empty. */
    public boolean isEmpty() {
        return size == 0;
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
        sb.append("IntArrayList [");
        for (int i=0; i<size; i++) {
            sb.append(elements[i]);
            if (i != size-1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /** Removes all identical neighboring elements, resizing the array list accordingly. */
    public void uniq() {
        if (size <= 1) { return; }
        int cursor = 0;
        for (int i=1; i<size; i++) {
            if (elements[cursor] != elements[i]) {
                cursor++;
                elements[cursor] = elements[i];
            }
        }
        size = cursor+1;
    }
    
}
