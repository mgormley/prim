package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.DoubleArrays;
import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.list.DoubleArrayList;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.map.LongDoubleEntry;
import edu.jhu.prim.map.LongDoubleSortedMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnLongDoubleToVoid;
import edu.jhu.prim.util.Lambda.LambdaBinOpDouble;
import edu.jhu.prim.util.SafeCast;

/**
 * Infinite length sparse vector.
 * 
 * @author mgormley
 *
 */
public class LongDoubleSortedVector extends LongDoubleSortedMap implements LongDoubleVector {
    
    private static final long serialVersionUID = 1L;
    private static final double ZERO = (double) 0;
    
    boolean norm2Cached = false;
    double norm2Value;
    
    public LongDoubleSortedVector() {
        super();
    }

    public LongDoubleSortedVector(int initialSize) {
        super(initialSize);
    }

    public LongDoubleSortedVector(long[] index, double[] data) {
    	super(index, data);
	}

	public LongDoubleSortedVector(double[] denseRow) {
		this(LongArrays.range(denseRow.length), denseRow);
	}
	
	/** Copy constructor. */
    public LongDoubleSortedVector(LongDoubleSortedVector vector) {
        super(vector);
    }

    /** Copy constructor. */
	public LongDoubleSortedVector(LongDoubleHashVector vector) {
	    super(vector);
    }
	
	/** Copy constructor. */
    public LongDoubleSortedVector(LongDoubleDenseVector vector) {
        this(vector.toNativeArray());
    }

    /** Copy constructor. */
    public LongDoubleSortedVector(LongDoubleVector other) {
        // TODO: Exploit the number of non-zero entries in other.
        this();
        final LongDoubleSortedVector thisVec = this; 
        other.iterate(new FnLongDoubleToVoid() {
            @Override
            public void call(long idx, double val) {
                thisVec.set(idx, val);
            }
        });
    }
    
    /** Gets a deep copy of this vector. */
    @Override
    public LongDoubleVector copy() {
        return new LongDoubleSortedVector(this);
    }
    
    // TODO: This could be done with a single binary search instead of two.
    public void add(long idx, double val) {
    	double curVal = getWithDefault(idx, ZERO);
    	put(idx, curVal + val);
    }
    
    public double set(long idx, double val) {
    	return put(idx, val);
    }
    
    @Override
	public double get(long idx) {
		return getWithDefault(idx, ZERO);
	}
    
    public void scale(double multiplier) {
    	for (int i=0; i<used; i++) {
    		values[i] *= multiplier;
    	}
    }

    /** Computes the dot product of this vector with the given vector. */
    public double dot(double[] other) {
        double dot = 0;
        for (int c = 0; c < used && indices[c] < other.length; c++) {
            if (indices[c] > Integer.MAX_VALUE) {
                break;
            }
            dot += values[c] * other[SafeCast.safeLongToInt(indices[c])];
        }
        return dot;
    }

    /** Computes the dot product of this vector with the column of the given matrix. */
    public double dot(double[][] matrix, int col) {
        double ret = 0;
        for (int c = 0; c < used && indices[c] < matrix.length; c++) {
            if (indices[c] > Integer.MAX_VALUE) {
                break;
            }
            ret += values[c] * matrix[SafeCast.safeLongToInt(indices[c])][col];
        }
        return ret;
    }
    
    /** Computes the dot product of this vector with the other vector. */   
    public double dot(LongDoubleVector y) {
        if (y instanceof LongDoubleSortedVector) {
            LongDoubleSortedVector other = ((LongDoubleSortedVector) y);
            double dot = 0;
            int oc = 0;
            for (int c = 0; c < used; c++) {
                while (oc < other.used) {
                    if (other.indices[oc] < indices[c]) {
                        oc++;
                    } else if (indices[c] == other.indices[oc]) {
                        dot += values[c] * other.values[oc];
                        break;
                    } else {
                        break;
                    }
                }
            }
            return dot;
        } else {
            double dot = 0;
            for (int c = 0; c < used; c++) {
                dot += this.values[c] * y.get(indices[c]);
            }
            return dot;
        }
    }    

    /**
     * @return A new vector without zeros OR the same vector if it has none.
     */
    public static LongDoubleSortedVector getWithNoZeroValues(LongDoubleSortedVector row, double zeroThreshold) {
        long[] origIndex = row.getIndices();
        double[] origData = row.getValues();
        
        // Count and keep track of nonzeros.
        int numNonZeros = 0;
        boolean[] isNonZero = new boolean[row.getUsed()];
        for (int i = 0; i < row.getUsed(); i++) {
            if (!Primitives.isZero(origData[i], zeroThreshold)) {
                isNonZero[i] = true;
                numNonZeros++;
            } else {
                isNonZero[i] = false;
            }
        }
        int numZeros = row.getUsed() - numNonZeros;
        
        if (numZeros > 0) {
            // Create the new vector without the zeros.
            long[] newIndex = new long[numNonZeros];
            double[] newData = new double[numNonZeros];

            int newIdx = 0;
            for (int i = 0; i < row.getUsed(); i++) {
                if (isNonZero[i]) {
                    newIndex[newIdx] = origIndex[i];
                    newData[newIdx] = origData[i];
                    newIdx++;
                }
            }
            return new LongDoubleSortedVector(newIndex, newData);
        } else {
            return row;
        }
    }
    

    /**
     * TODO: Make a SortedLongDoubleVectorWithExplicitZeros class and move this method there.
     * 
     * Here we override the zero method so that it doesn't set the number of
     * used values to 0. This ensures that we keep explicit zeros in.
     */
    public LongDoubleSortedVector zero() {
        java.util.Arrays.fill(values, 0);
        //used = 0;
        return this;
    }

    /** Sets all values in this vector to those in the other vector. */
    public void set(LongDoubleSortedVector other) {
        this.used = other.used;
        this.indices = LongArrays.copyOf(other.indices);
        this.values = DoubleArrays.copyOf(other.values);
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(LongDoubleVector other) {
        apply(other, new Lambda.DoubleAdd(), false);
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(LongDoubleVector other) {
        apply(other, new Lambda.DoubleSubtract(), false);
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(LongDoubleVector other) {
        apply(other, new Lambda.DoubleProd(), true);
    }
    
    /** Gets the entrywise sum of the two vectors. */
    public LongDoubleSortedVector getSum(LongDoubleVector other) {
        LongDoubleSortedVector sum = new LongDoubleSortedVector(this);
        sum.add(other);
        return sum;
    }
    
    /** Gets the entrywise difference of the two vectors. */
    public LongDoubleSortedVector getDiff(LongDoubleVector other) {
        LongDoubleSortedVector diff = new LongDoubleSortedVector(this);
        diff.subtract(other);
        return diff;
    }
    
    /** Gets the entrywise product (i.e. Hadamard product) of the two vectors. */
    public LongDoubleSortedVector getProd(LongDoubleVector other) {
        LongDoubleSortedVector prod = new LongDoubleSortedVector(this);
        prod.product(other);
        return prod;
    }

    @Override
    public double getProd() {
        throw new RuntimeException("not supported");
    }    

    /**
     * Applies the function to every pair of entries in this vector and an
     * other. If the call is skipping zeros, then the function is only applied
     * to those entries which are explicit in both vectors. Otherwise, it is
     * applied to any entry which is explicit in either vector.
     * 
     * @param other The other vector.
     * @param lambda The function to apply.
     * @param skipZeros Whether to skip entries which are explicit in one of the
     *            vectors. Note that most such entries will be non-zero, but the
     *            implementation may choose to allow explicit zero entries. This
     *            is useful for operations such as entrywise product.
     */
    public void apply(LongDoubleVector other, LambdaBinOpDouble lambda, boolean skipZeros) {
        if (other instanceof LongDoubleSortedVector) {
            applyToSorted((LongDoubleSortedVector)other, lambda, false);
        } else if (other instanceof LongDoubleHashVector) {
            other = new LongDoubleSortedVector((LongDoubleHashVector) other);
            applyToSorted((LongDoubleSortedVector) other, lambda, false);
        } else if (other instanceof LongDoubleDenseVector) {
            other = new LongDoubleSortedVector((LongDoubleDenseVector) other);
            applyToSorted((LongDoubleSortedVector) other, lambda, false);
        } else {
            // TODO: we could just add a generic constructor. 
            throw new IllegalStateException("Unhandled vector type: " + other.getClass());
        }
    }
    
    private void applyToSorted(final LongDoubleSortedVector other, final LambdaBinOpDouble lambda, final boolean skipZeros) {
        // It appears to be faster to just make a good (quick guess) for the
        // final length of the new array, rather than to make an educated
        // guess by counting.
        int numNewIndices = Math.max(this.used, other.used);
        LongArrayList newIndices = new LongArrayList(numNewIndices);
        DoubleArrayList newValues = new DoubleArrayList(numNewIndices);
        int i=0; 
        int j=0;
        while(i < this.used && j < other.used) {
            long e1 = this.indices[i];
            double v1 = this.values[i];
            long e2 = other.indices[j];
            double v2 = other.values[j];
            
            long diff = e1 - e2;
            if (diff == 0) {
                // Elements are equal. Add both of them.
                newIndices.add(e1);
                newValues.add(lambda.call(v1, v2));
                i++;
                j++;
            } else if (diff < 0) {
                if (!skipZeros) {
                    // e1 is less than e2, so only add e1 this round.
                    newIndices.add(e1);
                    newValues.add(lambda.call(v1, ZERO));
                }
                i++;
            } else {
                if (!skipZeros) {
                    // e2 is less than e1, so only add e2 this round.
                    newIndices.add(e2);
                    newValues.add(lambda.call(ZERO, v2));
                }
                j++;
            }
        }

        assert (!(i < this.used && j < other.used));

        if (!skipZeros) {
            // If there is a list that we didn't get all the way through, add all
            // the remaining elements. There will never be more than one such list. 
            for (; i < this.used; i++) {
                long e1 = this.indices[i];
                double v1 = this.values[i];
                newIndices.add(e1);
                newValues.add(lambda.call(v1, ZERO));
            }
            for (; j < other.used; j++) {
                long e2 = other.indices[j];
                double v2 = other.values[j];
                newIndices.add(e2);
                newValues.add(lambda.call(ZERO, v2));
            }
        }
        
        this.used = newIndices.size();
        this.indices = newIndices.getInternalElements();
        this.values = newValues.getInternalElements();
        if (indices.length != values.length) {
            throw new IllegalStateException("The primitive ArrayLists grew at different lengths. This should never occur.");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < used; i++) {
            sb.append(indices[i]);
            sb.append(":");
            sb.append(values[i]);
            if (i + 1 < used) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Returns true if the input vector is equal to this one.
     */
    public boolean eq(LongDoubleSortedVector other, double delta) {
        // This is slow, but correct.
        LongDoubleSortedVector v1 = LongDoubleSortedVector.getWithNoZeroValues(this, delta);
        LongDoubleSortedVector v2 = LongDoubleSortedVector.getWithNoZeroValues(other, delta);
                
        if (v2.size() != v1.size()) {
            return false;
        }

        for (LongDoubleEntry ve : v1) {
            if (!Primitives.equals(ve.get(), v2.get(ve.index()), delta)) {
                return false;
            }
        }
        for (LongDoubleEntry ve : v2) {
            if (!Primitives.equals(ve.get(), v1.get(ve.index()), delta)) {
                return false;
            }
        }
        return true;
    }
    
    public long getNumImplicitEntries() {
        return Math.max(0, LongArrays.max(indices) + 1);
    }

    /** Gets a NEW array containing all the elements in this vector. */
    public double[] toNativeArray() {
        final double[] arr = new double[SafeCast.safeLongToInt(getNumImplicitEntries())];
        iterate(new FnLongDoubleToVoid() {
            @Override
            public void call(long idx, double val) {
                arr[SafeCast.safeLongToInt(idx)] = val;
            }
        });
        return arr;
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("not implemented");
    }

}
