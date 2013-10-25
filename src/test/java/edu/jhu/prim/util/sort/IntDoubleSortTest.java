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

public class IntDoubleSortTest {
    
    /* ---------- Ints and Doubles --------------*/
    
    @Test
    public void testSortValuesAsc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0, 5.0};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ -1.0, 1.0f, 2.0, 3.0f, 5.0}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testSortValuesDesc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0, 5.0};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ 5.0, 3.0, 2.0, 1.0, -1.0}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testSortValuesInfinitiesAsc() {
        double[] values = new double[]{ 1.0f, Double.POSITIVE_INFINITY, 2.0f, -1.0, Double.NEGATIVE_INFINITY, 5.0};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new double[]{Double.NEGATIVE_INFINITY, -1.0, 1.0, 2.0, 5.0, Double.POSITIVE_INFINITY}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testSortValuesInfinitiesDesc() {
        double[] values = new double[]{ 1.0f, Double.POSITIVE_INFINITY, 2.0f, -1.0, Double.NEGATIVE_INFINITY, 5.0};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{Double.POSITIVE_INFINITY,  5.0, 2.0, 1.0, -1.0, Double.NEGATIVE_INFINITY}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testSortIndexAsc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0, 5.0};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntDoubleSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ 1.0, 5.0, 3.0, 2.0, -1.0 }, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testSortIndexDesc() {
        double[] values = new double[]{ 1.0f, 3.0f, 2.0f, -1.0, 5.0};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntDoubleSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ -1.0, 2.0, 3.0, 5.0, 1.0 }, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
            
}
