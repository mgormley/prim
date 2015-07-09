package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.util.IntJUnitUtils;
import edu.jhu.prim.util.Timer;

public class LongIntSortTest {
        
    /* ---------- Longs and Ints --------------*/
    
    @Test
    public void testLongIntSortValuesAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = LongArrays.range(values.length);
        LongIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        IntJUnitUtils.assertArrayEquals(new int[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new long[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testLongIntSortValuesDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = LongArrays.range(values.length);
        LongIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        IntJUnitUtils.assertArrayEquals(new int[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new long[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testLongIntSortValuesInfinitiesAsc() {
        int[] values = new int[]{ 1, Integer.MAX_VALUE, 2, -1, Integer.MIN_VALUE, 5};
        long[] index = LongArrays.range(values.length);
        LongIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        IntJUnitUtils.assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 1, 2, 5, Integer.MAX_VALUE}, values);
        Assert.assertArrayEquals(new long[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testLongIntSortValuesInfinitiesDesc() {
        int[] values = new int[]{ 1, Integer.MAX_VALUE, 2, -1, Integer.MIN_VALUE, 5};
        long[] index = LongArrays.range(values.length);
        LongIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        IntJUnitUtils.assertArrayEquals(new int[]{Integer.MAX_VALUE,  5, 2, 1, -1, Integer.MIN_VALUE}, values);
        Assert.assertArrayEquals(new long[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testLongIntSortIndexAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = new long[] { 1, 4, 5, 8, 3};
        LongIntSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        IntJUnitUtils.assertArrayEquals(new int[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new long[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testLongIntSortIndexDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = new long[] { 1, 4, 5, 8, 3};
        LongIntSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        IntJUnitUtils.assertArrayEquals(new int[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new long[]{ 8, 5, 4, 3, 1 }, index);
    }
    
    @Test
    public void testRandomArraysSortAsc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            int[] values = new int[size];
            long[] index = new long[size];
            for (int j=0; j<size; j++) {
                values[j] = (int) j;
                index[j] = (long) j;
            }
            IntArrays.shuffle(values);
            LongArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!LongSort.isSortedAsc(index));
            LongIntSort.sortIndexAsc(index, values);
            assertTrue(LongSort.isSortedAsc(index));
            
            assertTrue(!IntSort.isSortedAsc(values));
            LongIntSort.sortValuesAsc(values, index);
            assertTrue(IntSort.isSortedAsc(values));
        }
    }
    
    @Test
    public void testRandomArraysSortDesc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            int[] values = new int[size];
            long[] index = new long[size];
            for (int j=0; j<size; j++) {
                values[j] = (int) j;
                index[j] = (long) j;
            }
            IntArrays.shuffle(values);
            LongArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!LongSort.isSortedDesc(index));
            LongIntSort.sortIndexDesc(index, values);
            assertTrue(LongSort.isSortedDesc(index));
            
            assertTrue(!IntSort.isSortedDesc(values));
            LongIntSort.sortValuesDesc(values, index);
            assertTrue(IntSort.isSortedDesc(values));
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
                int[] values = new int[size];
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    values[j] = (int) j;
                    index[j] = (long) j;
                }
                IntArrays.shuffle(values);
                LongArrays.shuffle(index);
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                LongIntSort.quicksortIndexRecursive(index, values, 0, index.length-1, true);
                timer.stop();                
            }
            System.out.println("Total (ms) for recursive: " + timer.totMs());
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                // Get random arrays.
                int[] values = new int[size];
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    values[j] = (int) j;
                    index[j] = (long) j;
                }
                IntArrays.shuffle(values);
                LongArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                LongIntSort.sortIndexAsc(index, values);
                timer.stop();
                
            }
            System.out.println("Total (ms) for stack: " + timer.totMs());
        }
    }
    
    @Test
    public void testSortSpeedPresorted() {  
        int numTrials = 1; // Add a zero for results above.
        int size = Byte.MAX_VALUE;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                LongIntSort.numSwaps = 0;
                int[] values = new int[size];
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    values[j] = (int) -j;
                    index[j] = (long) -j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(LongSort.isSortedDesc(index));
                timer.start();
                LongIntSort.sortIndexDesc(index, values);
                timer.stop();     
                assertTrue(LongSort.isSortedDesc(index));
                assertEquals(0, LongIntSort.numSwaps);

                assertTrue(IntSort.isSortedDesc(values));
                timer.start();
                LongIntSort.sortValuesDesc(values, index);
                timer.stop();
                assertTrue(IntSort.isSortedDesc(values));
                assertEquals(0, LongIntSort.numSwaps);   
            }
            System.out.println("Num swaps: " + LongIntSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, LongIntSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                LongIntSort.numSwaps = 0;
                int[] values = new int[size];
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    values[j] = (int) j;
                    index[j] = (long) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(LongSort.isSortedAsc(index));
                timer.start();
                LongIntSort.sortIndexAsc(index, values);
                timer.stop();     
                assertTrue(LongSort.isSortedAsc(index));
                assertEquals(0, LongIntSort.numSwaps);

                assertTrue(IntSort.isSortedAsc(values));
                timer.start();
                LongIntSort.sortValuesAsc(values, index);
                timer.stop();
                assertTrue(IntSort.isSortedAsc(values));
                assertEquals(0, LongIntSort.numSwaps);                
            }
            System.out.println("Num swaps: " + LongIntSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, LongIntSort.numSwaps);
        }
    }
    
}
