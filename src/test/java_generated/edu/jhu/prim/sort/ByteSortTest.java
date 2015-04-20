package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import edu.jhu.prim.util.ByteJUnitUtils;

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
    
}
