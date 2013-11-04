package edu.jhu.prim.sort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.util.JUnitUtils;

public class IntLongSortTest {
        
    /* ---------- Ints and Longs --------------*/
    
    @Test
    public void testIntLongSortValuesAsc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = IntLongSort.getIntIndexArray(values);
        IntLongSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new long[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testIntLongSortValuesDesc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = IntLongSort.getIntIndexArray(values);
        IntLongSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new long[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testIntLongSortValuesInfinitiesAsc() {
        long[] values = new long[]{ 1, 9223372036854775806l, 2, -1, -9223372036854775806l, 5};
        int[] index = IntLongSort.getIntIndexArray(values);
        IntLongSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new long[]{-9223372036854775806l, -1, 1, 2, 5, 9223372036854775806l}, values);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testIntLongSortValuesInfinitiesDesc() {
        long[] values = new long[]{ 1, 9223372036854775806l, 2, -1, -9223372036854775806l, 5};
        int[] index = IntLongSort.getIntIndexArray(values);
        IntLongSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new long[]{9223372036854775806l,  5, 2, 1, -1, -9223372036854775806l}, values);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testIntLongSortIndexAsc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntLongSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new long[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testIntLongSortIndexDesc() {
        long[] values = new long[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntLongSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new long[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
    
}
