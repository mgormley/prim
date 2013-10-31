package edu.jhu.prim.sort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.util.JUnitUtils;

public class LongIntSortTest {
        
    /* ---------- Longs and Ints --------------*/
    
    @Test
    public void testLongIntSortValuesAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = LongIntSort.getLongIndexArray(values);
        LongIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new long[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testLongIntSortValuesDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = LongIntSort.getLongIndexArray(values);
        LongIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new long[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testLongIntSortValuesInfinitiesAsc() {
        int[] values = new int[]{ 1, Integer.MAX_VALUE, 2, -1, Integer.MIN_VALUE, 5};
        long[] index = LongIntSort.getLongIndexArray(values);
        LongIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 1, 2, 5, Integer.MAX_VALUE}, values);
        Assert.assertArrayEquals(new long[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testLongIntSortValuesInfinitiesDesc() {
        int[] values = new int[]{ 1, Integer.MAX_VALUE, 2, -1, Integer.MIN_VALUE, 5};
        long[] index = LongIntSort.getLongIndexArray(values);
        LongIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{Integer.MAX_VALUE,  5, 2, 1, -1, Integer.MIN_VALUE}, values);
        Assert.assertArrayEquals(new long[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testLongIntSortIndexAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = new long[] { 1, 4, 5, 8, 3};
        LongIntSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new long[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testLongIntSortIndexDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        long[] index = new long[] { 1, 4, 5, 8, 3};
        LongIntSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new long[]{ 8, 5, 4, 3, 1 }, index);
    }
    
}
