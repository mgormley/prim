package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.arrays.ShortArrays;
import edu.jhu.prim.util.ShortJUnitUtils;
import edu.jhu.prim.util.Timer;

public class ShortSortTest {
    
    /* ---------- Shorts only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        ShortSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        ShortJUnitUtils.assertArrayEquals(new short[]{ -1, 1, 2, 3, 5}, values);
    }
    
    @Test
    public void testQuicksortDesc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        ShortSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        ShortJUnitUtils.assertArrayEquals(new short[]{5, 3, 2, 1, -1}, values);
    }
    
    @Test
    public void testQuicksortAscMinMax() {
        short[] values = new short[]{ 1, -1, 5, Short.MAX_VALUE, Short.MIN_VALUE};
        ShortSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        ShortJUnitUtils.assertArrayEquals(new short[]{ Short.MIN_VALUE, -1, 1, 5, Short.MAX_VALUE}, values);
    }
    
    @Test
    public void testQuicksortDescMinMax() {
        short[] values = new short[]{ 1, -1, 5, Short.MAX_VALUE, Short.MIN_VALUE};
        ShortSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        ShortJUnitUtils.assertArrayEquals(new short[]{Short.MAX_VALUE, 5, 1, -1, Short.MIN_VALUE}, values);
    }
    
    @Test
    public void testQuicksortOnRandomInput() {
        Random random = new Random();
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (short) random.nextInt();
            }
            ShortSort.sortAsc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(ShortSort.isSortedAsc(values));
        }
        
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = (short) random.nextInt();
            }
            ShortSort.sortDesc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(ShortSort.isSortedDesc(values));
        }
    }
    
    /**
     * Note: When last tested ShortSort was slower than Arrays.
     * Total (ms) for Arrays.sort: 265.0
     * Total (ms) for ShortSort.sortAsc: 425.0
     */
    @Test
    public void testSortSpeedWithArrays() {  
        int numTrials = 1000; // Add a zero for results above.
        int size = 1000;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                short[] index = new short[size];
                for (int j=0; j<size; j++) {
                    index[j] = (short) j;
                }
                ShortArrays.shuffle(index);

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
                short[] index = new short[size];
                for (int j=0; j<size; j++) {
                    index[j] = (short) j;
                }
                ShortArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                timer.start();
                ShortSort.sortAsc(index);
                timer.stop();                
            }
            System.out.println("Total (ms) for ShortSort.sortAsc: " + timer.totMs());
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
                ShortSort.numSwaps = 0;
                short[] index = new short[size];
                for (int j=0; j<size; j++) {
                    index[j] = (short) -j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(ShortSort.isSortedDesc(index));
                timer.start();
                ShortSort.sortDesc(index);
                timer.stop();
                assertTrue(ShortSort.isSortedDesc(index));
            }
            System.out.println("Num swaps: " + ShortSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, ShortSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                ShortSort.numSwaps = 0;
                short[] index = new short[size];
                for (int j=0; j<size; j++) {
                    index[j] = (short) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(ShortSort.isSortedAsc(index));                
                timer.start();
                ShortSort.sortAsc(index);
                timer.stop();
                assertTrue(ShortSort.isSortedAsc(index));                
            }
            System.out.println("Num swaps: " + ShortSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, ShortSort.numSwaps);
        }
    }
    
}
