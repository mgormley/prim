package edu.jhu.prim.sort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.util.JUnitUtils;

public class IntShortSortTest {
        
    /* ---------- Ints and Shorts --------------*/
    
    @Test
    public void testIntShortSortValuesAsc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ -1, 1, 2, 3, 5}, values);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testIntShortSortValuesDesc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ 5, 3, 2, 1, -1}, values);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testIntShortSortValuesInfinitiesAsc() {
        short[] values = new short[]{ 1, 32767, 2, -1, -32768, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new short[]{-32768, -1, 1, 2, 5, 32767}, values);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testIntShortSortValuesInfinitiesDesc() {
        short[] values = new short[]{ 1, 32767, 2, -1, -32768, 5};
        int[] index = IntShortSort.getIntIndexArray(values);
        IntShortSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{32767,  5, 2, 1, -1, -32768}, values);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testIntShortSortIndexAsc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntShortSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ 1, 5, 3, 2, -1 }, values);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testIntShortSortIndexDesc() {
        short[] values = new short[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntShortSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new short[]{ -1, 2, 3, 5, 1 }, values);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
    
}
