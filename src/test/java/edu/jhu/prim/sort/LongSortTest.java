package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.util.JUnitUtils;

public class LongSortTest {
    
    /* ---------- Longs only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        LongSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new long[]{ -1, 1, 2, 3, 5}, values);
    }
    
    @Test
    public void testQuicksortDesc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        LongSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new long[]{5, 3, 2, 1, -1}, values);
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
    
}
