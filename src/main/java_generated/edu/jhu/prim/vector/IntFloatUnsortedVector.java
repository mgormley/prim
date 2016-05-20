package edu.jhu.prim.vector;

import java.util.Arrays;
import java.util.Iterator;

import edu.jhu.prim.iter.IntIter;
import edu.jhu.prim.map.IntFloatEntry;
import edu.jhu.prim.sort.IntFloatSort;
import edu.jhu.prim.util.Lambda.FnIntFloatToFloat;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.util.math.FastMath;

/**
 * Lazily-sorted vector.
 * 
 * @author Travis Wolfe <twolfe18@gmail.com>
 */
public class IntFloatUnsortedVector extends AbstractIntFloatVector implements IntFloatVector, Iterable<IntFloatEntry> {

    private static final long serialVersionUID = 1L;

    public static final int defaultSparseInitCapacity = 16;
    
    public boolean printWarnings = true;
    
    protected int[] idx;
    protected float[] vals;
    protected int top;          	// indices less than this are valid
    protected boolean compacted;    // are elements of idx sorted and unique?

    public IntFloatUnsortedVector() {
        this(defaultSparseInitCapacity);
    }
    
    public IntFloatUnsortedVector(int initCapacity) {
        idx = new int[initCapacity];
        vals = new float[initCapacity];
        top = 0;
        compacted = true;
    }

    /** Copy constructor. */
    public IntFloatUnsortedVector(IntFloatUnsortedVector other) {
        this(other.idx.length);
        for (int i=0; i<other.top; i++) {
            this.idx[i] = other.idx[i];
            this.vals[i] = other.vals[i];
        }
        this.top = other.top;
        this.compacted = other.compacted;
    }
    
    public IntFloatUnsortedVector(int[] idx, float[] values) {
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
    public IntFloatUnsortedVector clone() {
        IntFloatUnsortedVector v = new IntFloatUnsortedVector(0);
        v.idx = Arrays.copyOf(idx, idx.length);
        v.vals = Arrays.copyOf(vals, vals.length);
        v.top = top;
        v.compacted = compacted;
        return v;
    }

    @Override
    public IntFloatVector copy() {
        return clone();
    }

    @Override
    public float get(int index) {
        // if we need to do an O(#non-zero) operation here anyway, might as well compact
        compact();
        int i = findIndexMatching(index);
        if(i < 0) return 0;
        else return vals[i];
    }

    /**
     * @return -1 if not found
     */
    private int findIndexMatching(int index) {
        compact();
        return findIndexMatching(index, 0, top-1);
    }

    private int findIndexMatching(int index, int imin, int imax) {
        assert compacted;
        int needle = index;
        while(imin < imax) {
            int imid = (imax - imin) / 2 + imin; assert(imid < imax);
            int mid = idx[imid];
            if(mid < needle)
                imin = imid + 1;
            else
                imax = imid;
        }
        if(imax == imin) {
            int found = idx[imin];
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
        IntFloatSort.sortIndexAsc(idx, vals, top);

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
    
    public static boolean dbgEquals(IntFloatUnsortedVector a, IntFloatUnsortedVector b) {
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
    public float set(int index, float value) {
        compact();
        int i = findIndexMatching(index);
        if(i < 0) {
            add(index, value);
            compacted = false;
            return 0;
        } else {
            float old = vals[i];
            vals[i] = value;
            return old;
        }
    }

    public void add(int index, float value) {
        if(value == 0) return;
        int prevIdx = top > 0 ? idx[top-1] : -1;
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
    
    public int l0Norm() {
        compact();
        return top;
    }

    public float l1Norm() {
        compact();
        float sum = 0;
        for(int i=0; i<top; i++) {
            float v = vals[i];
            if(v >= 0) sum += v;
            else sum -= v;
        }
        return sum;
    }

    public float l2Norm() {
        compact();
        float sum = 0;
        for(int i=0; i<top; i++) {
            float v = vals[i];
            sum += v * v;
        }
        return FastMath.sqrt(sum);
    }

    public float lInfNorm() {
        float biggest = 0;
        compact();
        for(int i=0; i<top; i++) {
            float v = vals[i];
            if(v < 0 && v < biggest)
                biggest = v;
            else if(v > 0 && v > biggest)
                biggest = v;
        }
        return biggest >= 0 ? biggest : -biggest;
    }

    public void makeUnitVector() { scale(1 / l2Norm()); }

    /**
     * returns true if any values are NaN or Inf
     */
    public boolean hasBadValues() {
        for(int i=0; i<top; i++) {
            float v = vals[i];
            boolean bad = Float.isNaN(v) || Float.isInfinite(v);
            if(bad) return true;
        }
        return false;
    }
    
    /*  */

    public void scale(float factor) {
        // no need to compact here: a*x + a*y = a*(x+y)
        for(int i=0; i<top; i++)
            vals[i] *= factor;
    }
    
    @Override
    public void add(IntFloatVector other) {
        final IntFloatUnsortedVector me = this;
        other.iterate(new FnIntFloatToVoid() {
            @Override
            public void call(int idx, float val) {
                me.add(idx, val);
            }
        });
    }

    @Override
    public void apply(FnIntFloatToFloat function) {
        compact();
        for(int i=0; i<top; i++) {
            vals[i] = function.call(idx[i], vals[i]);
        }
    }

    @Override
    public void iterate(FnIntFloatToVoid function) {
        compact();
        for(int i=0; i<top; i++) {
            function.call(idx[i], vals[i]);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see edu.jhu.util.vector.IntFloatMap#iterator()
     */
    @Override
    public Iterator<IntFloatEntry> iterator() {
        compact();
        return new IntFloatIterator();
    }
    
    public void applyNoCompact(FnIntFloatToFloat function) {
        for(int i=0; i<top; i++) {
            vals[i] = function.call(idx[i], vals[i]);
        }
    }

    public void iterateNoCompact(FnIntFloatToVoid function) {
        for(int i=0; i<top; i++) {
            function.call(idx[i], vals[i]);
        }
    }

    public Iterator<IntFloatEntry> iteratorNoCompact() {
        return new IntFloatIterator();
    }

    @Override
    public void subtract(IntFloatVector other) {
        final IntFloatUnsortedVector me = this;
        other.iterate(new FnIntFloatToVoid() {
            @Override
            public void call(int idx, float val) {
                me.add(idx, - val);
            }
        });
    }

    @Override
    public void product(IntFloatVector other) {
        throw new RuntimeException("not supported");
    }
    
    @Override
    public float dot(float[] other) {
        float sum = 0;
        for(int i=0; i<top; i++)
            sum += other[idx[i]] * vals[i];
        return sum;
    }

    @Override
    public float dot(IntFloatVector other) {
        if(other instanceof IntFloatUnsortedVector) {
            IntFloatUnsortedVector oth = (IntFloatUnsortedVector) other;
            IntFloatUnsortedVector smaller = this, bigger = oth;
            if(this.top > oth.top) {
                smaller = oth; bigger = this;
            }
            smaller.compact();
            bigger.compact();
            float dot = 0;
            int j = 0;
            int attempt = bigger.idx[j];
            for(int i=0; i<smaller.top; i++) {
                int needle = smaller.idx[i];
                while(attempt < needle && j < bigger.top-1)
                    attempt = bigger.idx[++j];
                if(attempt == needle)
                    dot += smaller.vals[i] * bigger.vals[j];
                if(j == bigger.top)
                    break;
            }
            return dot;
        } else {
            float dot = 0;
            for(int i=0; i<top; i++) {
                dot += vals[i] * other.get(idx[i]);
            }
            return dot;
        }
    }

    public static class SparseIdxIter implements IntIter {
        private int i = 0, top;
        private int[] idx;
        public SparseIdxIter(int[] idx, int top) {
            this.idx = idx;
            this.top = top;
        }
        @Override
        public boolean hasNext() { return i < top; }
        @Override
        public int next() { return idx[i++]; }
        @Override
        public void reset() { i = 0; }
    }

    public IntIter indices() {
        return new SparseIdxIter(idx, top);
    }

    public class IntFloatEntryImpl implements IntFloatEntry {
        private int i;
        public IntFloatEntryImpl(int i) {
            this.i = i;
        }
        public int index() {
            return idx[i];
        }
        public float get() {
            return vals[i];
        }
    }

    /**
     * This iterator is fast in the case of for(Entry e : vector) { }, however a
     * given entry should not be used after the following call to next().
     */
    public class IntFloatIterator implements Iterator<IntFloatEntry> {

        // The current entry.
        private IntFloatEntryImpl entry = new IntFloatEntryImpl(-1);

        @Override
        public boolean hasNext() {
            return entry.i + 1 < top;
        }

        @Override
        public IntFloatEntry next() {
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
    public int getNumImplicitEntries() {
        compact();
        if (top-1 >= 0) {
            return idx[top-1] + 1;
        } else {
            return 0;
        }
    }

    @Override
    public float[] toNativeArray() {
        compact();
        final float[] arr = new float[getNumImplicitEntries()];
        iterate(new FnIntFloatToVoid() {
            @Override
            public void call(int idx, float val) {
                arr[idx] = val;
            }
        });
        return arr;
    }

    /**
     * Gets the INTERNAL representation of the indices. Great care should be
     * taken to avoid touching the values beyond the used indices.
     */
    public int[] getInternalIndices() {
        return idx;
    }

    /**
     * Gets the INTERNAL representation of the values. Great care should be
     * taken to avoid touching the values beyond the used values.
     */
    public float[] getInternalValues() {
        return vals;
    }
    
    /** Gets the INTERNAL number of used entries in this vector. */
    public int getUsed() {
        return top;
    }
    
}
