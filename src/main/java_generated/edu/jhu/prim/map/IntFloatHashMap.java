/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.jhu.prim.map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.sort.IntFloatSort;
import edu.jhu.prim.tuple.Pair;
import edu.jhu.prim.util.Lambda.FnIntFloatToFloat;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;
import edu.jhu.prim.vector.AbstractIntFloatVector;

/**
 * NOTICE: Changes made to this class:
 * <ul>
 * <li>This class was renamed from OpenIntToFloatHashMap to its current name.</li>
 * <li>The keys were converted from int to int and a new hash function was written.</li>
 * <li>A clear() method was added to this class.</li>
 * </ul> 
 * 
 * Open addressed map from int to float.
 * <p>This class provides a dedicated map from ints to floats with a
 * much smaller memory overhead than standard <code>java.util.Map</code>.</p>
 * <p>This class is not synchronized. The specialized iterators returned by
 * {@link #iterator()} are fail-fast: they throw a
 * <code>ConcurrentModificationException</code> when they detect the map has been
 * modified during iteration.</p>
 * @version $Id: OpenIntToFloatHashMap.java 1421448 2012-12-13 19:45:57Z tn $
 * @since 2.0
 */
public class IntFloatHashMap extends AbstractIntFloatVector implements Serializable, IntFloatMap {

    /** Status indicator for free table entries. */
    protected static final byte FREE    = 0;

    /** Status indicator for full table entries. */
    protected static final byte FULL    = 1;

    /** Status indicator for removed table entries. */
    protected static final byte REMOVED = 2;

    /** Serializable version identifier */
    private static final long serialVersionUID = -3646337053166149105L;

    /** Load factor for the map. */
    private static final float LOAD_FACTOR = 0.75f;

    /** Default starting size.
     * <p>This must be a power of two for bit mask to work properly. </p>
     */
    protected static final int DEFAULT_EXPECTED_SIZE = 16;

    /** Multiplier for size growth when map fills up.
     * <p>This must be a power of two for bit mask to work properly. </p>
     */
    private static final int RESIZE_MULTIPLIER = 2;

    /** Number of bits to perturb the index when probing for collision resolution. */
    private static final int PERTURB_SHIFT = 5;

    /** Keys table. */
    protected int[] keys;

    /** Values table. */
    protected float[] values;

    /** States table. */
    protected byte[] states;

    /** Return value for missing entries. */
    private final float missingEntries;

    /** Current size of the map. */
    private int size;

    /** Bit mask for hash values. */
    private int mask;

    /** Modifications count. */
    private transient int count;

    /**
     * Build an empty map with default size and using NaN for missing entries.
     */
    public IntFloatHashMap() {
        this(DEFAULT_EXPECTED_SIZE, Primitives.DEFAULT_MISSING_ENTRY_FLOAT);
    }

    /*  */
    
    /**
     * Build an empty map with specified size and using NaN for missing entries.
     * @param expectedSize expected number of elements in the map
     */
    public IntFloatHashMap(final int expectedSize) {
        this(expectedSize, Primitives.DEFAULT_MISSING_ENTRY_FLOAT);
    }
    
    /*  */

    /**
     * Build an empty map with specified size.
     * @param expectedSize expected number of elements in the map
     * @param missingEntries value to return when a missing entry is fetched
     */
    public IntFloatHashMap(final int expectedSize,
                                  final float missingEntries) {
        final int capacity = computeCapacity(expectedSize);
        keys   = new int[capacity];
        values = new float[capacity];
        states = new byte[capacity];
        this.missingEntries = missingEntries;
        size = 0;
        mask   = capacity - 1;
        count = 0;
    }

    /**
     * Copy constructor.
     * @param source map to copy
     */
    public IntFloatHashMap(final IntFloatHashMap source) {
        final int length = source.keys.length;
        keys = new int[length];
        System.arraycopy(source.keys, 0, keys, 0, length);
        values = new float[length];
        System.arraycopy(source.values, 0, values, 0, length);
        states = new byte[length];
        System.arraycopy(source.states, 0, states, 0, length);
        missingEntries = source.missingEntries;
        size  = source.size;
        mask  = source.mask;
        count = source.count;
    }
    
    /** Builds a map with the given keys and values. */
    public IntFloatHashMap(int[] keys, float[] vals) {
        this(keys.length, Primitives.DEFAULT_MISSING_ENTRY_FLOAT);
        if (keys.length != vals.length) {
            throw new IllegalStateException("keys and vals must be of the same length");
        }
        for (int i=0; i<keys.length; i++) {
            this.put(keys[i], vals[i]);
        }
    }

    /**
     * Compute the capacity needed for a given size.
     * @param expectedSize expected size of the map
     * @return capacity to use for the specified size
     */
    private static int computeCapacity(final int expectedSize) {
        if (expectedSize == 0) {
            return 1;
        }
        final int capacity   = (int) InternalFastMath.ceil(expectedSize / LOAD_FACTOR);
        final int powerOfTwo = Integer.highestOneBit(capacity);
        if (powerOfTwo == capacity) {
            return capacity;
        }
        return nextPowerOfTwo(capacity);
    }

    /**
     * Find the smallest power of two greater than the input value
     * @param i input value
     * @return smallest power of two greater than the input value
     */
    private static int nextPowerOfTwo(final int i) {
        return Integer.highestOneBit(i) << 1;
    }

    /**
     * Get the stored value associated with the given key
     * @param key key associated with the data
     * @return data associated with the key
     */
    public float get(final int key) {
        return getWithDefault(key, missingEntries);
    }
    
    @Override
    public float getWithDefault(int key, float missingEntries) {
        final int hash  = hashOf(key);
        int index = hash & mask;
        if (contains(key, index)) {
            return values[index];
        }

        if (states[index] == FREE) {
            return missingEntries;
        }

        int j = index;
        for (int perturb = perturb(hash); states[index] != FREE; perturb >>= PERTURB_SHIFT) {
            j = probe(perturb, j);
            index = j & mask;
            if (contains(key, index)) {
                return values[index];
            }
        }

        return missingEntries;
    }

    /**
     * Check if a value is associated with a key.
     * @param key key to check
     * @return true if a value is associated with key
     */
    public boolean contains(final int key) {

        final int hash  = hashOf(key);
        int index = hash & mask;
        if (contains(key, index)) {
            return true;
        }

        if (states[index] == FREE) {
            return false;
        }

        int j = index;
        for (int perturb = perturb(hash); states[index] != FREE; perturb >>= PERTURB_SHIFT) {
            j = probe(perturb, j);
            index = j & mask;
            if (contains(key, index)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Get an iterator over map elements.
     * <p>The specialized iterators returned are fail-fast: they throw a
     * <code>ConcurrentModificationException</code> when they detect the map
     * has been modified during iteration.</p>
     * @return iterator over the map elements
     */
    public Iterator<IntFloatEntry> iterator() {
        return new MapIterator();
    }

    /**
     * Perturb the hash for starting probing.
     * @param hash initial hash
     * @return perturbed hash
     */
    private static int perturb(final int hash) {
        return hash & 0x7fffffff;
    }

    /**
     * Find the index at which a key should be inserted
     * @param key key to lookup
     * @return index at which key should be inserted
     */
    private int findInsertionIndex(final int key) {
        return findInsertionIndex(keys, states, key, mask);
    }

    /**
     * Find the index at which a key should be inserted
     * @param keys keys table
     * @param states states table
     * @param key key to lookup
     * @param mask bit mask for hash values
     * @return index at which key should be inserted
     */
    private static int findInsertionIndex(final int[] keys, final byte[] states,
                                          final int key, final int mask) {
        final int hash = hashOf(key);
        int index = hash & mask;
        if (states[index] == FREE) {
            return index;
        } else if (states[index] == FULL && keys[index] == key) {
            return changeIndexSign(index);
        }

        int perturb = perturb(hash);
        int j = index;
        if (states[index] == FULL) {
            while (true) {
                j = probe(perturb, j);
                index = j & mask;
                perturb >>= PERTURB_SHIFT;

                if (states[index] != FULL || keys[index] == key) {
                    break;
                }
            }
        }

        if (states[index] == FREE) {
            return index;
        } else if (states[index] == FULL) {
            // due to the loop exit condition,
            // if (states[index] == FULL) then keys[index] == key
            return changeIndexSign(index);
        }

        final int firstRemoved = index;
        while (true) {
            j = probe(perturb, j);
            index = j & mask;

            if (states[index] == FREE) {
                return firstRemoved;
            } else if (states[index] == FULL && keys[index] == key) {
                return changeIndexSign(index);
            }

            perturb >>= PERTURB_SHIFT;

        }

    }

    /**
     * Compute next probe for collision resolution
     * @param perturb perturbed hash
     * @param j previous probe
     * @return next probe
     */
    private static int probe(final int perturb, final int j) {
        return (j << 2) + j + perturb + 1;
    }

    /**
     * Change the index sign
     * @param index initial index
     * @return changed index
     */
    private static int changeIndexSign(final int index) {
        return -index - 1;
    }

    /**
     * Get the number of elements stored in the map.
     * @return number of elements stored in the map
     */
    public int size() {
        return size;
    }

    public void remove(final int key) {
        removeAndGet(key);
    }
    
    /**
     * Remove the value associated with a key.
     * @param key key to which the value is associated
     * @return removed value
     */
    public float removeAndGet(final int key) {

        final int hash  = hashOf(key);
        int index = hash & mask;
        if (contains(key, index)) {
            return doRemove(index);
        }

        if (states[index] == FREE) {
            return missingEntries;
        }

        int j = index;
        for (int perturb = perturb(hash); states[index] != FREE; perturb >>= PERTURB_SHIFT) {
            j = probe(perturb, j);
            index = j & mask;
            if (contains(key, index)) {
                return doRemove(index);
            }
        }

        return missingEntries;
    }
    
    /** Removes all entries from the hash map. */
    public void clear() {
        final int capacity = keys.length;
        Arrays.fill(keys, 0);
        Arrays.fill(values, 0);
        Arrays.fill(states, FREE);
        size = 0;
        mask   = capacity - 1;
        count = 0;
    }

    /**
     * Check if the tables contain an element associated with specified key
     * at specified index.
     * @param key key to check
     * @param index index to check
     * @return true if an element is associated with key at index
     */
    private boolean contains(final int key, final int index) {
        return (key != 0 || states[index] == FULL) && keys[index] == key;
    }

    /**
     * Remove an element at specified index.
     * @param index index of the element to remove
     * @return removed value
     */
    private float doRemove(int index) {
        keys[index]   = 0;
        states[index] = REMOVED;
        final float previous = values[index];
        values[index] = missingEntries;
        --size;
        ++count;
        return previous;
    }

    public float put(final int key, final float value) {
        return putAndGet(key, value);
    }
    
    /**
     * Put a value associated with a key in the map.
     * @param key key to which value is associated
     * @param value value to put in the map
     * @return previous value associated with the key
     */
    public float putAndGet(final int key, final float value) {
        int index = findInsertionIndex(key);
        float previous = missingEntries;
        boolean newMapping = true;
        if (index < 0) {
            index = changeIndexSign(index);
            previous = values[index];
            newMapping = false;
        }
        keys[index]   = key;
        states[index] = FULL;
        values[index] = value;
        if (newMapping) {
            ++size;
            if (shouldGrowTable()) {
                growTable();
            }
            ++count;
        }
        return previous;
    }

    public void add(final int key, final float value) {
        addAndGet(key, value);
    }
    
    /**
     * Just like putAndGet, but adds to the previous value instead of replacing
     * the previous value.
     */
    public float addAndGet(final int key, final float value) {
        int index = findInsertionIndex(key);
        float previous = missingEntries;
        boolean newMapping = true;
        if (index < 0) {
            index = changeIndexSign(index);
            previous = values[index];
            newMapping = false;
        }
        keys[index]   = key;
        states[index] = FULL;
        values[index] = previous + value;
        if (newMapping) {
            ++size;
            if (shouldGrowTable()) {
                growTable();
            }
            ++count;
        }
        return previous;
    }

    /**
     * Grow the tables.
     */
    private void growTable() {

        final int oldLength      = states.length;
        final int[] oldKeys      = keys;
        final float[] oldValues = values;
        final byte[] oldStates   = states;

        final int newLength = RESIZE_MULTIPLIER * oldLength;
        final int[] newKeys = new int[newLength];
        final float[] newValues = new float[newLength];
        final byte[] newStates = new byte[newLength];
        final int newMask = newLength - 1;
        for (int i = 0; i < oldLength; ++i) {
            if (oldStates[i] == FULL) {
                final int key = oldKeys[i];
                final int index = findInsertionIndex(newKeys, newStates, key, newMask);
                newKeys[index]   = key;
                newValues[index] = oldValues[i];
                newStates[index] = FULL;
            }
        }

        mask   = newMask;
        keys   = newKeys;
        values = newValues;
        states = newStates;

    }

    /**
     * Check if tables should grow due to increased size.
     * @return true if  tables should grow
     */
    private boolean shouldGrowTable() {
        return size > (mask + 1) * LOAD_FACTOR;
    }

    /**
     * Compute the hash value of a key
     * @param key key to hash
     * @return hash value of the key
     */
    private static int hashOf(final int key) {
        return Primitives.hashOfInt(key);
    }

    /** Iterator class for the map. */
    public class MapIterator implements Iterator<IntFloatEntry>, IntFloatEntry {

        /** Reference modification count. */
        private final int referenceCount;

        /** Index of current element. */
        private int current;

        /** Index of next element. */
        private int next;

        /**
         * Simple constructor.
         */
        private MapIterator() {

            // preserve the modification count of the map to detect concurrent modifications later
            referenceCount = count;

            // initialize current index
            next = -1;
            try {
                advance();
            } catch (NoSuchElementException nsee) { // NOPMD
                // ignored
            }

        }

        /**
         * Check if there is a next element in the map.
         * @return true if there is a next element
         */
        public boolean hasNext() {
            return next >= 0;
        }
        
        public IntFloatEntry next() {
            advance();
            return this;
        }
        
        public void remove() {
            throw new RuntimeException("not implemented");
        }

        /**
         * Get the key of current entry.
         * @return key of current entry
         * @exception ConcurrentModificationException if the map is modified during iteration
         * @exception NoSuchElementException if there is no element left in the map
         */
        public int index()
            throws ConcurrentModificationException, NoSuchElementException {
            if (referenceCount != count) {
                throw new ConcurrentModificationException();
            }
            if (current < 0) {
                throw new NoSuchElementException();
            }
            return keys[current];
        }

        /**
         * Get the value of current entry.
         * @return value of current entry
         * @exception ConcurrentModificationException if the map is modified during iteration
         * @exception NoSuchElementException if there is no element left in the map
         */
        public float get()
            throws ConcurrentModificationException, NoSuchElementException {
            if (referenceCount != count) {
                throw new ConcurrentModificationException();
            }
            if (current < 0) {
                throw new NoSuchElementException();
            }
            return values[current];
        }

        /**
         * Advance iterator one step further.
         * @exception ConcurrentModificationException if the map is modified during iteration
         * @exception NoSuchElementException if there is no element left in the map
         */
        public void advance()
            throws ConcurrentModificationException, NoSuchElementException {

            if (referenceCount != count) {
                throw new ConcurrentModificationException();
            }

            // advance on step
            current = next;

            // prepare next step
            try {
                while (states[++next] != FULL) { // NOPMD
                    // nothing to do
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                next = -2;
                if (current < 0) {
                    throw new NoSuchElementException();
                }
            }

        }

    }

    /**
     * Read a serialized object.
     * @param stream input stream
     * @throws IOException if object cannot be read
     * @throws ClassNotFoundException if the class corresponding
     * to the serialized object cannot be found
     */
    private void readObject(final ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        count = 0;
    }
    
    @Override
    public int[] getIndices() {
        int cur = 0;
        int[] tmpKeys = new int[size()];
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                tmpKeys[cur++] = keys[i];
            }
        }
        return tmpKeys;
    }

    @Override
    public float[] getValues() {
        int cur = 0;
        float[] tmpVals = new float[size()];
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                tmpVals[cur++] = values[i];
            }
        }
        return tmpVals;
    }
    
    public Pair<int[], float[]> getIndicesAndValues() {
        int cur = 0;
        int[] tmpKeys = new int[size()];
        float[] tmpVals = new float[size()];
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                tmpKeys[cur] = keys[i];
                tmpVals[cur] = values[i];
                cur++;
            }
        }
        return new Pair<int[], float[]>(tmpKeys, tmpVals);
    }
    
    public void apply(FnIntFloatToFloat lambda) {
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                values[i] = lambda.call(keys[i], values[i]);
            }
        }
    }
    
    public void iterate(FnIntFloatToVoid lambda) {
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                lambda.call(keys[i], values[i]);
            }
        }
    }
    
    @Override
    public int hashCode() {
        int h = 0;
        int[] indices = getIndices();
        float[] values = getValues();
        IntFloatSort.sortIndexAsc(indices, values);
        for (int i=0; i<indices.length; i++) {
            int result = 17;
            result = 37*result + Primitives.hashOfInt(indices[i]);
            result = 37*result + Primitives.hashOfFloat(values[i]);
            h += result;
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        IntFloatHashMap other = (IntFloatHashMap) obj;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i=0; i<keys.length; i++) {
            if (states[i] == FULL) {
                if (!other.contains(keys[i])) 
                    return false;
                if (other.get(keys[i]) != values[i])
                    return false;
            }
        }
        return true;
    }
}
