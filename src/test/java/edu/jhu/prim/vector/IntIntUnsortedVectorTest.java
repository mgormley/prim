package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.map.IntIntEntry;

public class IntIntUnsortedVectorTest extends AbstractIntIntVectorTest {

    @Test
    public void testIterator() {
        IntIntUnsortedVector v2 = new IntIntUnsortedVector();
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));

        IntIntEntry e;
        Iterator<IntIntEntry> iter = v2.iterator();
        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(1, toInt(e.index()));
        assertEquals(11, toInt(e.get()));

        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(3, toInt(e.index()));
        assertEquals(33, toInt(e.get()));

        // Zeros are never explicitly added.
        //        assertTrue(iter.hasNext());
        //        e = iter.next();
        //        assertEquals(4, toInt(e.index()));
        //        assertEquals(0, toInt(e.get()));

        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(5, toInt(e.index()));
        assertEquals(55, toInt(e.get()));

        assertFalse(iter.hasNext());                
    }
    
    protected IntIntVector getIntIntVector() {
        return new IntIntUnsortedVector();
    }
    
}    
