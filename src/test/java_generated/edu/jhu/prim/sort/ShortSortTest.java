package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.util.JUnitUtils;

public class ShortSortTest {
    
    /* ---------- Shorts only --------------*/
    
    @Test
    public void testQuicksortAsc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        ShortSort.sortAsc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new short[]{ -1, 1, 2, 3, 5}, values);
    }
    
    @Test
    public void testQuicksortDesc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        ShortSort.sortDesc(values);
        System.out.println(Arrays.toString(values));
        JUnitUtils.assertArrayEquals(new short[]{5, 3, 2, 1, -1}, values);
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
    
}
