package edu.jhu.prim.map;

import java.util.Arrays;
import java.util.Iterator;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.tuple.Pair;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.sort.IntLongSort;
import edu.jhu.prim.util.sort.IntSort;

/**
 * A primitives map from ints to longs. The map is stored by keeping a sorted
 * array of indices, and an array of their corresponding values. This is useful
 * when an extremely compact representation of the map is needed.
 * 
 * @author mgormley
 */
public class IntLongSortedMap implements IntLongMap {
    
    private static final long serialVersionUID = 1L;
	protected int[] indices;
	protected long[] values;
	protected int used; // TODO: size
	
	public IntLongSortedMap() {
	    this(0);
	}
	
	public IntLongSortedMap(int initialSize) {
	    this.used = 0;
	    this.indices= new int[initialSize];
        this.values = new long[initialSize];
	}

	public IntLongSortedMap(int[] index, long[] data) {
		if (!IntSort.isSortedAscAndUnique(index)) {
			throw new IllegalStateException("Indices are not sorted ascending");
		}
		
		this.used = index.length;
		this.indices = index;
		this.values = data;
	}

	public IntLongSortedMap(IntLongSortedMap other) {
		this.used = other.used;
		this.indices = IntArrays.copyOf(other.indices);
		this.values = LongArrays.copyOf(other.values);
	}

    public IntLongSortedMap(IntLongHashMap other) {
        Pair<int[], long[]> pair = other.getIndicesAndValues();
        IntLongSort.sortIndexAsc(pair.get1(), pair.get2());
        this.used = other.size();
        this.indices = pair.get1();
        this.values = pair.get2();
    }
    
    //	// TODO: we need to break up Sort into SortIntLong, SortIntLong before adding this constructor.
    //	public SortedIntLongMap(PIntLongHashMap other) {
    //        Pair<int[], long[]> ivs = other.getIndicesAndValues();
    //        this.indices = ivs.get1();
    //        this.values = ivs.get2();
    //        this.used = indices.length;
    //        Sort.sortIndexAsc(indices, values);
    //	}

	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#clear()
     */
	@Override
    public void clear() {
		this.used = 0;
	}
	
	// TODO: rename to containsKey.
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#contains(int)
     */
	@Override
    public boolean contains(int idx) {
		return Arrays.binarySearch(indices, 0, used, idx) >= 0;
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#get(int)
     */
	@Override
    public long get(int idx) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i < 0) {
			throw new IllegalArgumentException("This map does not contain the key: " + idx);
		}
		return values[i];
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#getWithDefault(int, long)
     */
	@Override
    public long getWithDefault(int idx, long defaultVal) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i < 0) {
			return defaultVal;
		}
		return values[i];
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#remove(int)
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
     * @see edu.jhu.util.vector.IntLongMap#put(int, long)
     */
	@Override
    public void put(int idx, long val) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i >= 0) {
			// Just update the value.
			values[i] = val;
			return;
		} 
		int insertionPoint = -(i + 1);
		indices = insert(indices, insertionPoint, idx);
		values = insert(values, insertionPoint, val);
		used++;
	}
	
    /* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#put(int, long)
     */
    @Override
    public void add(int idx, long val) {
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
    
    public void apply(FnIntLongToLong lambda) {
        for (int i=0; i<used; i++) {
            values[i] = lambda.call(indices[i], values[i]);
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
	
	private final long[] insert(long[] array, int i, long val) {
		if (used >= array.length) {
			// Increase the capacity of the array.
			array = LongArrayList.ensureCapacity(array, used+1);
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

	public class IntLongEntryImpl implements IntLongEntry {
		private int i;
		public IntLongEntryImpl(int i) {
			this.i = i;
		}
		public int index() {
			return indices[i];
		}
		public long get() {
			return values[i];
		}
    }

    /**
     * This iterator is fast in the case of for(Entry e : vector) { }, however a
     * given entry should not be used after the following call to next().
     */
    public class IntLongIterator implements Iterator<IntLongEntry> {

        // The current entry.
        private IntLongEntryImpl entry = new IntLongEntryImpl(-1);

        @Override
        public boolean hasNext() {
            return entry.i + 1 < used;
        }

        @Override
        public IntLongEntry next() {
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
     * @see edu.jhu.util.vector.IntLongMap#iterator()
     */
	@Override
	public Iterator<IntLongEntry> iterator() {
		return new IntLongIterator();
	}


	/* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#size()
     */
	@Override
    public int size() {
		return used;
	}

	public int getUsed() {
		return used;
	}	

    /* (non-Javadoc)
     * @see edu.jhu.util.vector.IntLongMap#getIndices()
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
     * @see edu.jhu.util.vector.IntLongMap#getValues()
     */
    @Override
    public long[] getValues() {
        if (used == values.length)
            return values;

        long[] tmpValues = new long[used];
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
    public long[] getInternalValues() {
        return values;
    }
    
}
