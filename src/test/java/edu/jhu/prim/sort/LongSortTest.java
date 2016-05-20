package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.arrays.LongArrays;
import edu.jhu.prim.util.LongJUnitUtils;
import edu.jhu.prim.util.Timer;

public class LongSortTest {
    
    /* ---------- Longs only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        LongSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        LongJUnitUtils.assertArrayEquals(new long[]{ -1, 1, 2, 3, 5}, values);
    }
    
    @Test
    public void testQuicksortDesc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        LongSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        LongJUnitUtils.assertArrayEquals(new long[]{5, 3, 2, 1, -1}, values);
    }
    
    @Test
    public void testQuicksortAscMinMax() {
        long[] values = new long[]{ 1, -1, 5, Long.MAX_VALUE, Long.MIN_VALUE};
        LongSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        LongJUnitUtils.assertArrayEquals(new long[]{ Long.MIN_VALUE, -1, 1, 5, Long.MAX_VALUE}, values);
    }
    
    @Test
    public void testQuicksortDescMinMax() {
        long[] values = new long[]{ 1, -1, 5, Long.MAX_VALUE, Long.MIN_VALUE};
        LongSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        LongJUnitUtils.assertArrayEquals(new long[]{Long.MAX_VALUE, 5, 1, -1, Long.MIN_VALUE}, values);
    }
    
    @Test
    public void testQuicksortOnRandomInput() {
        Random random = new Random();
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (long) random.nextInt();
            }
            LongSort.sortAsc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(LongSort.isSortedAsc(values));
        }
        
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (long) random.nextInt();
            }
            LongSort.sortDesc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(LongSort.isSortedDesc(values));
        }
    }
    
    /**
     * Note: When last tested LongSort was slower than Arrays.
     * Total (ms) for Arrays.sort: 265.0
     * Total (ms) for LongSort.sortAsc: 425.0
     */
    @Test
    public void testSortSpeedWithArrays() {  
        int numTrials = 1000; // Add a zero for results above.
        int size = 1000;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    index[j] = (long) j;
                }
                LongArrays.shuffle(index);

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
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    index[j] = (long) j;
                }
                LongArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                timer.start();
                LongSort.sortAsc(index);
                timer.stop();                
            }
            System.out.println("Total (ms) for LongSort.sortAsc: " + timer.totMs());
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
                LongSort.numSwaps = 0;
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    index[j] = (long) -j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(LongSort.isSortedDesc(index));
                timer.start();
                LongSort.sortDesc(index);
                timer.stop();
                assertTrue(LongSort.isSortedDesc(index));
            }
            System.out.println("Num swaps: " + LongSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, LongSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                LongSort.numSwaps = 0;
                long[] index = new long[size];
                for (int j=0; j<size; j++) {
                    index[j] = (long) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(LongSort.isSortedAsc(index));                
                timer.start();
                LongSort.sortAsc(index);
                timer.stop();
                assertTrue(LongSort.isSortedAsc(index));                
            }
            System.out.println("Num swaps: " + LongSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, LongSort.numSwaps);
        }
    }

    @Test
    public void testQuicksortAscSublist() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        LongSort.sortAsc(values, 1, 3);
        System.out.println(Arrays.toString(values));
        LongJUnitUtils.assertArrayEquals(new long[]{ 1, 2, 3, -1, 5}, values);
    }

    @Test
    public void testQuicksortDescSublist() {
        long[] values = new long[]{ 1, 2, 3, -1, 5};
        LongSort.sortDesc(values, 1, 3);
        System.out.println(Arrays.toString(values));
        LongJUnitUtils.assertArrayEquals(new long[]{ 1, 3, 2, -1, 5}, values);
    }
}
