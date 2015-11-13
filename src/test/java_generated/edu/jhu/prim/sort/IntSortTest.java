package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.arrays.IntArrays;
import edu.jhu.prim.util.IntJUnitUtils;
import edu.jhu.prim.util.Timer;

public class IntSortTest {
    
    /* ---------- Ints only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        IntSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        IntJUnitUtils.assertArrayEquals(new int[]{ -1, 1, 2, 3, 5}, values);
    }
    
    @Test
    public void testQuicksortDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        IntSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        IntJUnitUtils.assertArrayEquals(new int[]{5, 3, 2, 1, -1}, values);
    }
    
    @Test
    public void testQuicksortAscMinMax() {
        int[] values = new int[]{ 1, -1, 5, Integer.MAX_VALUE, Integer.MIN_VALUE};
        IntSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        IntJUnitUtils.assertArrayEquals(new int[]{ Integer.MIN_VALUE, -1, 1, 5, Integer.MAX_VALUE}, values);
    }
    
    @Test
    public void testQuicksortDescMinMax() {
        int[] values = new int[]{ 1, -1, 5, Integer.MAX_VALUE, Integer.MIN_VALUE};
        IntSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        IntJUnitUtils.assertArrayEquals(new int[]{Integer.MAX_VALUE, 5, 1, -1, Integer.MIN_VALUE}, values);
    }
    
    @Test
    public void testQuicksortOnRandomInput() {
        Random random = new Random();
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (int) random.nextInt();
            }
            IntSort.sortAsc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(IntSort.isSortedAsc(values));
        }
        
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (int) random.nextInt();
            }
            IntSort.sortDesc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(IntSort.isSortedDesc(values));
        }
    }
    
    /**
     * Note: When last tested IntSort was slower than Arrays.
     * Total (ms) for Arrays.sort: 265.0
     * Total (ms) for IntSort.sortAsc: 425.0
     */
    @Test
    public void testSortSpeedWithArrays() {  
        int numTrials = 1000; // Add a zero for results above.
        int size = 1000;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    index[j] = (int) j;
                }
                IntArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                timer.start();
                Arrays.sort(index);
                timer.stop();                
            }
            System.out.println("Total (ms) for Arrays.sort: " + timer.totMs());
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    index[j] = (int) j;
                }
                IntArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                timer.start();
                IntSort.sortAsc(index);
                timer.stop();                
            }
            System.out.println("Total (ms) for IntSort.sortAsc: " + timer.totMs());
        }
    }    

    @Test
    public void testSortSpeedPresorted() {  
        int numTrials = 1;
        // This is a trick to ensure the other cases (e.g. ByteSort are handled correctly)
        int size = Byte.MAX_VALUE;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                IntSort.numSwaps = 0;
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    index[j] = (int) -j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(IntSort.isSortedDesc(index));
                timer.start();
                IntSort.sortDesc(index);
                timer.stop();
                assertTrue(IntSort.isSortedDesc(index));
            }
            System.out.println("Num swaps: " + IntSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, IntSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                IntSort.numSwaps = 0;
                int[] index = new int[size];
                for (int j=0; j<size; j++) {
                    index[j] = (int) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(IntSort.isSortedAsc(index));                
                timer.start();
                IntSort.sortAsc(index);
                timer.stop();
                assertTrue(IntSort.isSortedAsc(index));                
            }
            System.out.println("Num swaps: " + IntSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, IntSort.numSwaps);
        }
    }

    @Test
    public void testQuicksortAscSublist() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        IntSort.sortAsc(values, 1, 3);
        System.out.println(Arrays.toString(values));
        IntJUnitUtils.assertArrayEquals(new int[]{ 1, 2, 3, -1, 5}, values);
    }

    @Test
    public void testQuicksortDescSublist() {
        int[] values = new int[]{ 1, 2, 3, -1, 5};
        IntSort.sortDesc(values, 1, 3);
        System.out.println(Arrays.toString(values));
        IntJUnitUtils.assertArrayEquals(new int[]{ 1, 3, 2, -1, 5}, values);
    }
}
