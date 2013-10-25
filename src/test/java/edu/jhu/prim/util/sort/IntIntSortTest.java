package edu.jhu.prim.util.sort;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.util.JUnitUtils;
import edu.jhu.prim.util.sort.Sort;

public class IntIntSortTest {
        
    /* ---------- Ints and Ints --------------*/
    
    @Test
    public void testIntIntSortValuesAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testIntIntSortValuesDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testIntIntSortValuesInfinitiesAsc() {
        int[] values = new int[]{ 1, Integer.MAX_VALUE, 2, -1, Integer.MIN_VALUE, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 1, 2, 5, Integer.MAX_VALUE}, values);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testIntIntSortValuesInfinitiesDesc() {
        int[] values = new int[]{ 1, Integer.MAX_VALUE, 2, -1, Integer.MIN_VALUE, 5};
        int[] index = IntIntSort.getIntIndexArray(values);
        IntIntSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{Integer.MAX_VALUE,  5, 2, 1, -1, Integer.MIN_VALUE}, values);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testIntIntSortIndexAsc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntIntSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testIntIntSortIndexDesc() {
        int[] values = new int[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntIntSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new int[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
    
}
