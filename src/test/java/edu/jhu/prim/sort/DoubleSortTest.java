package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.sort.DoubleSort;
import edu.jhu.prim.util.JUnitUtils;

public class DoubleSortTest {
    
    /* ---------- Doubles only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0, 5.0};
        DoubleSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new double[]{ -1.0, 1.0f, 2.0, 3.0f, 5.0}, values, 1e-13);
    }
    
    @Test
    public void testQuicksortDesc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0, 5.0};
        DoubleSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new double[]{5.0, 3.0, 2.0, 1.0, -1.0}, values, 1e-13);
    }
    
    @Test
    public void testQuicksortOnRandomInput() {
        Random random = new Random();
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0, 5.0};
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
    
}
