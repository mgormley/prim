package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.map.LongIntEntry;

public class LongIntSortedVectorTest extends AbstractLongIntVectorTest {

    @Test
    public void testGetWithNoZeroValues() {
        LongIntSortedVector v1 = new LongIntSortedVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(0));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        v1.set(5, toInt(0));
        
		assertEquals(11, toInt(v1.get(1)));
		assertEquals(22, toInt(v1.get(2)));
		assertEquals(0, toInt(v1.get(3)));
		assertEquals(44, toInt(v1.get(4)));
		assertEquals(0, toInt(v1.get(5)));
		assertEquals(5, v1.getUsed());
		
        LongIntSortedVector v2 = LongIntSortedVector.getWithNoZeroValues(v1);
        assertEquals(3, v2.getUsed());
		assertEquals(11, toInt(v2.get(1)));
		assertEquals(22, toInt(v2.get(2)));
		assertEquals(44, toInt(v2.get(4)));
    }
    
    @Test
    public void testHadamardProduct() {
        LongIntSortedVector v1 = new LongIntSortedVector();
        LongIntSortedVector v2 = new LongIntSortedVector();
        
        v1.set(1, toInt(11));
        v1.set(3, toInt(0));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        v1.set(5, toInt(0));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(0));
        v2.set(2, toInt(22));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        LongIntSortedVector v3 = v1.getProd(v2);

		assertEquals(11*11, toInt(v3.get(1)));
		assertEquals(22*22, toInt(v3.get(2)));
		assertEquals(0, toInt(v3.get(3)));
		assertEquals(0, toInt(v3.get(4)));
		assertEquals(0, toInt(v3.get(5)));
    }
    
    @Test
    public void testSetAll() {
        LongIntSortedVector v1 = new LongIntSortedVector();
        LongIntSortedVector v2 = new LongIntSortedVector();
        
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        v1.set(v2);

        assertEquals(11, toInt(v1.get(1)));
        assertEquals(0, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals(0, toInt(v1.get(4)));
        assertEquals(55, toInt(v1.get(5)));
    }
    
    @Test
    public void testGetElementwiseSum() {
        LongIntSortedVector v1 = new LongIntSortedVector();
        LongIntSortedVector v2 = new LongIntSortedVector();
        
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        LongIntSortedVector v3 = v1.getSum(v2);

        assertEquals(22, toInt(v3.get(1)));
        assertEquals(22, toInt(v3.get(2)));
        assertEquals(33, toInt(v3.get(3)));
        assertEquals(44, toInt(v3.get(4)));
        assertEquals(55, toInt(v3.get(5)));        
    }
    
    @Test
    public void testIterator() {
        LongIntSortedVector v2 = new LongIntSortedVector();
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));

        LongIntEntry e;
        Iterator<LongIntEntry> iter = v2.iterator();
        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(1, toInt(e.index()));
        assertEquals(11, toInt(e.get()));

        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(3, toInt(e.index()));
        assertEquals(33, toInt(e.get()));

        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(4, toInt(e.index()));
        assertEquals(0, toInt(e.get()));

        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(5, toInt(e.index()));
        assertEquals(55, toInt(e.get()));

        assertFalse(iter.hasNext());                
    }

    protected LongIntSortedVector getLongIntVector() {
        return new LongIntSortedVector();
    }
    
}    
