package edu.jhu.prim.vector;

import java.util.Arrays;
import java.util.Iterator;

import edu.jhu.prim.iter.LongIter;
import edu.jhu.prim.map.LongIntEntry;
import edu.jhu.prim.sort.LongIntSort;
import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.Lambda.FnLongIntToVoid;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.util.math.FastMath;

/**
 * Lazily-sorted vector.
 * 
 * @author Travis Wolfe <twolfe18@gmail.com>
 */
public class LongIntUnsortedVector extends AbstractLongIntVector implements LongIntVector, Iterable<LongIntEntry> {

    private static final long serialVersionUID = 1L;

    public static final int defaultSparseInitCapacity = 16;
    
    public boolean printWarnings = true;
    
    protected long[] idx;
    protected int[] vals;
    protected int top;          	// indices less than this are valid
    protected boolean compacted;    // are elements of idx sorted and unique?

    public LongIntUnsortedVector() {
        this(defaultSparseInitCapacity);
    }
    
    public LongIntUnsortedVector(int initCapacity) {
        idx = new long[initCapacity];
        vals = new int[initCapacity];
        top = 0;
        compacted = true;
    }

    /** Copy constructor. */
    public LongIntUnsortedVector(LongIntUnsortedVector other) {
        this(other.idx.length);
        for (int i=0; i<other.top; i++) {
            this.idx[i] = other.idx[i];
            this.vals[i] = other.vals[i];
        }
        this.top = other.top;
        this.compacted = other.compacted;
    }
    
    public LongIntUnsortedVector(long[] idx, int[] values) {
        if(idx != null && idx.length != values.length)
            throw new IllegalArgumentException();
        this.idx = idx;
        this.vals = values;
        this.top = idx.length;
        this.compacted = false;
    }

    protected int capacity() {
        return idx.length;
    }

    @Override
    public LongIntUnsortedVector clone() {
        LongIntUnsortedVector v = new LongIntUnsortedVector(0);
        v.idx = Arrays.copyOf(idx, idx.length);
        v.vals = Arrays.copyOf(vals, vals.length);
        v.top = top;
        v.compacted = compacted;
        return v;
    }

    @Override
    public LongIntVector copy() {
        return clone();
    }

    @Override
    public int get(long index) {
        // if we need to do an O(#non-zero) operation here anyway, might as well compact
        compact();
        int i = findIndexMatching(index);
        if(i < 0) return 0;
        else return vals[i];
    }

    /**
     * @return -1 if not found
     */
    private int findIndexMatching(long index) {
        compact();
        return findIndexMatching(index, 0, top-1);
    }

    private int findIndexMatching(long index, int imin, int imax) {
        assert compacted;
        long needle = index;
        while(imin < imax) {
            int imid = (imax - imin) / 2 + imin; assert(imid < imax);
            long mid = idx[imid];
            if(mid < needle)
                imin = imid + 1;
            else
                imax = imid;
        }
        if(imax == imin) {
            long found = idx[imin];
            if(found == needle) return imin;
        }
        return -1;
    }

    /**
     * sort indices and consolidate duplicate entries (only for sparse vectors)
     * @param freeExtraMem will allocate new arrays as small as possible to store indices/values
     * 
     * this method is protected, not private, so that sub-classes that want to observe inefficient
     * operations can override, observe, and forward back this method.
     */
    public void compact(boolean freeExtraMem) {

        if(compacted) return;
        
        // sort items by index (not including junk >=top)
        LongIntSort.sortIndexAsc(idx, vals, top);

        // let add() remove duplicate entries
        int oldTop = top;
        top = 0;
        for(int i=0; i<oldTop; i++)
            add(idx[i], vals[i]);

        if(freeExtraMem) {
            idx = Arrays.copyOf(idx, top);
            vals = Arrays.copyOf(vals, top);
        }

        compacted = true;
    }

    public void compact() { compact(false); }
    
    public static boolean dbgEquals(LongIntUnsortedVector a, LongIntUnsortedVector b) {
        if(a.top != b.top) return false;
        if(a.compacted ^ b.compacted) return false;
        for(int i=0; i<a.top; i++) {
            if(a.idx[i] != b.idx[i])
                return false;
            if(a.vals[i] != b.vals[i])
                return false;
        }
        return true;
    }

    /**
     * sets this vector to the 0 vector
     */
    public void clear() {
        top = 0;
        compacted = true;
    }

    /**
     * NOTE: this is much less efficient than calls to add().
     */
    public int set(long index, int value) {
        compact();
        int i = findIndexMatching(index);
        if(i < 0) {
            add(index, value);
            compacted = false;
            return 0;
        } else {
            int old = vals[i];
            vals[i] = value;
            return old;
        }
    }

    public void add(long index, int value) {
        if(value == 0) return;
        long prevIdx = top > 0 ? idx[top-1] : -1;
        if(index == prevIdx) {
            //System.out.printf("[add] prevIndex=%d top=%d prevVal=%.2f index=%d value=%.2f\n", prevIdx, top, vals[top-1], index, value);
            vals[top-1] += value;
            if(vals[top-1] == 0)
                top--;
        }
        else {
            //System.out.printf("[add] top=%d index=%d value=%.2f\n" , top, index, value);
            if(top == capacity()) grow();
            idx[top] = index;
            vals[top] = value;
            top++;
            compacted &= (index > prevIdx);
        }
    }

    private void grow() {
        int newSize = (int)(capacity() * 1.3d + 8d);
        idx = Arrays.copyOf(idx, newSize);
        vals = Arrays.copyOf(vals, newSize);
    }

    /*  */

    public void scale(int factor) {
        // no need to compact here: a*x + a*y = a*(x+y)
        for(int i=0; i<top; i++)
            vals[i] *= factor;
    }
    
    @Override
    public void add(LongIntVector other) {
        final LongIntUnsortedVector me = this;
        other.iterate(new FnLongIntToVoid() {
            @Override
            public void call(long idx, int val) {
                me.add(idx, val);
            }
        });
    }

    @Override
    public void apply(FnLongIntToInt function) {
        compact();
        for(int i=0; i<top; i++) {
            vals[i] = function.call(idx[i], vals[i]);
        }
    }

    @Override
    public void iterate(FnLongIntToVoid function) {
        compact();
        for(int i=0; i<top; i++) {
            function.call(idx[i], vals[i]);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see edu.jhu.util.vector.LongIntMap#iterator()
     */
    @Override
    public Iterator<LongIntEntry> iterator() {
        compact();
        return new LongIntIterator();
    }
    
    public void applyNoCompact(FnLongIntToInt function) {
        for(int i=0; i<top; i++) {
            vals[i] = function.call(idx[i], vals[i]);
        }
    }

    public void iterateNoCompact(FnLongIntToVoid function) {
        for(int i=0; i<top; i++) {
            function.call(idx[i], vals[i]);
        }
    }

    public Iterator<LongIntEntry> iteratorNoCompact() {
        return new LongIntIterator();
    }

    @Override
    public void subtract(LongIntVector other) {
        final LongIntUnsortedVector me = this;
        other.iterate(new FnLongIntToVoid() {
            @Override
            public void call(long idx, int val) {
                me.add(idx, - val);
            }
        });
    }

    @Override
    public void product(LongIntVector other) {
        throw new RuntimeException("not supported");
    }
    
    @Override
    public int dot(int[] other) {
        int sum = 0;
        for(int i=0; i<top; i++)
            sum += other[SafeCast.safeLongToInt(idx[i])] * vals[i];
        return sum;
    }

    @Override
    public int dot(LongIntVector other) {
        if(other instanceof LongIntUnsortedVector) {
            LongIntUnsortedVector oth = (LongIntUnsortedVector) other;
            LongIntUnsortedVector smaller = this, bigger = oth;
            if(this.top > oth.top) {
                smaller = oth; bigger = this;
            }
            smaller.compact();
            bigger.compact();
            int dot = 0;
            int j = 0;
            long attempt = bigger.idx[j];
            for(int i=0; i<smaller.top; i++) {
                long needle = smaller.idx[i];
                while(attempt < needle && j < bigger.top-1)
                    attempt = bigger.idx[++j];
                if(attempt == needle)
                    dot += smaller.vals[i] * bigger.vals[j];
                if(j == bigger.top)
                    break;
            }
            return dot;
        } else {
            int dot = 0;
            for(int i=0; i<top; i++) {
                dot += vals[i] * other.get(idx[i]);
            }
            return dot;
        }
    }

    public static class SparseIdxIter implements LongIter {
        private int i = 0, top;
        private long[] idx;
        public SparseIdxIter(long[] idx, int top) {
            this.idx = idx;
            this.top = top;
        }
        @Override
        public boolean hasNext() { return i < top; }
        @Override
        public long next() { return idx[i++]; }
        @Override
        public void reset() { i = 0; }
    }

    public LongIter indices() {
        return new SparseIdxIter(idx, top);
    }

    public class LongIntEntryImpl implements LongIntEntry {
        private int i;
        public LongIntEntryImpl(int i) {
            this.i = i;
        }
        public long index() {
            return idx[i];
        }
        public int get() {
            return vals[i];
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
            return entry.i + 1 < top;
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0; i<top; i++) {
            sb.append(String.format("%d:%.2f", idx[i], vals[i]));
            if(i < top - 1) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public long getNumImplicitEntries() {
        compact();
        if (top-1 >= 0) {
            return idx[top-1] + 1;
        } else {
            return 0;
        }
    }

    @Override
    public int[] toNativeArray() {
        compact();
        final int[] arr = new int[SafeCast.safeLongToInt(getNumImplicitEntries())];
        iterate(new FnLongIntToVoid() {
            @Override
            public void call(long idx, int val) {
                arr[SafeCast.safeLongToInt(idx)] = val;
            }
        });
        return arr;
    }

    /**
     * Gets the INTERNAL representation of the indices. Great care should be
     * taken to avoid touching the values beyond the used indices.
     */
    public long[] getInternalIndices() {
        return idx;
    }

    /**
     * Gets the INTERNAL representation of the values. Great care should be
     * taken to avoid touching the values beyond the used values.
     */
    public int[] getInternalValues() {
        return vals;
    }
    
    /** Gets the INTERNAL number of used entries in this vector. */
    public int getUsed() {
        return top;
    }
    
}
