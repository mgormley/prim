package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.arrays.ShortArrays;
import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.util.JUnitUtils;
import edu.jhu.util.Timer;

public class IntShortSortTest {
        
    /* ---------- Ints and Shorts --------------*/
    
    @Test
    public void testIntShortSortValuesAsc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testIntShortSortValuesDesc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testIntShortSortValuesInfinitiesAsc() {
        short[] values = new short[]{ 1, 32767, 2, -1, -32768, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new short[]{-32768, -1, 1, 2, 5, 32767}, values);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testIntShortSortValuesInfinitiesDesc() {
        short[] values = new short[]{ 1, 32767, 2, -1, -32768, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{32767,  5, 2, 1, -1, -32768}, values);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testIntShortSortIndexAsc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntShortSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testIntShortSortIndexDesc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntShortSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
    
    @Test
    public void testRandomArraysSortAsc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            short[] values = new short[size];
            int[] index = new int[size];
            for (int j=0; j<size; j++) {
                values[j] = (short) j;
                index[j] = (int) j;
            }
            ShortArrays.shuffle(values);
            IntArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!IntSort.isSortedAsc(index));
            IntShortSort.sortIndexAsc(index, values);
            assertTrue(IntSort.isSortedAsc(index));
            
            assertTrue(!ShortSort.isSortedAsc(values));
            IntShortSort.sortValuesAsc(values, index);
            assertTrue(ShortSort.isSortedAsc(values));
        }
    }
    
    @Test
    public void testRandomArraysSortDesc() {        
        for (int i=0; i<10; i++) {           
            // Get random arrays.
            int size = 100;
            short[] values = new short[size];
            int[] index = new int[size];
            for (int j=0; j<size; j++) {
                values[j] = (short) j;
                index[j] = (int) j;
            }
            ShortArrays.shuffle(values);
            IntArrays.shuffle(index);
            
            // Sort and ONLY check the sorted array, not both.
            assertTrue(!IntSort.isSortedDesc(index));
            IntShortSort.sortIndexDesc(index, values);
            assertTrue(IntSort.isSortedDesc(index));
            
            assertTrue(!ShortSort.isSortedDesc(values));
            IntShortSort.sortValuesDesc(values, index);
            assertTrue(ShortSort.isSortedDesc(values));
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
                short[] values = new short[size];
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (short) j;
                    index[j] = (int) j;
                }
                ShortArrays.shuffle(values);
                IntArrays.shuffle(index);
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                IntShortSort.quicksortIndexRecursive(index, values, 0, index.length-1);
                timer.stop();
                
            }
            System.out.println("Total (ms) for recursive: " + timer.totMs());
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                // Get random arrays.
                short[] values = new short[size];
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    values[j] = (short) j;
                    index[j] = (int) j;
                }
                ShortArrays.shuffle(values);
                IntArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                
                timer.start();
                IntShortSort.sortIndexAsc(index, values);
                timer.stop();
                
            }
            System.out.println("Total (ms) for stack: " + timer.totMs());
        }
    }
    
}
