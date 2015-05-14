package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.FloatArrays;
import edu.jhu.prim.util.FloatJUnitUtils;
import edu.jhu.prim.util.Timer;

public class FloatSortTest {
    
    /* ---------- Floats only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        float[] values = new float[]{ 1.0f, 3.0f, 2.0f, -1.0f, 5.0f};
        FloatSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        FloatJUnitUtils.assertArrayEquals(new float[]{ -1.0f, 1.0f, 2.0f, 3.0f, 5.0f}, values, Primitives.DEFAULT_FLOAT_DELTA);
    }
    
    @Test
    public void testQuicksortDesc() {
        float[] values = new float[]{ 1.0f, 3.0f, 2.0f, -1.0f, 5.0f};
        FloatSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        FloatJUnitUtils.assertArrayEquals(new float[]{5.0f, 3.0f, 2.0f, 1.0f, -1.0f}, values, Primitives.DEFAULT_FLOAT_DELTA);
    }
    
    @Test
    public void testQuicksortAscInfs() {
        float[] values = new float[]{ 1.0f, -1.0f, 5.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY};
        FloatSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        FloatJUnitUtils.assertArrayEquals(new float[]{ Float.NEGATIVE_INFINITY, -1.0f, 1.0f, 5.0f, Float.POSITIVE_INFINITY}, values, Primitives.DEFAULT_FLOAT_DELTA);
    }
    
    @Test
    public void testQuicksortDescInfs() {
        float[] values = new float[]{ 1.0f, -1.0f, 5.0f, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY};
        FloatSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        FloatJUnitUtils.assertArrayEquals(new float[]{Float.POSITIVE_INFINITY, 5.0f, 1.0f, -1.0f, Float.NEGATIVE_INFINITY}, values, Primitives.DEFAULT_FLOAT_DELTA);
    }
    
    @Test
    public void testQuicksortOnRandomInput() {
        Random random = new Random();
        float[] values = new float[]{ 1.0f, 3.0f, 2.0f, -1.0f, 5.0f};
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = random.nextFloat();
            }
            FloatSort.sortAsc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(FloatSort.isSortedAsc(values));
        }
        
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = random.nextFloat();
            }
            FloatSort.sortDesc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(FloatSort.isSortedDesc(values));
        }
    }
    

    /**
     * Note: When last tested FloatSort was slower than Arrays.
     * Total (ms) for Arrays.sort: 265.0
     * Total (ms) for FloatSort.sortAsc: 425.0
     */
    @Test
    public void testSortSpeedWithArrays() {  
        int numTrials = 1000; // Add a zero for results above.
        int size = 1000;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                float[] index = new float[size];
                for (int j=0; j<size; j++) {
                    index[j] = (float) j;
                }
                FloatArrays.shuffle(index);

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
                float[] index = new float[size];
                for (int j=0; j<size; j++) {
                    index[j] = (float) j;
                }
                FloatArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                timer.start();
                FloatSort.sortAsc(index);
                timer.stop();                
            }
            System.out.println("Total (ms) for FloatSort.sortAsc: " + timer.totMs());
        }
    }    

    @Test
    public void testSortSpeedPresorted() {  
        int numTrials = 1;
        int size = 100;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                FloatSort.numSwaps = 0;
                float[] index = new float[size];
                for (int j=0; j<size; j++) {
                    index[j] = - (float) j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(FloatSort.isSortedDesc(index));
                timer.start();
                FloatSort.sortDesc(index);
                timer.stop();
                assertTrue(FloatSort.isSortedDesc(index));
            }
            System.out.println("Num swaps: " + FloatSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, FloatSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                FloatSort.numSwaps = 0;
                float[] index = new float[size];
                for (int j=0; j<size; j++) {
                    index[j] = (float) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(FloatSort.isSortedAsc(index));                
                timer.start();
                FloatSort.sortAsc(index);
                timer.stop();
                assertTrue(FloatSort.isSortedAsc(index));                
            }
            System.out.println("Num swaps: " + FloatSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, FloatSort.numSwaps);
        }
    }
    
}
