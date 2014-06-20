package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.util.JUnitUtils;

public class IntSortTest {
    
    /* ---------- Ints only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        IntSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new int[]{ -1, 1, 2, 3, 5}, values);
    }
    
    @Test
    public void testQuicksortDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        IntSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new int[]{5, 3, 2, 1, -1}, values);
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
    
}
