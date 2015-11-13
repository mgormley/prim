package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.arrays.ByteArrays;
import edu.jhu.prim.util.ByteJUnitUtils;
import edu.jhu.prim.util.Timer;

public class ByteSortTest {
    
    /* ---------- Bytes only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        byte[] values = new byte[]{ 1, 3, 2, -1, 5};
        ByteSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        ByteJUnitUtils.assertArrayEquals(new byte[]{ -1, 1, 2, 3, 5}, values);
    }
    
    @Test
    public void testQuicksortDesc() {
        byte[] values = new byte[]{ 1, 3, 2, -1, 5};
        ByteSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        ByteJUnitUtils.assertArrayEquals(new byte[]{5, 3, 2, 1, -1}, values);
    }
    
    @Test
    public void testQuicksortAscMinMax() {
        byte[] values = new byte[]{ 1, -1, 5, Byte.MAX_VALUE, Byte.MIN_VALUE};
        ByteSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        ByteJUnitUtils.assertArrayEquals(new byte[]{ Byte.MIN_VALUE, -1, 1, 5, Byte.MAX_VALUE}, values);
    }
    
    @Test
    public void testQuicksortDescMinMax() {
        byte[] values = new byte[]{ 1, -1, 5, Byte.MAX_VALUE, Byte.MIN_VALUE};
        ByteSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        ByteJUnitUtils.assertArrayEquals(new byte[]{Byte.MAX_VALUE, 5, 1, -1, Byte.MIN_VALUE}, values);
    }
    
    @Test
    public void testQuicksortOnRandomInput() {
        Random random = new Random();
        byte[] values = new byte[]{ 1, 3, 2, -1, 5};
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (byte) random.nextInt();
            }
            ByteSort.sortAsc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(ByteSort.isSortedAsc(values));
        }
        
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (byte) random.nextInt();
            }
            ByteSort.sortDesc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(ByteSort.isSortedDesc(values));
        }
    }
    
    /**
     * Note: When last tested ByteSort was slower than Arrays.
     * Total (ms) for Arrays.sort: 265.0
     * Total (ms) for ByteSort.sortAsc: 425.0
     */
    @Test
    public void testSortSpeedWithArrays() {  
        int numTrials = 1000; // Add a zero for results above.
        int size = 1000;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                byte[] index = new byte[size];
                for (int j=0; j<size; j++) {
                    index[j] = (byte) j;
                }
                ByteArrays.shuffle(index);

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
                byte[] index = new byte[size];
                for (int j=0; j<size; j++) {
                    index[j] = (byte) j;
                }
                ByteArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                timer.start();
                ByteSort.sortAsc(index);
                timer.stop();                
            }
            System.out.println("Total (ms) for ByteSort.sortAsc: " + timer.totMs());
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
                ByteSort.numSwaps = 0;
                byte[] index = new byte[size];
                for (int j=0; j<size; j++) {
                    index[j] = (byte) -j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(ByteSort.isSortedDesc(index));
                timer.start();
                ByteSort.sortDesc(index);
                timer.stop();
                assertTrue(ByteSort.isSortedDesc(index));
            }
            System.out.println("Num swaps: " + ByteSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, ByteSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                ByteSort.numSwaps = 0;
                byte[] index = new byte[size];
                for (int j=0; j<size; j++) {
                    index[j] = (byte) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(ByteSort.isSortedAsc(index));                
                timer.start();
                ByteSort.sortAsc(index);
                timer.stop();
                assertTrue(ByteSort.isSortedAsc(index));                
            }
            System.out.println("Num swaps: " + ByteSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, ByteSort.numSwaps);
        }
    }

    @Test
    public void testQuicksortAscSublist() {
        byte[] values = new byte[]{ 1, 3, 2, -1, 5};
        ByteSort.sortAsc(values, 1, 3);
        System.out.println(Arrays.toString(values));
        ByteJUnitUtils.assertArrayEquals(new byte[]{ 1, 2, 3, -1, 5}, values);
    }

    @Test
    public void testQuicksortDescSublist() {
        byte[] values = new byte[]{ 1, 2, 3, -1, 5};
        ByteSort.sortDesc(values, 1, 3);
        System.out.println(Arrays.toString(values));
        ByteJUnitUtils.assertArrayEquals(new byte[]{ 1, 3, 2, -1, 5}, values);
    }
}
