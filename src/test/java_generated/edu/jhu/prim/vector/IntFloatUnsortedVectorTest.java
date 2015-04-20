package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toFloat;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.map.IntFloatEntry;

public class IntFloatUnsortedVectorTest extends AbstractIntFloatVectorTest {

    @Test
    public void testIterator() {
        IntFloatUnsortedVector v2 = new IntFloatUnsortedVector();
        
        v2.set(1, toFloat(11));
        v2.set(3, toFloat(33));
        v2.set(4, toFloat(0));
        v2.set(5, toFloat(55));

        IntFloatEntry e;
        Iterator<IntFloatEntry> iter = v2.iterator();
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
    
    protected IntFloatVector getIntFloatVector() {
        return new IntFloatUnsortedVector();
    }
    
}    
