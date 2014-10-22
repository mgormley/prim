package edu.jhu.prim.vector;

import java.util.Arrays;
import java.util.Iterator;

import edu.jhu.prim.iter.IntIter;
import edu.jhu.prim.map.IntDoubleEntry;
import edu.jhu.prim.sort.IntDoubleSort;
import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;
import edu.jhu.prim.util.Lambda.FnIntDoubleToVoid;
import edu.jhu.prim.util.SafeCast;

/**
 * Lazily-sorted vector.
 * 
 * @author Travis Wolfe <twolfe18@gmail.com>
 */
public class IntDoubleUnsortedVector extends AbstractIntDoubleVector implements IntDoubleVector, Iterable<IntDoubleEntry> {

    private static final long serialVersionUID = 1L;

    public static final int defaultSparseInitCapacity = 16;
    
    public boolean printWarnings = true;
    
    protected int[] idx;
    protected double[] vals;
    protected int top;          	// indices less than this are valid
    protected boolean compacted;    // are elements of idx sorted and unique?

    public IntDoubleUnsortedVector() {
        this(defaultSparseInitCapacity);
    }
    
    public IntDoubleUnsortedVector(int initCapacity) {
        idx = new int[initCapacity];
        vals = new double[initCapacity];
        top = 0;
        compacted = true;
    }

    /** Copy constructor. */
    public IntDoubleUnsortedVector(IntDoubleUnsortedVector other) {
        this(other.idx.length);
        for (int i=0; i<other.top; i++) {
            this.idx[i] = other.idx[i];
            this.vals[i] = other.vals[i];
        }
        this.top = other.top;
        this.compacted = other.compacted;
    }
    
    public IntDoubleUnsortedVector(int[] idx, double[] values) {
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
    public IntDoubleUnsortedVector clone() {
        IntDoubleUnsortedVector v = new IntDoubleUnsortedVector(0);
        v.idx = Arrays.copyOf(idx, idx.length);
        v.vals = Arrays.copyOf(vals, vals.length);
        v.top = top;
        v.compacted = compacted;
        return v;
    }

    @Override
    public IntDoubleVector copy() {
        return clone();
    }

    @Override
    public double get(int index) {
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
        IntDoubleSort.sortIndexAsc(idx, vals, top);

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
    
    public static boolean dbgEquals(IntDoubleUnsortedVector a, IntDoubleUnsortedVector b) {
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
    public double set(int index, double value) {
        compact();
        int i = findIndexMatching(index);
        if(i < 0) {
            add(index, value);
            compacted = false;
            return 0;
        } else {
            double old = vals[i];
            vals[i] = value;
            return old;
        }
    }

    public void add(int index, double value) {
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

    public double l1Norm() {
        compact();
        double sum = 0;
        for(int i=0; i<top; i++) {
            double v = vals[i];
            if(v >= 0) sum += v;
            else sum -= v;
        }
        return sum;
    }

    public double l2Norm() {
        compact();
        double sum = 0;
        for(int i=0; i<top; i++) {
            double v = vals[i];
            sum += v * v;
        }
        return Math.sqrt(sum);
    }

    public double lInfNorm() {
        double biggest = 0;
        compact();
        for(int i=0; i<top; i++) {
            double v = vals[i];
            if(v < 0 && v < biggest)
                biggest = v;
            else if(v > 0 && v > biggest)
                biggest = v;
        }
        return biggest >= 0 ? biggest : -biggest;
    }

    public void makeUnitVector() { scale(1d / l2Norm()); }

    /**
     * returns true if any values are NaN or Inf
     */
    public boolean hasBadValues() {
        for(int i=0; i<top; i++) {
            double v = vals[i];
            boolean bad = Double.isNaN(v) || Double.isInfinite(v);
            if(bad) return true;
        }
        return false;
    }
    
    /*  */

    public void scale(double factor) {
        // no need to compact here: a*x + a*y = a*(x+y)
        for(int i=0; i<top; i++)
            vals[i] *= factor;
    }
    
    @Override
    public void add(IntDoubleVector other) {
        final IntDoubleUnsortedVector me = this;
        other.iterate(new FnIntDoubleToVoid() {
            @Override
            public void call(int idx, double val) {
                me.add(idx, val);
            }
        });
    }

    @Override
    public void apply(FnIntDoubleToDouble function) {
        compact();
        for(int i=0; i<top; i++) {
            vals[i] = function.call(idx[i], vals[i]);
        }
    }

    @Override
    public void iterate(FnIntDoubleToVoid function) {
        compact();
        for(int i=0; i<top; i++) {
            function.call(idx[i], vals[i]);
        }
    }

    @Override
    public void subtract(IntDoubleVector other) {
        final IntDoubleUnsortedVector me = this;
        other.iterate(new FnIntDoubleToVoid() {
            @Override
            public void call(int idx, double val) {
                me.add(idx, - val);
            }
        });
    }

    @Override
    public void product(IntDoubleVector other) {
        throw new RuntimeException("not supported");
    }

    @Override
    public double getProd() {
        throw new RuntimeException("not supported");
    }
    
    @Override
    public double dot(double[] other) {
        double sum = 0;
        for(int i=0; i<top; i++)
            sum += other[idx[i]] * vals[i];
        return sum;
    }

    @Override
    public double dot(IntDoubleVector other) {
        if(other instanceof IntDoubleUnsortedVector) {
            IntDoubleUnsortedVector oth = (IntDoubleUnsortedVector) other;
            IntDoubleUnsortedVector smaller = this, bigger = oth;
            if(this.top > oth.top) {
                smaller = oth; bigger = this;
            }
            smaller.compact();
            bigger.compact();
            double dot = 0;
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
            double dot = 0;
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

    public class IntDoubleEntryImpl implements IntDoubleEntry {
        private int i;
        public IntDoubleEntryImpl(int i) {
            this.i = i;
        }
        public int index() {
            return idx[i];
        }
        public double get() {
            return vals[i];
        }
    }

    /**
     * This iterator is fast in the case of for(Entry e : vector) { }, however a
     * given entry should not be used after the following call to next().
     */
    public class IntDoubleIterator implements Iterator<IntDoubleEntry> {

        // The current entry.
        private IntDoubleEntryImpl entry = new IntDoubleEntryImpl(-1);

        @Override
        public boolean hasNext() {
            return entry.i + 1 < top;
        }

        @Override
        public IntDoubleEntry next() {
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
     * @see edu.jhu.util.vector.IntDoubleMap#iterator()
     */
    @Override
    public Iterator<IntDoubleEntry> iterator() {
        return new IntDoubleIterator();
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
    public double[] toNativeArray() {
        compact();
        final double[] arr = new double[getNumImplicitEntries()];
        iterate(new FnIntDoubleToVoid() {
            @Override
            public void call(int idx, double val) {
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
    public double[] getInternalValues() {
        return vals;
    }
    
    /** Gets the INTERNAL number of used entries in this vector. */
    public int getUsed() {
        return top;
    }
    
}
