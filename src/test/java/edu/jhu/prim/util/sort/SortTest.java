package edu.jhu.prim.util.sort;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.jhu.prim.list.IntArrayList;

public class SortTest {
    
    @Test
    public void testMergeSortedLists() {
        ArrayList<Integer> l1 = new ArrayList<Integer>();
        ArrayList<Integer> l2 = new ArrayList<Integer>();
        // Add some fib nums
        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(5);
        l1.add(8);
        // Add odd numbers
        l2.add(3);
        l2.add(5);
        l2.add(7);
        l2.add(9);
        l2.add(11);
        
        ArrayList<Integer> l3 = Sort.getMergedList(l1, l2);
        Assert.assertArrayEquals(new Integer[]{ 1, 2, 3, 5, 7, 8, 9, 11 }, l3.toArray(new Integer[]{}));
    }
    
    @Test
    public void testMergeSortedArrays() {
        IntArrayList l1 = new IntArrayList();
        IntArrayList l2 = new IntArrayList();
        // Add some fib nums
        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(5);
        l1.add(8);
        // Add odd numbers
        l2.add(3);
        l2.add(5);
        l2.add(7);
        l2.add(9);
        l2.add(11);
        
        int[] l3 = Sort.getMergedSortedArray(l1.toNativeArray(), l2.toNativeArray());
        Assert.assertArrayEquals(new int[]{ 1, 2, 3, 5, 7, 8, 9, 11 }, l3);
    }

    @Test
    public void testDiffSortedLists1() {
        ArrayList<Integer> l1 = new ArrayList<Integer>();
        ArrayList<Integer> l2 = new ArrayList<Integer>();
        // Add some fib nums
        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(5);
        l1.add(8);
        // Add odd numbers
        l2.add(3);
        l2.add(5);
        l2.add(7);
        l2.add(9);
        l2.add(11);
        
        ArrayList<Integer> l3 = Sort.getDiffOfSortedLists(l1, l2);
        System.out.println(l3);
        Assert.assertArrayEquals(new Integer[]{ 1, 2, 8 }, l3.toArray(new Integer[]{}));
    }

    @Test
    public void testDiffSortedLists2() {
        ArrayList<Integer> l1 = new ArrayList<Integer>();
        ArrayList<Integer> l2 = new ArrayList<Integer>();
        // Add some fib nums
        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(5);
        l1.add(8);
        // Add odd numbers
        l2.add(1);
        l2.add(3);
        l2.add(7);
        l2.add(8);
        
        ArrayList<Integer> l3 = Sort.getDiffOfSortedLists(l1, l2);
        System.out.println(l3);
        Assert.assertArrayEquals(new Integer[]{ 2, 5 }, l3.toArray(new Integer[]{}));
    }
    
    @Test
    public void testDiffSortedLists3() {
        ArrayList<Integer> l1 = new ArrayList<Integer>();
        ArrayList<Integer> l2 = new ArrayList<Integer>();
        // Add some fib nums
        l1.add(1);
        l1.add(2);
        l1.add(3);
        // Add odd numbers
        l2.add(1);
        l2.add(2);
        l2.add(3);
        
        ArrayList<Integer> l3 = Sort.getDiffOfSortedLists(l1, l2);
        System.out.println(l3);
        Assert.assertArrayEquals(new Integer[]{ }, l3.toArray(new Integer[]{}));
    }
        
}
