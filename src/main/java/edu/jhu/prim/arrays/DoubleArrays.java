package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.tuple.IntTuple;
import edu.jhu.prim.util.math.FastMath;
import edu.jhu.prim.util.random.Prng;


/**
 * Utility methods and math for double arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class DoubleArrays {

    private DoubleArrays() {
        // private constructor
    }

    public static double[] copyOf(double[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static double[] copyOf(double[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static double[][] copyOf(double[][] array) {
        double[][] newArray = new double[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static double[][][] copyOf(double[][][] array) {
        double[][][] clone = new double[array.length][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }
    
    public static double[][][][] copyOf(double[][][][] array) {
        double[][][][] clone = new double[array.length][][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static void copy(double[] array, double[] clone) {
        assert (array.length == clone.length);
        System.arraycopy(array, 0, clone, 0, array.length);
    }

    public static void copy(double[][] array, double[][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < clone.length; i++) {
            copy(array[i], clone[i]);
        }
    }

    public static void copy(double[][][] array, double[][][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < clone.length; i++) {
            copy(array[i], clone[i]);
        }
    }

    public static void copy(double[][][][] array, double[][][][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < clone.length; i++) {
            copy(array[i], clone[i]);
        }
    }

    /**
     * Faster version of Arrays.fill(). That standard version does NOT use
     * memset, and only iterates over the array filling each value. This method
     * works out to be much faster and seems to be using memset as appropriate.
     */
    // TODO: Iterating is still the fastest way to fill an array.
    public static void fill(final double[] array, final double value) {
        //        final int n = array.length;
        //        if (n > 0) {
        //            array[0] = value;
        //        }
        //        for (int i = 1; i < n; i += i) {
        //           System.arraycopy(array, 0, array, i, ((n - i) < i) ? (n - i) : i);
        //        }
        for (int i=0; i<array.length; i++) {
            array[i] = value;
        }
    }

    public static void fill(double[][] array, double value) {
        for (int i=0; i<array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(double[][][] array, double value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(double[][][][] array, double value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }
    public static String toString(double[] array, String formatString) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i=0; i<array.length; i++) {
            sb.append(String.format(formatString, array[i]));
            if (i < array.length -1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static String deepToString(double[][] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (double[] arr : array) {
            sb.append("[");
            for (double a : arr) {
                sb.append(String.format("%10.3g, ", a));
            }
            sb.append("], ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static double sum(double[] vector) {
        double sum = 0;
        for(int i=0; i<vector.length; i++) {
            sum += vector[i];
        }
        return sum;
    }

    public static double prod(double[] vector) {
        double prod = 1;
        for(int i=0; i<vector.length; i++) {
            prod *= vector[i];
        }
        return prod;
    }

    public static void assertNoZeroes(double[] draw, double[] logDraw) {
        assertNoZeros(draw);
        assertNoNegInfs(logDraw);
    }

    public static void assertNoNegInfs(double[] logDraw) {
        for (int i=0; i<logDraw.length; i++) {
            assert(!Double.isNaN(logDraw[i]));
            assert(!Double.isInfinite(logDraw[i]));
        }
    }

    public static void assertNoZeros(double[] draw) {
        for (int i=0; i<draw.length; i++) {
            assert(!Double.isNaN(draw[i]));
            assert(draw[i] != 0);
        }
    }

    public static void assertSameSize(double[][] newLogPhi, double[][] logPhi) {
        assert(newLogPhi.length == logPhi.length);
        for (int k=0; k<logPhi.length; k++) {
            assert(newLogPhi[k].length == logPhi[k].length); 
        }
    }

    public static double[] getExp(double[] logPhi) {
        double[] phi = new double[logPhi.length];
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.exp(logPhi[i]);
        }
        return phi;
    }

    public static void exp(double[] phi) {
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.exp(phi[i]);
        }
    }

    public static void log(double[] phi) {
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.log(phi[i]);
        }
    }

    public static void log(double[][] phi) {
        for (int i=0; i<phi.length; i++) {
            log(phi[i]);
        }
    }

    public static void logForIlp(double[] phi) {
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.logForIlp(phi[i]);
        }
    }

    public static double[] getLog(double[] phi) {
        double[] logPhi = new double[phi.length];
        updateLogPhi(phi, logPhi);
        return logPhi;
    }

    public static double[] getLogForIlp(double[] phi) {
        double[] logPhi = new double[phi.length];
        for (int t=0; t<logPhi.length; t++) {
            logPhi[t] = FastMath.logForIlp(phi[t]);
        }
        return logPhi;
    }

    public static void updateLogPhi(double[] phi, double[] logPhi) {
    	for (int t=0; t<logPhi.length; t++) {
    		logPhi[t] = FastMath.log(phi[t]);
    	}
    }

    /**
     * TODO: This should live in a matrix class
     */
    public static double sum(double[][] matrix) {
        double sum = 0; 
        for (int i=0; i<matrix.length; i++) {
            sum += sum(matrix[i]);
        }
        return sum;
    }

    public static double max(double[] array) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i=0; i<array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static double min(double[] array) {
        double min = Double.POSITIVE_INFINITY;
        for (int i=0; i<array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static int argmax(double[] array) {
        double max = Double.NEGATIVE_INFINITY;
        int argmax = -1;
        for (int i=0; i<array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                argmax = i;
            }
        }
        return argmax;
    }

    public static int argmin(double[] array) {
        double min = Double.POSITIVE_INFINITY;
        int argmin = -1;
        for (int i=0; i<array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                argmin = i;
            }
        }
        return argmin;
    }

    /**
     * Gets the argmax breaking ties randomly.
     */
    public static IntTuple getArgmax(double[][] array) {
        return getArgmax(array, Primitives.DEFAULT_DOUBLE_DELTA);
    }
    
    /**
     * Gets the argmax breaking ties randomly.
     */
    public static IntTuple getArgmax(double[][] array, double delta) {
        double maxValue = Double.NEGATIVE_INFINITY;
        int maxX = -1;
        int maxY = -1;
        double numMax = 1;
        for (int x=0; x<array.length; x++) {
            for (int y=0; y<array[x].length; y++) {
                double diff = Primitives.compare(array[x][y], maxValue, delta);
                if (diff == 0 && Prng.nextDouble() < 1.0 / numMax) {
                    maxValue = array[x][y];
                    maxX = x;
                    maxY = y;
                    numMax++;
                } else if (diff > 0) {
                    maxValue = array[x][y];
                    maxX = x;
                    maxY = y;
                    numMax = 1;
                }
            }
        }
        return new IntTuple(maxX, maxY);
    }


    public static double dotProduct(double[] array1, double[] array2) {
        if (array1.length != array2.length) {
            throw new IllegalStateException("array1.length != array2.length");
        }
        double dotProduct = 0;
        for (int i=0; i<array1.length; i++) {
            dotProduct += array1[i] * array2[i];
        }
        return dotProduct;
    }

    public static void scale(double[] array, double alpha) {
        for (int i=0; i<array.length; i++) {
            array[i] *= alpha;
        }
    }

    public static void scale(double[][] array, double alpha) {
        for (int i=0; i<array.length; i++) {
            scale(array[i], alpha);
        }
    }

    public static double mean(double[] array) {
        return sum(array) / array.length;
    }

    public static double variance(double[] array) {
        double mean = mean(array);
        double sumOfSquares = 0;
        for (int i=0; i<array.length; i++) {
            sumOfSquares += (array[i] - mean)*(array[i] - mean);
        }
        return sumOfSquares / (array.length - 1);
    }

    public static double stdDev(double[] array) {
        return FastMath.sqrt(variance(array));
    }

    public static double logSum(double[] logProps) {
        double logPropSum = Double.NEGATIVE_INFINITY;
        for (int d = 0; d < logProps.length; d++) {
            logPropSum = FastMath.logAdd(logPropSum, logProps[d]);
        }
        return logPropSum;
    }

    public static void add(double[] params, double lambda) {
        for (int i=0; i<params.length; i++) {
            params[i] += lambda;
        }
    }
    
    /** Each element of the second array is added to each element of the first. */
    public static void add(double[] array1, double[] array2) {
        assert (array1.length == array2.length);
        for (int i=0; i<array1.length; i++) {
            array1[i] += array2[i];
        }
    }
    
    /** Each element of the second array is subtracted from each element of the first. */
    public static void subtract(double[] array1, double[] array2) {
        assert (array1.length == array2.length);
        for (int i=0; i<array1.length; i++) {
            array1[i] -= array2[i];
        }
    }
    
    /** Each element of the second array is multiplied with each element of the first. */
    public static void multiply(double[] array1, double[] array2) {
        assert (array1.length == array2.length);
        for (int i=0; i<array1.length; i++) {
            array1[i] *= array2[i];
        }
    }
    
    /** Each element of the first array is divided by each element of the second. */
    public static void divide(double[] array1, double[] array2) {
        assert (array1.length == array2.length);
        for (int i=0; i<array1.length; i++) {
            array1[i] /= array2[i];
        }
    }
    
    public static double l1norm(double[] array) {
        double l1norm = 0;
        for (int i=0; i<array.length; i++) {
            l1norm += Math.abs(array[i]);
        }
        return l1norm;
    }
    
    public static double l2norm(double[] array) {
        double l2norm = 0;
        for (int i=0; i<array.length; i++) {
            l2norm += array[i] * array[i];
        }
        return (double) Math.sqrt(l2norm);
    }
    
    public static double infinityNorm(double[] gradient) {
        double maxAbs = 0;
        for (int i=0; i<gradient.length; i++) {
            double tempVal = Math.abs(gradient[i]);
            if (tempVal > maxAbs) {
                maxAbs = tempVal;
            }
        }
        return maxAbs;
    }

    public static double infinityNorm(double[][] gradient) {
        double maxIN = 0;
        for (int i=0; i<gradient.length; i++) {
            double tempVal = infinityNorm(gradient[i]);
            if (tempVal > maxIN) {
                maxIN = tempVal;
            }
        }
        return maxIN;
    }

    public static boolean isInRange(double[] p, double min, double max) {
        for (int i=0; i<p.length; i++) {
            if (p[i] < min) {
                return false;
            }
            if (p[i] > max) {
                return false;
            }
        }
        return true;
    }

    /** 
     * Reorder array in place. 
     * 
     * Letting A denote the original array and B the reordered version, 
     * we will have B[i] = A[order[i]].
     * 
     * @param array The array to reorder.
     * @param order The order to apply.
     */
    public static void reorder(double[] array, int[] order) {
        double[] original = copyOf(array);
        for (int i=0; i<array.length; i++) {
            array[i] = original[order[i]];
        }
    }
    
    /** Checks in O(n) by linear search if the array contains the value. */
    public static boolean contains(double[] array, double value, double delta) {
        for (int i=0; i<array.length; i++) {
            if (Primitives.equals(array[i], value, delta)) {
                return true;
            }
        }
        return false;
    }
    
    /** Checks in O(n) by linear search if the array contains the value. */
    public static boolean contains(double[][] array, double value, double delta) {
        for (int i=0; i<array.length; i++) {
            if (contains(array[i], value, delta)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Fisher-Yates shuffle randomly reorders the elements in array. This is
     * O(n) in the length of the array.
     */
    public static void shuffle(double[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = Prng.nextInt(i + 1);
            // Swap array[i] and array[j]
            double tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    /** Gets the first index of a given value in an array or -1 if not present. */
    public static int indexOf(double[] array, double val) {
        for (int i=0; i<array.length; i++) {
            if (array[i] == val) {
                return i;
            }
        }
        return -1;
    }
    
    /** Gets the last index of a given value in an array or -1 if not present. */
    public static int lastIndexOf(double[] array, double val) {
        for (int i=array.length-1; i >= 0; i--) {
            if (array[i] == val) {
                return i;
            }
        }
        return -1;
    }
    
    /** Gets the number of times a given value occurs in an array. */
    public static int count(double[] array, double val) {
        int count = 0;
        for (int i=0; i<array.length; i++) {
            if (array[i] == val) {
                count++;
            }
        }
        return count;
    }

    public static double[] newFilled(int length, double val) {
        double[] a = new double[length];
        Arrays.fill(a, val);
        return a;
    }
    
}
