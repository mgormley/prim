package edu.jhu.prim.sort;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.sort.IntDoubleSort;
import edu.jhu.prim.sort.Sort;
import edu.jhu.prim.util.JUnitUtils;

public class IntDoubleSortTest {
        
    /* ---------- Ints and Doubles --------------*/
    
    @Test
    public void testIntDoubleSortValuesAsc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ -1, 1, 2, 3, 5}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 3, 0, 2, 1, 4}, index);
    }
    
    @Test
    public void testIntDoubleSortValuesDesc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ 5, 3, 2, 1, -1}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 4, 1, 2, 0, 3}, index);
    }
    
    @Test
    public void testIntDoubleSortValuesInfinitiesAsc() {
        double[] values = new double[]{ 1, Double.POSITIVE_INFINITY, 2, -1, Double.NEGATIVE_INFINITY, 5};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesAsc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));

        JUnitUtils.assertArrayEquals(new double[]{Double.NEGATIVE_INFINITY, -1, 1, 2, 5, Double.POSITIVE_INFINITY}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 4, 3, 0, 2, 5, 1 }, index);
    }
    
    @Test
    public void testIntDoubleSortValuesInfinitiesDesc() {
        double[] values = new double[]{ 1, Double.POSITIVE_INFINITY, 2, -1, Double.NEGATIVE_INFINITY, 5};
        int[] index = IntDoubleSort.getIntIndexArray(values);
        IntDoubleSort.sortValuesDesc(values, index);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{Double.POSITIVE_INFINITY,  5, 2, 1, -1, Double.NEGATIVE_INFINITY}, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 1, 5, 2, 0, 3, 4 }, index);
    }    

    @Test
    public void testIntDoubleSortIndexAsc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntDoubleSort.sortIndexAsc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ 1, 5, 3, 2, -1 }, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 1, 3, 4, 5, 8 }, index);
    }

    @Test
    public void testIntDoubleSortIndexDesc() {
        double[] values = new double[]{ 1, 3, 2, -1, 5};
        int[] index = new int[] { 1, 4, 5, 8, 3};
        IntDoubleSort.sortIndexDesc(index, values);
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(index));
        
        JUnitUtils.assertArrayEquals(new double[]{ -1, 2, 3, 5, 1 }, values, 1e-13);
        Assert.assertArrayEquals(new int[]{ 8, 5, 4, 3, 1 }, index);
    }
    
}
