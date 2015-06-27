package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toDouble;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.map.LongDoubleEntry;

public class LongDoubleUnsortedVectorTest extends AbstractLongDoubleVectorTest {

    @Override
    @Test
    public void testProductAll() {        
        // The .product method isn't implemented. Just pass.
        System.out.println("SKIPPING TEST: LongDoubleUnsortedVectorTest.testProductAll()");
    }
    
    @Test
    public void testIterator() {
        LongDoubleUnsortedVector v2 = new LongDoubleUnsortedVector();
        
        v2.set(1, toDouble(11));
        v2.set(3, toDouble(33));
        v2.set(4, toDouble(0));
        v2.set(5, toDouble(55));

        LongDoubleEntry e;
        Iterator<LongDoubleEntry> iter = v2.iterator();
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
    
    protected LongDoubleVector getLongDoubleVector() {
        return new LongDoubleUnsortedVector();
    }
    
}    
