package edu.jhu.prim.map;

import static edu.jhu.prim.Primitives.toFloat;
import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.Primitives;

public class IntFloatHashMapTest {

    @Test
    public void testPowersOf2() {
        IntFloatHashMap map = new IntFloatHashMap();
        int start = 0;
        int end = Primitives.INT_NUM_BITS;
        for (int i=start; i<end; i++) {
            int key = toInt(2) << i;
            System.out.print(key + " ");
            map.put(key, i);  
        }
        assertEquals(end - start, map.size());
        for (int i=start; i<end; i++) {
            int key = toInt(2) << i;
            assertEquals(i, map.get(key), 1e-13);  
        }
        System.out.println("");
    }

    @Test
    public void testOrderedUsage() {
        IntFloatMap map = new IntFloatHashMap();
        map.put(1, toFloat(11));
        map.put(2, toFloat(22));
        map.put(3, toFloat(33));
        
        assertEquals(11, toInt(map.get(1)));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(33, toInt(map.get(3)));
    }
    
    @Test
    public void testNormalUsage() {
        IntFloatMap map = new IntFloatHashMap();
        map.put(2, toFloat(22));
        map.put(1, toFloat(11));
        map.put(3, toFloat(33));
        map.put(-1, toFloat(-11));
        map.put(8, toFloat(88));
        map.put(6, toFloat(66));

        assertEquals(33, toInt(map.get(3)));        
        assertEquals(11, toInt(map.get(1)));
        assertEquals(-11, toInt(map.get(-1)));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(88, toInt(map.get(8)));
        assertEquals(66, toInt(map.get(6)));
        
        // Clear the map.
        map.clear();
        
        map.put(3, toFloat(33));
        map.put(2, toFloat(22));
        map.put(1, toFloat(11));
        
        assertEquals(22, toInt(map.get(2)));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(33, toInt(map.get(3)));
    }


    @Test
    public void testRemove() {
        // First element.
        IntFloatMap map = new IntFloatHashMap();
        map.put(2, toFloat(22));
        map.put(1, toFloat(11));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(11, toInt(map.get(1)));
        
        map.remove(1);
        assertEquals(false, map.contains(1));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(1, map.size());
        
        // Middle element.
        map = new IntFloatHashMap();
        map.put(2, toFloat(22));
        map.put(3, toFloat(33));
        map.put(1, toFloat(11));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(33, toInt(map.get(3)));        
        
        map.remove(2);
        assertEquals(false, map.contains(2));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(33, toInt(map.get(3)));        
        assertEquals(2, map.size());
        
        // Last element.
        map = new IntFloatHashMap();
        map.put(2, toFloat(22));
        map.put(3, toFloat(33));
        map.put(1, toFloat(11));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(33, toInt(map.get(3)));        
        
        map.remove(3);
        assertEquals(false, map.contains(3));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(22, toInt(map.get(2)));        
        assertEquals(2, map.size());
    }

    @Test
    public void testBadGets() {
        IntFloatMap map = new IntFloatHashMap();

        try {
            map.get(2);
        } catch(Exception e) {
            // pass
        }
        map.put(3, toFloat(33));
        try {
            map.get(-3);
        } catch(Exception e) {
            // pass
        }
    }

    @Test
    public void testIterator() {
        IntFloatHashMap map = new IntFloatHashMap();
        map.put(2, toFloat(22));
        map.put(1, toFloat(11));
        
        IntFloatEntry cur;
        Iterator<IntFloatEntry> iter = map.iterator();
        assertEquals(true, iter.hasNext()); 
        assertEquals(true, iter.hasNext()); 
        cur = iter.next();
        assertEquals(1, cur.index()); 
        assertEquals(11, toInt(cur.get())); 
        assertEquals(true, iter.hasNext()); 
        cur = iter.next();
        assertEquals(2, cur.index()); 
        assertEquals(22, toInt(cur.get())); 
        assertEquals(false, iter.hasNext());
    }
    
}
