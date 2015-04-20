package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.FloatJUnitUtils;

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
    
}
