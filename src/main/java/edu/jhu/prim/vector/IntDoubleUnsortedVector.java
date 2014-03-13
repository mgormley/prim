package edu.jhu.prim.vector;

import java.util.*;

import travis.Vector;
import edu.jhu.prim.map.IntDoubleEntry;
import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;

/**
 * an adapter to get my Vector class to play nice with Matt's code.
 * @author travis
 */
public class IntDoubleUnsortedVector extends Vector implements IntDoubleVector, Iterable<IntDoubleEntry> {
	
	private static final long serialVersionUID = 3455462422261002449L;
	
	public boolean warnOfInefficientOps = false;
	public boolean showStackTracesOfInefficientOps = false;
	
	public IntDoubleUnsortedVector() {
		this(Vector.defaultSparseInitCapacity);
	}
	
	public IntDoubleUnsortedVector(int initialSize) {
		super(false, initialSize);
	}
	
	public IntDoubleUnsortedVector(int[] indices, double[] values) {
		super(indices, values);
	}
	
	/** try not to use this unless absolutely needed */
	public int[] getInternalIndices() {
		if(tags != null)
			throw new IllegalStateException();
		return idx;
	}
	
	/** try not to use this unless absolutely needed */
	public double[] getInternalValues() {
		return vals;
	}
	
	/**
	 * Returns how many indices are used to represent this vector
	 * (note this may be more than the number of non-zero indices if
	 *  duplicates were added and no compaction has been run).
	 * This is not the same thing as the ``dimension" of a vector.
	 */
	public int size() {
		return top;
	}
	
	@Override
	public double dot(double[] other) {
		Vector dense = new Vector(other);
		return dense.dot(this);
	}

	@Override
	public double dot(IntDoubleVector other) {
		double dot = 0d;
		assert this.isSparse();
		for(int i=0; i<top; i++) {
			int idx = super.idx[i];
			dot += super.vals[i] * other.get(idx);
		}
		return dot;
	}
	
	private void warnOfInefficientOp() {
		if(warnOfInefficientOps) {
            System.err.println("WARNING: you are calling compact, which means you've "
                    + "lost the benefit of using the ``unsorted\" variant of Vector.");
            if(showStackTracesOfInefficientOps) {
                for(StackTraceElement ste : Thread.currentThread().getStackTrace())
                    System.err.println("\t" + ste);
                System.err.println();
            }
        }
	}
	
	@Override
	public void compact(boolean freeExtraMem) {
		warnOfInefficientOp();
		super.compact(freeExtraMem);
	}
	
	@Override
	public void compact() {
		warnOfInefficientOp();
        super.compact();
	}

	@Override
	public void apply(FnIntDoubleToDouble function) {
		if(isSparse()) {
			if(tags != null) throw new IllegalStateException();
			compact();
			for(int i=0; i<top; i++)
                vals[i] = function.call(idx[i], vals[i]);
		}
		else {
			for(int i=0; i<vals.length; i++)
	            vals[i] = function.call(i, vals[i]);
		}
	}

	@Override
	public void add(IntDoubleVector other) {
		if(other instanceof IntDoubleUnsortedVector) {
			IntDoubleUnsortedVector that = (IntDoubleUnsortedVector) other;
			super.add(that);
		}
		else throw reasonWhyICantImplThis();
	}

	@Override
	public void subtract(IntDoubleVector other) {
		throw reasonWhyICantImplThis();
	}

	@Override
	public void product(IntDoubleVector other) {
		throw reasonWhyICantImplThis();
	}
	
	private RuntimeException reasonWhyICantImplThis() {
		throw new RuntimeException("I can't implement some methods involving "
				+ "IntDoubleVector because I don't know either their dimension "
				+ "or the maximum non-zero index on which to call get()");
	}

	@Override
	public IntDoubleVector copy() {
		IntDoubleUnsortedVector n = new IntDoubleUnsortedVector();
		n.vals = super.vals.clone();
		n.tags = null;	assert super.tags == null;
		n.idx = super.idx.clone();
		n.top = super.top;
		n.compacted = super.compacted;
		n.printWarnings = super.printWarnings;
		return n;
	}
	
	public static class IntDoubleEntryImpl implements IntDoubleEntry {
		private Map.Entry<Integer, Double> x;
		public IntDoubleEntryImpl(Map.Entry<Integer, Double> x) { this.x = x; }
        @Override
        public int index() { return x.getKey(); }
        @Override
        public double get() { return x.getValue(); }
	}
	
	public static class IntDoubleEntryIter implements Iterator<IntDoubleEntry> {
        private Iterator<Vector.IntDouble> iter;
        public IntDoubleEntryIter(Iterator<Vector.IntDouble> iter) { this.iter = iter; }
        @Override
        public boolean hasNext() { return iter.hasNext(); }
        @Override
        public IntDoubleEntry next() { return new IntDoubleEntryImpl(iter.next()); }
        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }

    @Override
    public Iterator<IntDoubleEntry> iterator() {
       return new IntDoubleEntryIter(super.indicesAndValues());
    }

}
