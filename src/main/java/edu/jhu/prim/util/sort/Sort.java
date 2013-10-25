package edu.jhu.prim.util.sort;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.util.Utilities;
import edu.jhu.prim.util.math.Vectors;

public class Sort {

    public Sort() {
        // private constructor
    }

    public static <T extends Comparable<T>> void diffSortedLists(List<T> list1, List<T> list2, List<T> newList) {
        int i=0; 
        int j=0;
        while(i < list1.size() && j < list2.size()) {
            T e1 = list1.get(i);
            T e2 = list2.get(j);
            int diff = e1.compareTo(e2);
            if (diff == 0) {
                // Elements are equal: discard both e1 and e2.
                i++;
                j++;
            } else if (diff < 0) {
                // e1 is less than e2, so e1 must not be in list2: add e1 this round.
                newList.add(e1);
                i++;
            } else {
                // e2 is less than e1, so we won't encounter it again in list1: discard e2 this round.
                j++;
            }
        }

        assert (!(i < list1.size() && j < list2.size()));

        // If we didn't get all the way through list1, add all the remaining elements.
        for (; i < list1.size(); i++) {
            newList.add(list1.get(i));
        }
    }

    public static <T extends Comparable<T>> ArrayList<T> getDiffOfSortedLists(List<T> list1,
            List<T> list2) {
        ArrayList<T> newList = new ArrayList<T>();
        diffSortedLists(list1, list2, newList);
        return newList;
    }
    
    public static <T extends Comparable<T>> void mergeSortedLists(List<T> list1, List<T> list2, List<T> newList) {
        int i=0; 
        int j=0;
        while(i < list1.size() && j < list2.size()) {
            T e1 = list1.get(i);
            T e2 = list2.get(j);
            int diff = e1.compareTo(e2);
            if (diff == 0) {
                // Elements are equal. Only add one.
                newList.add(e1);
                i++;
                j++;
            } else if (diff < 0) {
                // e1 is less than e2, so only add e1 this round.
                newList.add(e1);
                i++;
            } else {
                // e2 is less than e1, so only add e2 this round.
                newList.add(e2);
                j++;
            }
        }

        // If there is a list that we didn't get all the way through, add all
        // the remaining elements. There will never be more than one such list. 
        assert (!(i < list1.size() && j < list2.size()));
        for (; i < list1.size(); i++) {
            newList.add(list1.get(i));
        }
        for (; j < list2.size(); j++) {
            newList.add(list2.get(j));
        }
    }

    public static <T extends Comparable<T>> ArrayList<T> getMergedList(List<T> list1,
            List<T> list2) {
        ArrayList<T> newList = new ArrayList<T>();
        mergeSortedLists(list1, list2, newList);
        return newList;
    }

    public static int[] getMergedSortedArray(int[] list1, int[] list2) {
        IntArrayList newList = new IntArrayList();
        int i=0; 
        int j=0;
        while(i < list1.length && j < list2.length) {
            int e1 = list1[i];
            int e2 = list2[j];
            int diff = e1 - e2;
            if (diff == 0) {
                // Elements are equal. Only add one.
                newList.add(e1);
                i++;
                j++;
            } else if (diff < 0) {
                // e1 is less than e2, so only add e1 this round.
                newList.add(e1);
                i++;
            } else {
                // e2 is less than e1, so only add e2 this round.
                newList.add(e2);
                j++;
            }
        }

        // If there is a list that we didn't get all the way through, add all
        // the remaining elements. There will never be more than one such list. 
        assert (!(i < list1.length && j < list2.length));
        for (; i < list1.length; i++) {
            newList.add(list1[i]);
        }
        for (; j < list2.length; j++) {
            newList.add(list2[j]);
        }
        return newList.toNativeArray();
    }

}
