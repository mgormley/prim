package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.map.IntIntEntry;
import edu.jhu.prim.map.IntIntSortedMap;
import edu.jhu.prim.sort.IntIntSort;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.Lambda.FnIntIntToInt;
import edu.jhu.prim.util.Lambda.LambdaBinOpInt;
import edu.jhu.prim.util.SafeCast;

/**
 * Infinite length sparse vector.
 * 
 * @author mgormley
 *
 */
public class IntIntSortedVector extends IntIntSortedMap implements IntIntVector {
    
    private static final long serialVersionUID = 1L;
    private static final int ZERO = (int) 0;
    
    boolean norm2Cached = false;
    int norm2Value;
    
    public IntIntSortedVector() {
        super();
    }

    public IntIntSortedVector(int initialSize) {
        super(initialSize);
    }

    public IntIntSortedVector(int[] index, int[] data) {
    	super(index, data);
	}

	public IntIntSortedVector(int[] denseRow) {
		this(IntIntSort.getIntIndexArray(denseRow.length), denseRow);
	}
	
	/** Copy constructor. */
    public IntIntSortedVector(IntIntSortedVector vector) {
        super(vector);
    }

    /** Copy constructor. */
	public IntIntSortedVector(IntIntHashVector vector) {
	    super(vector);
    }
	
	/** Copy constructor. */
    public IntIntSortedVector(IntIntDenseVector vector) {
        this(vector.toNativeArray());
    }

    /** Copy constructor. */
    public IntIntSortedVector(IntIntVector other) {
        // TODO: Exploit the number of non-zero entries in other.
        this();
        final IntIntSortedVector thisVec = this; 
        other.apply(new FnIntIntToInt() {            
            @Override
            public int call(int idx, int val) {
                thisVec.set(idx, val);
                return val;
            }
        });
    }
    
    // TODO: This could be done with a single binary search instead of two.
    public void add(int idx, int val) {
    	int curVal = getWithDefault(idx, ZERO);
    	put(idx, curVal + val);
    }
    
    public void set(int idx, int val) {
    	put(idx, val);
    }
    
    @Override
	public int get(int idx) {
		return getWithDefault(idx, ZERO);
	}
    
    public void scale(int multiplier) {
    	for (int i=0; i<used; i++) {
    		values[i] *= multiplier;
    	}
    }

    /** Computes the dot product of this vector with the given vector. */
    public int dot(int[] other) {
        int dot = 0;
        for (int c = 0; c < used && indices[c] < other.length; c++) {
            if (indices[c] > Integer.MAX_VALUE) {
                break;
            }
            dot += values[c] * other[indices[c]];
        }
        return dot;
    }

    /** Computes the dot product of this vector with the column of the given matrix. */
    public int dot(int[][] matrix, int col) {
        int ret = 0;
        for (int c = 0; c < used && indices[c] < matrix.length; c++) {
            if (indices[c] > Integer.MAX_VALUE) {
                break;
            }
            ret += values[c] * matrix[indices[c]][col];
        }
        return ret;
    }
    
    /** Computes the dot product of this vector with the other vector. */   
    public int dot(IntIntVector y) {
        if (y instanceof IntIntSortedVector) {
            IntIntSortedVector other = ((IntIntSortedVector) y);
            int dot = 0;
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
            int dot = 0;
            for (int c = 0; c < used; c++) {
                dot += this.values[c] * y.get(indices[c]);
            }
            return dot;
        }
    }    

    /**
     * @return A new vector without zeros OR the same vector if it has none.
     */
    public static IntIntSortedVector getWithNoZeroValues(IntIntSortedVector row) {
        int[] origIndex = row.getIndices();
        int[] origData = row.getValues();
        
        // Count and keep track of nonzeros.
        int numNonZeros = 0;
        boolean[] isNonZero = new boolean[row.getUsed()];
        for (int i = 0; i < row.getUsed(); i++) {
            if (!Primitives.isZero(origData[i])) {
                isNonZero[i] = true;
                numNonZeros++;
            } else {
                isNonZero[i] = false;
            }
        }
        int numZeros = row.getUsed() - numNonZeros;
        
        if (numZeros > 0) {
            // Create the new vector without the zeros.
            int[] newIndex = new int[numNonZeros];
            int[] newData = new int[numNonZeros];

            int newIdx = 0;
            for (int i = 0; i < row.getUsed(); i++) {
                if (isNonZero[i]) {
                    newIndex[newIdx] = origIndex[i];
                    newData[newIdx] = origData[i];
                    newIdx++;
                }
            }
            return new IntIntSortedVector(newIndex, newData);
        } else {
            return row;
        }
    }
    

    /**
     * TODO: Make a SortedIntIntVectorWithExplicitZeros class and move this method there.
     * 
     * Here we override the zero method so that it doesn't set the number of
     * used values to 0. This ensures that we keep explicit zeros in.
     */
    public IntIntSortedVector zero() {
        java.util.Arrays.fill(values, 0);
        //used = 0;
        return this;
    }

    /** Sets all values in this vector to those in the other vector. */
    public void set(IntIntSortedVector other) {
        this.used = other.used;
        this.indices = IntArrays.copyOf(other.indices);
        this.values = IntArrays.copyOf(other.values);
    }

    /** Updates this vector to be the entrywise sum of this vector with the other. */
    public void add(IntIntVector other) {
        apply(other, new Lambda.IntAdd(), false);
    }
    
    /** Updates this vector to be the entrywise difference of this vector with the other. */
    public void subtract(IntIntVector other) {
        apply(other, new Lambda.IntSubtract(), false);
    }
    
    /** Updates this vector to be the entrywise product (i.e. Hadamard product) of this vector with the other. */
    public void product(IntIntVector other) {
        apply(other, new Lambda.IntProd(), true);
    }
    
    /** Gets the entrywise sum of the two vectors. */
    public IntIntSortedVector getSum(IntIntVector other) {
        IntIntSortedVector sum = new IntIntSortedVector(this);
        sum.add(other);
        return sum;
    }
    
    /** Gets the entrywise difference of the two vectors. */
    public IntIntSortedVector getDiff(IntIntVector other) {
        IntIntSortedVector diff = new IntIntSortedVector(this);
        diff.subtract(other);
        return diff;
    }
    
    /** Gets the entrywise product (i.e. Hadamard product) of the two vectors. */
    public IntIntSortedVector getProd(IntIntVector other) {
        IntIntSortedVector prod = new IntIntSortedVector(this);
        prod.product(other);
        return prod;
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
    public void apply(IntIntVector other, LambdaBinOpInt lambda, boolean skipZeros) {
        if (other instanceof IntIntSortedVector) {
            applyToSorted((IntIntSortedVector)other, lambda, false);
        } else if (other instanceof IntIntHashVector) {
            other = new IntIntSortedVector((IntIntHashVector) other);
            applyToSorted((IntIntSortedVector) other, lambda, false);
        } else if (other instanceof IntIntDenseVector) {
            other = new IntIntSortedVector((IntIntDenseVector) other);
            applyToSorted((IntIntSortedVector) other, lambda, false);
        } else {
            // TODO: we could just add a generic constructor. 
            throw new IllegalStateException("Unhandled vector type: " + other.getClass());
        }
    }
    
    private void applyToSorted(final IntIntSortedVector other, final LambdaBinOpInt lambda, final boolean skipZeros) {
        // It appears to be faster to just make a good (quick guess) for the
        // final length of the new array, rather than to make an educated
        // guess by counting.
        int numNewIndices = Math.max(this.used, other.used);
        IntArrayList newIndices = new IntArrayList(numNewIndices);
        IntArrayList newValues = new IntArrayList(numNewIndices);
        int i=0; 
        int j=0;
        while(i < this.used && j < other.used) {
            int e1 = this.indices[i];
            int v1 = this.values[i];
            int e2 = other.indices[j];
            int v2 = other.values[j];
            
            int diff = e1 - e2;
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
                int e1 = this.indices[i];
                int v1 = this.values[i];
                newIndices.add(e1);
                newValues.add(lambda.call(v1, ZERO));
            }
            for (; j < other.used; j++) {
                int e2 = other.indices[j];
                int v2 = other.values[j];
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
    public boolean eq(IntIntSortedVector other) {
        // This is slow, but correct.
        IntIntSortedVector v1 = IntIntSortedVector.getWithNoZeroValues(this);
        IntIntSortedVector v2 = IntIntSortedVector.getWithNoZeroValues(other);
                
        if (v2.size() != v1.size()) {
            return false;
        }

        for (IntIntEntry ve : v1) {
            if (!Primitives.equals(ve.get(), v2.get(ve.index()))) {
                return false;
            }
        }
        for (IntIntEntry ve : v2) {
            if (!Primitives.equals(ve.get(), v1.get(ve.index()))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("not implemented");
    }

}
