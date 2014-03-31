package edu.jhu.prim.map;

import java.util.Arrays;
import java.util.Iterator;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.sort.LongIntSort;
import edu.jhu.prim.sort.LongSort;
import edu.jhu.prim.tuple.Pair;
import edu.jhu.prim.util.Lambda.FnLongIntToInt;

/**
 * A primitives map from longs to ints. The map is stored by keeping a sorted
 * array of indices, and an array of their corresponding values. This is useful
 * when an extremely compact representation of the map is needed.
 * 
 * @author mgormley
 */
public class LongIntSortedMap implements LongIntMap {
    
    private static final long serialVersionUID = 1L;
	protected long[] indices;
	protected int[] values;
	protected int used; // TODO: size
	
	public LongIntSortedMap() {
	    this(0);
	}
	
	public LongIntSortedMap(int initialSize) {
	    this.used = 0;
	    this.indices= new long[initialSize];
        this.values = new int[initialSize];
	}

	public LongIntSortedMap(long[] index, int[] data) {
		if (!LongSort.isSortedAscAndUnique(index)) {
			throw new IllegalStateException("Indices are not sorted ascending");
		}
		
		this.used = index.length;
		this.indices = index;
		this.values = data;
	}

	public LongIntSortedMap(LongIntSortedMap other) {
		this.used = other.used;
		this.indices = LongArrays.copyOf(other.indices);
		this.values = IntArrays.copyOf(other.values);
	}

    public LongIntSortedMap(LongIntHashMap other) {
        Pair<long[], int[]> pair = other.getIndicesAndValues();
        LongIntSort.sortIndexAsc(pair.get1(), pair.get2());
        this.used = other.size();
        this.indices = pair.get1();
        this.values = pair.get2();
    }
    
    //	// TODO: we need to break up Sort into SortLongInt, SortIntInt before adding this constructor.
    //	public SortedLongIntMap(PLongIntHashMap other) {
    //        Pair<long[], int[]> ivs = other.getIndicesAndValues();
    //        this.indices = ivs.get1();
    //        this.values = ivs.get2();
    //        this.used = indices.length;
    //        Sort.sortIndexAsc(indices, values);
    //	}

	/* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#clear()
     */
	@Override
    public void clear() {
		this.used = 0;
	}
	
	// TODO: rename to containsKey.
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#contains(long)
     */
	@Override
    public boolean contains(long idx) {
		return Arrays.binarySearch(indices, 0, used, idx) >= 0;
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#get(long)
     */
	@Override
    public int get(long idx) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i < 0) {
			throw new IllegalArgumentException("This map does not contain the key: " + idx);
		}
		return values[i];
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#getWithDefault(long, int)
     */
	@Override
    public int getWithDefault(long idx, int defaultVal) {
		int i = Arrays.binarySearch(indices, 0, used, idx);
		if (i < 0) {
			return defaultVal;
		}
		return values[i];
	}
	
	/* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#remove(long)
     */
	@Override
    public void remove(long idx) {
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
     * @see edu.jhu.util.vector.LongIntMap#put(long, int)
     */
	@Override
    public int put(long idx, int val) {
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
     * @see edu.jhu.util.vector.LongIntMap#put(long, int)
     */
    @Override
    public void add(long idx, int val) {
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
    
    public void apply(FnLongIntToInt lambda) {
        for (int i=0; i<used; i++) {
            values[i] = lambda.call(indices[i], values[i]);
        }
    }
	
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

	public class LongIntEntryImpl implements LongIntEntry {
		private int i;
		public LongIntEntryImpl(int i) {
			this.i = i;
		}
		public long index() {
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
    public class LongIntIterator implements Iterator<LongIntEntry> {

        // The current entry.
        private LongIntEntryImpl entry = new LongIntEntryImpl(-1);

        @Override
        public boolean hasNext() {
            return entry.i + 1 < used;
        }

        @Override
        public LongIntEntry next() {
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
     * @see edu.jhu.util.vector.LongIntMap#iterator()
     */
	@Override
	public Iterator<LongIntEntry> iterator() {
		return new LongIntIterator();
	}


	/* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#size()
     */
	@Override
    public int size() {
		return used;
	}

	public int getUsed() {
		return used;
	}	

    /* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#getIndices()
     */
    @Override
    public long[] getIndices() {
        if (used == indices.length)
            return indices;

        long[] tmpIndices = new long[used];
        for (int i = 0; i < used; i++) {
        	tmpIndices[i] = indices[i];
        }
        return tmpIndices;
    }
    
    /* (non-Javadoc)
     * @see edu.jhu.util.vector.LongIntMap#getValues()
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
    public long[] getInternalIndices() {
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
