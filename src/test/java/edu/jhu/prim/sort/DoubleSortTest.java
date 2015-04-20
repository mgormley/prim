package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.DoubleJUnitUtils;

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
    
}
