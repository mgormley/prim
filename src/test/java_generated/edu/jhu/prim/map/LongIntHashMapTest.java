package edu.jhu.prim.map;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toLong;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.Primitives;

public class LongIntHashMapTest {

    @Test
    public void testPowersOf2() {
        LongIntHashMap map = new LongIntHashMap();
        int start = 0;
        int end = Primitives.LONG_NUM_BITS;
        for (int i=start; i<end; i++) {
            long key = toLong(2) << i;
            System.out.print(key + " ");
            map.put(key, i);  
        }
        assertEquals(end - start, map.size());
        for (int i=start; i<end; i++) {
            long key = toLong(2) << i;
            assertEquals(i, map.get(key));  
        }
        System.out.println("");
    }

    @Test
    public void testOrderedUsage() {
        LongIntMap map = new LongIntHashMap();
        map.put(1, toInt(11));
        map.put(2, toInt(22));
        map.put(3, toInt(33));
        
        assertEquals(11, toInt(map.get(1)));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(33, toInt(map.get(3)));
    }
    
    @Test
    public void testNormalUsage() {
        LongIntMap map = new LongIntHashMap();
        map.put(2, toInt(22));
        map.put(1, toInt(11));
        map.put(3, toInt(33));
        map.put(-1, toInt(-11));
        map.put(8, toInt(88));
        map.put(6, toInt(66));

        assertEquals(33, toInt(map.get(3)));        
        assertEquals(11, toInt(map.get(1)));
        assertEquals(-11, toInt(map.get(-1)));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(88, toInt(map.get(8)));
        assertEquals(66, toInt(map.get(6)));
        
        // Clear the map.
        map.clear();
        
        map.put(3, toInt(33));
        map.put(2, toInt(22));
        map.put(1, toInt(11));
        
        assertEquals(22, toInt(map.get(2)));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(33, toInt(map.get(3)));
    }


    @Test
    public void testRemove() {
        // First element.
        LongIntMap map = new LongIntHashMap();
        map.put(2, toInt(22));
        map.put(1, toInt(11));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(11, toInt(map.get(1)));
        
        map.remove(1);
        assertEquals(false, map.contains(1));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(1, map.size());
        
        // Middle element.
        map = new LongIntHashMap();
        map.put(2, toInt(22));
        map.put(3, toInt(33));
        map.put(1, toInt(11));
        assertEquals(22, toInt(map.get(2)));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(33, toInt(map.get(3)));        
        
        map.remove(2);
        assertEquals(false, map.contains(2));
        assertEquals(11, toInt(map.get(1)));
        assertEquals(33, toInt(map.get(3)));        
        assertEquals(2, map.size());
        
        // Last element.
        map = new LongIntHashMap();
        map.put(2, toInt(22));
        map.put(3, toInt(33));
        map.put(1, toInt(11));
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
        LongIntMap map = new LongIntHashMap();

        try {
            map.get(2);
        } catch(Exception e) {
            // pass
        }
        map.put(3, toInt(33));
        try {
            map.get(-3);
        } catch(Exception e) {
            // pass
        }
    }

    @Test
    public void testIterator() {
        LongIntHashMap map = new LongIntHashMap();
        map.put(2, toInt(22));
        map.put(1, toInt(11));
        
        LongIntEntry cur;
        Iterator<LongIntEntry> iter = map.iterator();
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

    @Test
    public void testEquals() throws Exception {
        LongIntHashMap m1 = new LongIntHashMap();
        LongIntHashMap m2 = new LongIntHashMap();
        assertTrue(m1.equals(m2));
        
        m1.put(2, toInt(22));
        m1.put(1, toInt(11));
        assertFalse(m1.equals(m2));
        
        m2.put(1, toInt(11));
        m2.put(2, toInt(22));
        assertTrue(m1.equals(m2));
        
        m2.put(3, toInt(33));
        assertFalse(m1.equals(m2));
        
        m1.put(3, toInt(33));
        assertTrue(m1.equals(m2));
    }

    @Test
    public void testHashCode() throws Exception {
        LongIntHashMap m1 = new LongIntHashMap();
        LongIntHashMap m2 = new LongIntHashMap();
        assertEquals(m1.hashCode(), m2.hashCode());
        
        m1.put(2, toInt(22));
        m1.put(1, toInt(11));
        m2.put(1, toInt(11));
        m2.put(2, toInt(22));
        assertEquals(m1.hashCode(), m2.hashCode());
        
        m2.put(3, toInt(33));
        m1.put(3, toInt(33));
        assertEquals(m1.hashCode(), m2.hashCode());
    }
    
}
