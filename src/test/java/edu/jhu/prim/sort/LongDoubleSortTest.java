package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.arrays.DoubleArrays;
import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.util.JUnitUtils;
import edu.jhu.util.Timer;

public class LongDoubleSortTest {
        
    /* ---------- Longs and Doubles --------------*/
    
    @Test
    public void testLongDoubleSortValuesAsc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        long[] index = LongArrays.range(values.length);
        LongDoubleSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ -1, 1, 2, 3, 5}, values, 1e-13);
        Assert.assertArrayEquals(new long[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testLongDoubleSortValuesDesc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        long[] index = LongArrays.range(values.length);
        LongDoubleSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ 5, 3, 2, 1, -1}, values, 1e-13);
        Assert.assertArrayEquals(new long[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testLongDoubleSortValuesInfinitiesAsc() {
        double[] values = new double[]{ 1, Double.POSITIVE_INFINITY, 2, -1, Double.NEGATIVE_INFINITY, 5};
        long[] index = LongArrays.range(values.length);
        LongDoubleSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new double[]{Double.NEGATIVE_INFINITY, -1, 1, 2, 5, Double.POSITIVE_INFINITY}, values, 1e-13);
        Assert.assertArrayEquals(new long[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testLongDoubleSortValuesInfinitiesDesc() {
        double[] values = new double[]{ 1, Double.POSITIVE_INFINITY, 2, -1, Double.NEGATIVE_INFINITY, 5};
        long[] index = LongArrays.range(values.length);
        LongDoubleSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{Double.POSITIVE_INFINITY,  5, 2, 1, -1, Double.NEGATIVE_INFINITY}, values, 1e-13);
        Assert.assertArrayEquals(new long[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testLongDoubleSortIndexAsc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        long[] index = new long[] { 1, 4, 5, 8, 3};
        LongDoubleSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ 1, 5, 3, 2, -1 }, values, 1e-13);
        Assert.assertArrayEquals(new long[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testLongDoubleSortIndexDesc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        long[] index = new long[] { 1, 4, 5, 8, 3};
        LongDoubleSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ -1, 2, 3, 5, 1 }, values, 1e-13);
        Assert.assertArrayEquals(new long[]{ 8, 5, 4, 3, 1 }, index);
    }
    
    @Test
    public void testRandomArraysSortAsc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            double[] values = new double[size];
            long[] index = new long[size];
            for (int j=0; j<size; j++) {
                values[j] = (double) j;
                index[j] = (long) j;
            }
            DoubleArrays.shuffle(values);
            LongArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!LongSort.isSortedAsc(index));
            LongDoubleSort.sortIndexAsc(index, values);
            assertTrue(LongSort.isSortedAsc(index));
            
            assertTrue(!DoubleSort.isSortedAsc(values));
            LongDoubleSort.sortValuesAsc(values, index);
            assertTrue(DoubleSort.isSortedAsc(values));
        }
    }
    
    @Test
    public void testRandomArraysSortDesc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            double[] values = new double[size];
            long[] index = new long[size];
            for (int j=0; j<size; j++) {
                values[j] = (double) j;
                index[j] = (long) j;
            }
            DoubleArrays.shuffle(values);
            LongArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!LongSort.isSortedDesc(index));
            LongDoubleSort.sortIndexDesc(index, values);
            assertTrue(LongSort.isSortedDesc(index));
            
            assertTrue(!DoubleSort.isSortedDesc(values));
            LongDoubleSort.sortValuesDesc(values, index);
            assertTrue(DoubleSort.isSortedDesc(values));
        }
    }
    

    /** 
     * OUTPUT:
     * Total (ms) for recursive: 409.0
     * Total (ms) for stack: 410.0
     */
    @Test
    public void testSortSpeed() {  
        int numTrials = 1000; // Add a zero for results above.
        int size = 1000;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                // Get random arrays.
                double[] values = new double[size];
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    values[j] = (double) j;
                    index[j] = (long) j;
                }
                DoubleArrays.shuffle(values);
                LongArrays.shuffle(index);
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                LongDoubleSort.quicksortIndexRecursive(index, values, 0, index.length-1);
                timer.stop();
                
            }
            System.out.println("Total (ms) for recursive: " + timer.totMs());
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                // Get random arrays.
                double[] values = new double[size];
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    values[j] = (double) j;
                    index[j] = (long) j;
                }
                DoubleArrays.shuffle(values);
                LongArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                LongDoubleSort.sortIndexAsc(index, values);
                timer.stop();
                
            }
            System.out.println("Total (ms) for stack: " + timer.totMs());
        }
    }
    
}
