package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.util.JUnitUtils;
import edu.jhu.util.Timer;

public class IntIntSortTest {
        
    /* ---------- Ints and Ints --------------*/
    
    @Test
    public void testIntIntSortValuesAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testIntIntSortValuesDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testIntIntSortValuesInfinitiesAsc() {
        int[] values = new int[]{ 1, 2147483646, 2, -1, -2147483646, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new int[]{-2147483646, -1, 1, 2, 5, 2147483646}, values);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testIntIntSortValuesInfinitiesDesc() {
        int[] values = new int[]{ 1, 2147483646, 2, -1, -2147483646, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{2147483646,  5, 2, 1, -1, -2147483646}, values);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testIntIntSortIndexAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntIntSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testIntIntSortIndexDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntIntSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
    
    @Test
    public void testRandomArraysSortAsc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            int[] values = new int[size];
            int[] index = new int[size];
            for (int j=0; j<size; j++) {
                values[j] = (int) j;
                index[j] = (int) j;
            }
            IntArrays.shuffle(values);
            IntArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!IntSort.isSortedAsc(index));
            IntIntSort.sortIndexAsc(index, values);
            assertTrue(IntSort.isSortedAsc(index));
            
            assertTrue(!IntSort.isSortedAsc(values));
            IntIntSort.sortValuesAsc(values, index);
            assertTrue(IntSort.isSortedAsc(values));
        }
    }
    
    @Test
    public void testRandomArraysSortDesc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            int[] values = new int[size];
            int[] index = new int[size];
            for (int j=0; j<size; j++) {
                values[j] = (int) j;
                index[j] = (int) j;
            }
            IntArrays.shuffle(values);
            IntArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!IntSort.isSortedDesc(index));
            IntIntSort.sortIndexDesc(index, values);
            assertTrue(IntSort.isSortedDesc(index));
            
            assertTrue(!IntSort.isSortedDesc(values));
            IntIntSort.sortValuesDesc(values, index);
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
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (int) j;
                    index[j] = (int) j;
                }
                IntArrays.shuffle(values);
                IntArrays.shuffle(index);
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                IntIntSort.quicksortIndexRecursive(index, values, 0, index.length-1);
                timer.stop();
                
            }
            System.out.println("Total (ms) for recursive: " + timer.totMs());
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                // Get random arrays.
                int[] values = new int[size];
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (int) j;
                    index[j] = (int) j;
                }
                IntArrays.shuffle(values);
                IntArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                IntIntSort.sortIndexAsc(index, values);
                timer.stop();
                
            }
            System.out.println("Total (ms) for stack: " + timer.totMs());
        }
    }
    
}
