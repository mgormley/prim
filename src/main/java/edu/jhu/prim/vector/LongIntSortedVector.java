package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.map.LongIntEntry;
import edu.jhu.prim.map.LongIntSortedMap;
import edu.jhu.prim.util.Lambda;
import edu.jhu.prim.util.SafeCast;
import edu.jhu.prim.util.Utilities;
import edu.jhu.prim.util.Lambda.LambdaBinOpInt;

/**
 * Infinite length sparse vector.
 * 
 * @author mgormley
 *
 */
public class LongIntSortedVector extends LongIntSortedMap implements LongIntVector {

    private static final int ZERO = (int) 0;
    
    boolean norm2Cached = false;
    int norm2Value;
    
    public LongIntSortedVector() {
        super();
    }

    public LongIntSortedVector(int initialSize) {
        super(initialSize);
    }

    public LongIntSortedVector(long[] index, int[] data) {
    	super(index, data);
	}
    
    public LongIntSortedVector(LongIntSortedVector vector) {
    	super(vector);
    }

	public LongIntSortedVector(int[] denseRow) {
		this(Utilities.getLongIndexArray(denseRow.length), denseRow);
	}

	// TODO: This could be done with a single binary search instead of two.
    public void add(long idx, int val) {
    	int curVal = getWithDefault(idx, ZERO);
    	put(idx, curVal + val);
    }
    
    public void set(long idx, int val) {
    	put(idx, val);
    }
    
    @Override
	public int get(long idx) {
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
            dot += values[c] * other[SafeCast.safeLongToInt(indices[c])];
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
            ret += values[c] * matrix[SafeCast.safeLongToInt(indices[c])][col];
        }
        return ret;
    }
    
    /** Computes the dot product of this vector with the other vector. */   
    public int dot(LongIntVector y) {
        if (y instanceof LongIntSortedVector) {
            LongIntSortedVector other = ((LongIntSortedVector) y);
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
    public static LongIntSortedVector getWithNoZeroValues(LongIntSortedVector row) {
        long[] origIndex = row.getIndices();
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
            long[] newIndex = new long[numNonZeros];
            int[] newData = new int[numNonZeros];

            int newIdx = 0;
            for (int i = 0; i < row.getUsed(); i++) {
                if (isNonZero[i]) {
                    newIndex[newIdx] = origIndex[i];
                    newData[newIdx] = origData[i];
                    newIdx++;
                }
            }
            return new LongIntSortedVector(newIndex, newData);
        } else {
            return row;
        }
    }
    

    /**
     * TODO: Make a SortedLongIntVectorWithExplicitZeros class and move this method there.
     * 
     * Here we override the zero method so that it doesn't set the number of
     * used values to 0. This ensures that we keep explicit zeros in.
     */
    public LongIntSortedVector zero() {
        java.util.Arrays.fill(values, 0);
        //used = 0;
        return this;
    }

    /** Sets all values in this vector to those in the other vector. */
    public void set(LongIntSortedVector other) {
        this.used = other.used;
        this.indices = Utilities.copyOf(other.indices);
        this.values = Utilities.copyOf(other.values);
    }
    
    /**
     * Computes the Hadamard product (or entry-wise product) of this vector with
     * another.
     */
    // TODO: this could just be a binaryOp call.
    public LongIntSortedVector hadamardProd(LongIntSortedVector other) {
    	LongIntSortedVector ip = new LongIntSortedVector();
        int oc = 0;
        for (int c = 0; c < used; c++) {
            while (oc < other.used) {
                if (other.indices[oc] < indices[c]) {
                    oc++;
                } else if (indices[c] == other.indices[oc]) {
                    ip.set(indices[c], values[c] * other.values[oc]);
                    break;
                } else {
                    break;
                }
            }
        }
        return ip;
    }

    public void add(LongIntSortedVector other) {
        binaryOp(other, new Lambda.IntAdd());
    }
    
    public void subtract(LongIntSortedVector other) {
        binaryOp(other, new Lambda.IntSubtract());
    }
    
    public void binaryOp(LongIntSortedVector other, LambdaBinOpInt lambda) {
        LongArrayList newIndices = new LongArrayList(Math.max(this.indices.length, other.indices.length));
        IntArrayList newValues = new IntArrayList(Math.max(this.indices.length, other.indices.length));
        int i=0; 
        int j=0;
        while(i < this.used && j < other.used) {
            long e1 = this.indices[i];
            int v1 = this.values[i];
            long e2 = other.indices[j];
            int v2 = other.values[j];
            
            long diff = e1 - e2;
            if (diff == 0) {
                // Elements are equal. Add both of them.
                newIndices.add(e1);
                newValues.add(lambda.call(v1, v2));
                i++;
                j++;
            } else if (diff < 0) {
                // e1 is less than e2, so only add e1 this round.
                newIndices.add(e1);
                newValues.add(lambda.call(v1, ZERO));
                i++;
            } else {
                // e2 is less than e1, so only add e2 this round.
                newIndices.add(e2);
                newValues.add(lambda.call(ZERO, v2));
                j++;
            }
        }

        // If there is a list that we didn't get all the way through, add all
        // the remaining elements. There will never be more than one such list. 
        assert (!(i < this.used && j < other.used));
        for (; i < this.used; i++) {
            long e1 = this.indices[i];
            int v1 = this.values[i];
            newIndices.add(e1);
            newValues.add(lambda.call(v1, ZERO));
        }
        for (; j < other.used; j++) {
            long e2 = other.indices[j];
            int v2 = other.values[j];
            newIndices.add(e2);
            newValues.add(lambda.call(ZERO, v2));
        }
        
        this.used = newIndices.size();
        this.indices = newIndices.toNativeArray();
        this.values = newValues.toNativeArray();
    }
    
    /**
     * Counts the number of unique indices in two arrays.
     * @param indices1 Sorted array of indices.
     * @param indices2 Sorted array of indices.
     */
    public static int countUnique(long[] indices1, long[] indices2) {
        int numUniqueIndices = 0;
        int i = 0;
        int j = 0;
        while (i < indices1.length && j < indices2.length) {
            if (indices1[i] < indices2[j]) {
                numUniqueIndices++;
                i++;
            } else if (indices2[j] < indices1[i]) {
                numUniqueIndices++;
                j++;
            } else {
                // Equal indices.
                i++;
                j++;
            }
        }
        for (; i < indices1.length; i++) {
            numUniqueIndices++;
        }
        for (; j < indices2.length; j++) {
            numUniqueIndices++;
        }
        return numUniqueIndices;
    }
    
    public LongIntSortedVector getElementwiseSum(LongIntSortedVector other) {
        LongIntSortedVector sum = new LongIntSortedVector(this);
        sum.add(other);
        return sum;
    }
    
    public LongIntSortedVector getElementwiseDiff(LongIntSortedVector other) {
        LongIntSortedVector sum = new LongIntSortedVector(this);
        sum.subtract(other);
        return sum;
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
    public boolean eq(LongIntSortedVector other) {
        // This is slow, but correct.
        LongIntSortedVector v1 = LongIntSortedVector.getWithNoZeroValues(this);
        LongIntSortedVector v2 = LongIntSortedVector.getWithNoZeroValues(other);
                
        if (v2.size() != v1.size()) {
            return false;
        }

        for (LongIntEntry ve : v1) {
            if (!Utilities.equals(ve.get(), v2.get(ve.index()))) {
                return false;
            }
        }
        for (LongIntEntry ve : v2) {
            if (!Utilities.equals(ve.get(), v1.get(ve.index()))) {
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
