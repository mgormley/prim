package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.util.LongJUnitUtils;
import edu.jhu.prim.util.Timer;

public class IntLongSortTest {
        
    /* ---------- Ints and Longs --------------*/
    
    @Test
    public void testIntLongSortValuesAsc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = IntArrays.range(values.length);
        IntLongSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        LongJUnitUtils.assertArrayEquals(new long[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testIntLongSortValuesDesc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = IntArrays.range(values.length);
        IntLongSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        LongJUnitUtils.assertArrayEquals(new long[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testIntLongSortValuesInfinitiesAsc() {
        long[] values = new long[]{ 1, Long.MAX_VALUE, 2, -1, Long.MIN_VALUE, 5};
        int[] index = IntArrays.range(values.length);
        IntLongSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        LongJUnitUtils.assertArrayEquals(new long[]{Long.MIN_VALUE, -1, 1, 2, 5, Long.MAX_VALUE}, values);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testIntLongSortValuesInfinitiesDesc() {
        long[] values = new long[]{ 1, Long.MAX_VALUE, 2, -1, Long.MIN_VALUE, 5};
        int[] index = IntArrays.range(values.length);
        IntLongSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        LongJUnitUtils.assertArrayEquals(new long[]{Long.MAX_VALUE,  5, 2, 1, -1, Long.MIN_VALUE}, values);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testIntLongSortIndexAsc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntLongSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        LongJUnitUtils.assertArrayEquals(new long[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testIntLongSortIndexDesc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntLongSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        LongJUnitUtils.assertArrayEquals(new long[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
    
    @Test
    public void testRandomArraysSortAsc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            long[] values = new long[size];
            int[] index = new int[size];
            for (int j=0; j<size; j++) {
                values[j] = (long) j;
                index[j] = (int) j;
            }
            LongArrays.shuffle(values);
            IntArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!IntSort.isSortedAsc(index));
            IntLongSort.sortIndexAsc(index, values);
            assertTrue(IntSort.isSortedAsc(index));
            
            assertTrue(!LongSort.isSortedAsc(values));
            IntLongSort.sortValuesAsc(values, index);
            assertTrue(LongSort.isSortedAsc(values));
        }
    }
    
    @Test
    public void testRandomArraysSortDesc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            long[] values = new long[size];
            int[] index = new int[size];
            for (int j=0; j<size; j++) {
                values[j] = (long) j;
                index[j] = (int) j;
            }
            LongArrays.shuffle(values);
            IntArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!IntSort.isSortedDesc(index));
            IntLongSort.sortIndexDesc(index, values);
            assertTrue(IntSort.isSortedDesc(index));
            
            assertTrue(!LongSort.isSortedDesc(values));
            IntLongSort.sortValuesDesc(values, index);
            assertTrue(LongSort.isSortedDesc(values));
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
                long[] values = new long[size];
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (long) j;
                    index[j] = (int) j;
                }
                LongArrays.shuffle(values);
                IntArrays.shuffle(index);
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                IntLongSort.quicksortIndexRecursive(index, values, 0, index.length-1, true);
                timer.stop();                
            }
            System.out.println("Total (ms) for recursive: " + timer.totMs());
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                // Get random arrays.
                long[] values = new long[size];
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (long) j;
                    index[j] = (int) j;
                }
                LongArrays.shuffle(values);
                IntArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                IntLongSort.sortIndexAsc(index, values);
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
                IntLongSort.numSwaps = 0;
                long[] values = new long[size];
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (long) -j;
                    index[j] = (int) -j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(IntSort.isSortedDesc(index));
                timer.start();
                IntLongSort.sortIndexDesc(index, values);
                timer.stop();     
                assertTrue(IntSort.isSortedDesc(index));
                assertEquals(0, IntLongSort.numSwaps);

                assertTrue(LongSort.isSortedDesc(values));
                timer.start();
                IntLongSort.sortValuesDesc(values, index);
                timer.stop();
                assertTrue(LongSort.isSortedDesc(values));
                assertEquals(0, IntLongSort.numSwaps);   
            }
            System.out.println("Num swaps: " + IntLongSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, IntLongSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                IntLongSort.numSwaps = 0;
                long[] values = new long[size];
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (long) j;
                    index[j] = (int) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(IntSort.isSortedAsc(index));
                timer.start();
                IntLongSort.sortIndexAsc(index, values);
                timer.stop();     
                assertTrue(IntSort.isSortedAsc(index));
                assertEquals(0, IntLongSort.numSwaps);

                assertTrue(LongSort.isSortedAsc(values));
                timer.start();
                IntLongSort.sortValuesAsc(values, index);
                timer.stop();
                assertTrue(LongSort.isSortedAsc(values));
                assertEquals(0, IntLongSort.numSwaps);                
            }
            System.out.println("Num swaps: " + IntLongSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, IntLongSort.numSwaps);
        }
    }
    
}
