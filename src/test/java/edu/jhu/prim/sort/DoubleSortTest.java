package edu.jhu.prim.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.arrays.DoubleArrays;
import edu.jhu.prim.util.DoubleJUnitUtils;
import edu.jhu.prim.util.Timer;

public class DoubleSortTest {
    
    /* ---------- Doubles only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0f, 5.0f};
        DoubleSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        DoubleJUnitUtils.assertArrayEquals(new double[]{ -1.0f, 1.0f, 2.0f, 3.0f, 5.0f}, values, Primitives.DEFAULT_DOUBLE_DELTA);
    }
    
    @Test
    public void testQuicksortDesc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0f, 5.0f};
        DoubleSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        DoubleJUnitUtils.assertArrayEquals(new double[]{5.0f, 3.0f, 2.0f, 1.0f, -1.0f}, values, Primitives.DEFAULT_DOUBLE_DELTA);
    }
    
    @Test
    public void testQuicksortAscInfs() {
        double[] values = new double[]{ 1.0f, -1.0f, 5.0f, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        DoubleSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        DoubleJUnitUtils.assertArrayEquals(new double[]{ Double.NEGATIVE_INFINITY, -1.0f, 1.0f, 5.0f, Double.POSITIVE_INFINITY}, values, Primitives.DEFAULT_DOUBLE_DELTA);
    }
    
    @Test
    public void testQuicksortDescInfs() {
        double[] values = new double[]{ 1.0f, -1.0f, 5.0f, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        DoubleSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        DoubleJUnitUtils.assertArrayEquals(new double[]{Double.POSITIVE_INFINITY, 5.0f, 1.0f, -1.0f, Double.NEGATIVE_INFINITY}, values, Primitives.DEFAULT_DOUBLE_DELTA);
    }
    
    @Test
    public void testQuicksortOnRandomInput() {
        Random random = new Random();
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0f, 5.0f};
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = random.nextDouble();
            }
            DoubleSort.sortAsc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(DoubleSort.isSortedAsc(values));
        }
        
        for (int i=0; i<10; i++) {            
            for (int j=0; j<values.length; j++) {
                values[j] = random.nextDouble();
            }
            DoubleSort.sortDesc(values);
            System.out.println(Arrays.toString(values));
            assertTrue(DoubleSort.isSortedDesc(values));
        }
    }
    

    /**
     * Note: When last tested DoubleSort was slower than Arrays.
     * Total (ms) for Arrays.sort: 265.0
     * Total (ms) for DoubleSort.sortAsc: 425.0
     */
    @Test
    public void testSortSpeedWithArrays() {  
        int numTrials = 1000; // Add a zero for results above.
        int size = 1000;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                double[] index = new double[size];
                for (int j=0; j<size; j++) {
                    index[j] = (double) j;
                }
                DoubleArrays.shuffle(index);

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
                double[] index = new double[size];
                for (int j=0; j<size; j++) {
                    index[j] = (double) j;
                }
                DoubleArrays.shuffle(index);

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                timer.start();
                DoubleSort.sortAsc(index);
                timer.stop();                
            }
            System.out.println("Total (ms) for DoubleSort.sortAsc: " + timer.totMs());
        }
    }    

    @Test
    public void testSortSpeedPresorted() {  
        int numTrials = 1;
        int size = 100;
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {           
                DoubleSort.numSwaps = 0;
                double[] index = new double[size];
                for (int j=0; j<size; j++) {
                    index[j] = - (double) j;
                }

                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(DoubleSort.isSortedDesc(index));
                timer.start();
                DoubleSort.sortDesc(index);
                timer.stop();
                assertTrue(DoubleSort.isSortedDesc(index));
            }
            System.out.println("Num swaps: " + DoubleSort.numSwaps);
            System.out.println("Total (ms) for descending: " + timer.totMs());
            assertEquals(0, DoubleSort.numSwaps);
        }
        {
            Timer timer = new Timer();
            for (int trial=0; trial<numTrials; trial++) {  
                DoubleSort.numSwaps = 0;
                double[] index = new double[size];
                for (int j=0; j<size; j++) {
                    index[j] = (double) j;
                }
                
                if (trial == numTrials/2) {
                    timer = new Timer();
                }
                assertTrue(DoubleSort.isSortedAsc(index));                
                timer.start();
                DoubleSort.sortAsc(index);
                timer.stop();
                assertTrue(DoubleSort.isSortedAsc(index));                
            }
            System.out.println("Num swaps: " + DoubleSort.numSwaps);
            System.out.println("Total (ms) for ascending: " + timer.totMs());
            assertEquals(0, DoubleSort.numSwaps);
        }
    }
    
}
