package edu.jhu.prim.arrays;

import java.util.Arrays;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.tuple.IntTuple;
import edu.jhu.prim.util.math.FastMath;
import edu.jhu.util.Prng;


/**
 * Utility methods and math for float arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class FloatArrays {

    private FloatArrays() {
        // private constructor
    }

    public static float[] copyOf(float[] original, int newLength) {
        return Arrays.copyOf(original, newLength);
    }

    public static float[] copyOf(float[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static float[][] copyOf(float[][] array) {
        float[][] newArray = new float[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    public static float[][][] copyOf(float[][][] array) {
        float[][][] clone = new float[array.length][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }
    
    public static float[][][][] copyOf(float[][][][] array) {
        float[][][][] clone = new float[array.length][][][];
        for (int i = 0; i < clone.length; i++) {
            clone[i] = copyOf(array[i]);
        }
        return clone;
    }

    public static void copy(float[] array, float[] clone) {
        assert (array.length == clone.length);
        System.arraycopy(array, 0, clone, 0, array.length);
    }

    public static void copy(float[][] array, float[][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < clone.length; i++) {
            copy(array[i], clone[i]);
        }
    }

    public static void copy(float[][][] array, float[][][] clone) {
        assert (array.length == clone.length);
        for (int i = 0; i < clone.length; i++) {
            copy(array[i], clone[i]);
        }
    }

    public static void copy(float[][][][] array, float[][][][] clone) {
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
    public static void fill(final float[] array, final float value) {
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

    public static void fill(float[][] array, float value) {
        for (int i=0; i<array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(float[][][] array, float value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(float[][][][] array, float value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }
    public static String toString(float[] array, String formatString) {
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

    public static String deepToString(float[][] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (float[] arr : array) {
            sb.append("[");
            for (float a : arr) {
                sb.append(String.format("%10.3g, ", a));
            }
            sb.append("], ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static float sum(float[] vector) {
        float sum = 0;
        for(int i=0; i<vector.length; i++) {
            sum += vector[i];
        }
        return sum;
    }

    public static void assertNoZeroes(float[] draw, float[] logDraw) {
        assertNoZeros(draw);
        assertNoNegInfs(logDraw);
    }

    public static void assertNoNegInfs(float[] logDraw) {
        for (int i=0; i<logDraw.length; i++) {
            assert(!Float.isNaN(logDraw[i]));
            assert(!Float.isInfinite(logDraw[i]));
        }
    }

    public static void assertNoZeros(float[] draw) {
        for (int i=0; i<draw.length; i++) {
            assert(!Float.isNaN(draw[i]));
            assert(draw[i] != 0);
        }
    }

    public static void assertSameSize(float[][] newLogPhi, float[][] logPhi) {
        assert(newLogPhi.length == logPhi.length);
        for (int k=0; k<logPhi.length; k++) {
            assert(newLogPhi[k].length == logPhi[k].length); 
        }
    }

    public static float[] getExp(float[] logPhi) {
        float[] phi = new float[logPhi.length];
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.exp(logPhi[i]);
        }
        return phi;
    }

    public static void exp(float[] phi) {
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.exp(phi[i]);
        }
    }

    public static void log(float[] phi) {
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.log(phi[i]);
        }
    }

    public static void log(float[][] phi) {
        for (int i=0; i<phi.length; i++) {
            log(phi[i]);
        }
    }

    public static void logForIlp(float[] phi) {
        for (int i=0; i<phi.length; i++) {
            phi[i] = FastMath.logForIlp(phi[i]);
        }
    }

    public static float[] getLog(float[] phi) {
        float[] logPhi = new float[phi.length];
        updateLogPhi(phi, logPhi);
        return logPhi;
    }

    public static float[] getLogForIlp(float[] phi) {
        float[] logPhi = new float[phi.length];
        for (int t=0; t<logPhi.length; t++) {
            logPhi[t] = FastMath.logForIlp(phi[t]);
        }
        return logPhi;
    }

    public static void updateLogPhi(float[] phi, float[] logPhi) {
    	for (int t=0; t<logPhi.length; t++) {
    		logPhi[t] = FastMath.log(phi[t]);
    	}
    }

    /**
     * TODO: This should live in a matrix class
     */
    public static float sum(float[][] matrix) {
        float sum = 0; 
        for (int i=0; i<matrix.length; i++) {
            sum += sum(matrix[i]);
        }
        return sum;
    }

    public static float max(float[] array) {
        float max = Float.NEGATIVE_INFINITY;
        for (int i=0; i<array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static float min(float[] array) {
        float min = Float.POSITIVE_INFINITY;
        for (int i=0; i<array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static int argmax(float[] array) {
        float max = Float.NEGATIVE_INFINITY;
        int argmax = -1;
        for (int i=0; i<array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                argmax = i;
            }
        }
        return argmax;
    }

    public static int argmin(float[] array) {
        float min = Float.POSITIVE_INFINITY;
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
    public static IntTuple getArgmax(float[][] array) {
        return getArgmax(array, Primitives.DEFAULT_FLOAT_DELTA);
    }
    
    /**
     * Gets the argmax breaking ties randomly.
     */
    public static IntTuple getArgmax(float[][] array, float delta) {
        float maxValue = Float.NEGATIVE_INFINITY;
        int maxX = -1;
        int maxY = -1;
        float numMax = 1;
        for (int x=0; x<array.length; x++) {
            for (int y=0; y<array[x].length; y++) {
                float diff = Primitives.compare(array[x][y], maxValue, delta);
                if (diff == 0 && Prng.nextFloat() < 1.0 / numMax) {
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


    public static float dotProduct(float[] array1, float[] array2) {
        if (array1.length != array2.length) {
            throw new IllegalStateException("array1.length != array2.length");
        }
        float dotProduct = 0;
        for (int i=0; i<array1.length; i++) {
            dotProduct += array1[i] * array2[i];
        }
        return dotProduct;
    }

    public static void scale(float[] array, float alpha) {
        for (int i=0; i<array.length; i++) {
            array[i] *= alpha;
        }
    }

    public static float mean(float[] array) {
        return sum(array) / array.length;
    }

    public static float variance(float[] array) {
        float mean = mean(array);
        float sumOfSquares = 0;
        for (int i=0; i<array.length; i++) {
            sumOfSquares += (array[i] - mean)*(array[i] - mean);
        }
        return sumOfSquares / (array.length - 1);
    }

    public static float stdDev(float[] array) {
        return FastMath.sqrt(variance(array));
    }

    public static float logSum(float[] logProps) {
        float logPropSum = Float.NEGATIVE_INFINITY;
        for (int d = 0; d < logProps.length; d++) {
            logPropSum = FastMath.logAdd(logPropSum, logProps[d]);
        }
        return logPropSum;
    }

    public static void add(float[] params, float lambda) {
        for (int i=0; i<params.length; i++) {
            params[i] += lambda;
        }
    }
    
    /** Each element of the second array is added to each element of the first. */
    public static void add(float[] array1, float[] array2) {
        assert (array1.length == array2.length);
        for (int i=0; i<array1.length; i++) {
            array1[i] += array2[i];
        }
    }
    
    public static float infinityNorm(float[] gradient) {
        float maxAbs = 0;
        for (int i=0; i<gradient.length; i++) {
            float tempVal = Math.abs(gradient[i]);
            if (tempVal > maxAbs) {
                maxAbs = tempVal;
            }
        }
        return maxAbs;
    }

    public static float infinityNorm(float[][] gradient) {
        float maxIN = 0;
        for (int i=0; i<gradient.length; i++) {
            float tempVal = infinityNorm(gradient[i]);
            if (tempVal > maxIN) {
                maxIN = tempVal;
            }
        }
        return maxIN;
    }

    public static boolean isInRange(float[] p, float min, float max) {
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
    public static void reorder(float[] array, int[] order) {
        float[] original = copyOf(array);
        for (int i=0; i<array.length; i++) {
            array[i] = original[order[i]];
        }
    }
    
}
