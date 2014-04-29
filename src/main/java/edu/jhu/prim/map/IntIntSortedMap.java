package edu.jhu.prim.map;

import java.util.Arrays;
import java.util.Iterator;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.sort.IntIntSort;
import edu.jhu.prim.sort.IntSort;
import edu.jhu.prim.tuple.Pair;
import edu.jhu.prim.util.Lambda.FnIntIntToInt;
import edu.jhu.prim.util.Lambda.FnIntIntToVoid;

/**
 * A primitives map from ints to ints. The map is stored by keeping a sorted
 * array of indices, and an array of their corresponding values. This is useful
 * when an extremely compact representation of the map is needed.
 * 
 * @author mgormley
 */
public class IntIntSortedMap implements IntIntMap {
    
    private static final long serialVersionUID = 1L;
	protected int[] indices;
	protected int[] values;
	protected int used; // TODO: size
	
	public IntIntSortedMap() {
	    this(0);
	}
	
	public IntIntSortedMap(int initialSize) {
	    this.used = 0;
	    this.indices= new int[initialSize];
        this.values = new int[initialSize];
	}

	public IntIntSortedMap(int[] index, int[] data) {
		if (!IntSort.isSortedAscAndUnique(index)) {
			throw new IllegalStateException("Indices are not sorted ascending");
		}
		
		this.used = index.length;
		this.indices = index;
		this.values = data;
	}

	public IntIntSortedMap(IntIntSortedMap other) {
		this.used = other.used;
		this.indices = IntArrays.copyOf(other.indices);
		this.values = IntArrays.copyOf(other.values);
	}

    public IntIntSortedMap(IntIntHashMap other) {
        Pair<int[], int[]> pair = other.getIndicesAndValues();
        IntIntSort.sortIndexAsc(pair.get1(), pair.get2());
        this.used = other.size();
        this.indices = pair.get1();
        this.values = pair.get2();
    }
    
    //	// TODO: we need to break up Sort into SortIntInt, SortIntInt before adding this constructor.
    //	public SortedIntIntMap(PIntIntHashMap other) {
    //        Pair<int[], int[]> ivs = other.getIndicesAndValues();
    //        this.indices = ivs.get1();
    //        this.values = ivs.get2();
    //        this.used = indices.length;
    //        Sort.sortIndexAsc(indices, values);
    //	}

	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#clear()
     */
	@Override
    public void clear() {
		this.used = 0;
	}
	
	// TODO: rename to containsKey.
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#contains(int)
     */
	@Override
    public boolean contains(int idx) {
		return Arrays.binarySearch(indices, 0, used, idx) >= 0;
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#get(int)
     */
	@Override
    public int get(int idx) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i < 0) {
			throw new IllegalArgumentException("This map does not contain the key: " + idx);
		}
		return values[i];
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#getWithDefault(int, int)
     */
	@Override
    public int getWithDefault(int idx, int defaultVal) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i < 0) {
			return defaultVal;
		}
		return values[i];
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#remove(int)
     */
	@Override
    public void remove(int idx) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i < 0) {
			throw new IllegalArgumentException("This map does not contain the key: " + idx);
		}		
		// Shift the values over.
		System.arraycopy(indices, i+1, indices, i, used - i - 1);
		System.arraycopy(values, i+1, values, i, used - i - 1);
		used--;
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#put(int, int)
     */
	@Override
    public int put(int idx, int val) {
	    int old = 0;
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i >= 0) {
			// Just update the value.
		    old = values[i];
			values[i] = val;
			return old;
		} 
		int insertionPoint = -(i + 1);
		indices = insert(indices, insertionPoint, idx);
		values = insert(values, insertionPoint, val);
		used++;
		return old;
	}
	
    /* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#put(int, int)
     */
    @Override
    public void add(int idx, int val) {
        int i = Arrays.binarySearch(indices, 0, used, idx);
        if (i >= 0) {
            // Just add to the existing value.
            values[i] += val;
            return;
        } 
        int insertionPoint = -(i + 1);
        indices = insert(indices, insertionPoint, idx);
        values = insert(values, insertionPoint, val);
        used++;
    }
    
    public void apply(FnIntIntToInt lambda) {
        for (int i=0; i<used; i++) {
            values[i] = lambda.call(indices[i], values[i]);
        }
    }

    public void iterate(FnIntIntToVoid lambda) {
        for (int i=0; i<used; i++) {
            lambda.call(indices[i], values[i]);
        }
    }
	
	private final int[] insert(int[] array, int i, int val) {
		if (used >= array.length) {
			// Increase the capacity of the array.
			array = IntArrayList.ensureCapacity(array, used+1);
		}
		if (i < used) {
			// Shift the values over.
			System.arraycopy(array, i, array, i+1, used - i);
		}
		// Insert the new index into the array.
		array[i] = val;
		return array;
	}
		
    /*  */

	public class IntIntEntryImpl implements IntIntEntry {
		private int i;
		public IntIntEntryImpl(int i) {
			this.i = i;
		}
		public int index() {
			return indices[i];
		}
		public int get() {
			return values[i];
		}
    }

    /**
     * This iterator is fast in the case of for(Entry e : vector) { }, however a
     * given entry should not be used after the following call to next().
     */
    public class IntIntIterator implements Iterator<IntIntEntry> {

        // The current entry.
        private IntIntEntryImpl entry = new IntIntEntryImpl(-1);

        @Override
        public boolean hasNext() {
            return entry.i + 1 < used;
        }

        @Override
        public IntIntEntry next() {
            entry.i++;
            return entry;
        }

        @Override
        public void remove() {
            throw new RuntimeException("operation not supported");
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.jhu.util.vector.IntIntMap#iterator()
     */
	@Override
	public Iterator<IntIntEntry> iterator() {
		return new IntIntIterator();
	}


	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#size()
     */
	@Override
    public int size() {
		return used;
	}

	public int getUsed() {
		return used;
	}	

    /* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#getIndices()
     */
    @Override
    public int[] getIndices() {
        if (used == indices.length)
            return indices;

        int[] tmpIndices = new int[used];
        for (int i = 0; i < used; i++) {
        	tmpIndices[i] = indices[i];
        }
        return tmpIndices;
    }
    
    /* (non-Javadoc)
     * @see edu.jhu.util.vector.IntIntMap#getValues()
     */
    @Override
    public int[] getValues() {
        if (used == values.length)
            return values;

        int[] tmpValues = new int[used];
        for (int i = 0; i < used; i++) {
        	tmpValues[i] = values[i];
        }
        return tmpValues;
    }
		
    /**
     * Gets the INTERNAL representation of the indices. Great care should be
     * taken to avoid touching the values beyond the used indices.
     */
    public int[] getInternalIndices() {
        return indices;
    }

    /**
     * Gets the INTERNAL representation of the values. Great care should be
     * taken to avoid touching the values beyond the used values.
     */
    public int[] getInternalValues() {
        return values;
    }
    
}
