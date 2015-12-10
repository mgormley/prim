package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toLong;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.map.IntLongEntry;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.Lambda.FnIntLongToVoid;

public class IntLongUnsortedVectorTest extends AbstractIntLongVectorTest {

    @Override
    @Test
    public void testProductAll() {        
        // The .product method isn't implemented. Just pass.
        System.out.println("SKIPPING TEST: IntLongUnsortedVectorTest.testProductAll()");
    }
    
    @Test
    public void testIterator() {
        IntLongUnsortedVector v2 = new IntLongUnsortedVector();
        
        v2.set(1, toLong(11));
        v2.set(3, toLong(33));
        v2.set(4, toLong(0));
        v2.set(5, toLong(55));

        IntLongEntry e;
        Iterator<IntLongEntry> iter = v2.iterator();
        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(1, toInt(e.index()));
        assertEquals(11, toInt(e.get()));

        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(3, toInt(e.index()));
        assertEquals(33, toInt(e.get()));

        // TODO: Zeros are never explicitly added.
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

    // TODO: This test assumes we're working with a sparse representation, so it would fail if used
    // for a dense vector. Is this a problem with the iterate/apply methods?
    @Test
    public void testIterate() {
        IntLongVector v2 = getIntLongVector();
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));
        // TODO: v2.add(3, toLong(0));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnIntLongToVoid() {           
            @Override
            public void call(int idx, long val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }

    @Test
    public void testApply() {
        IntLongVector v2 = getIntLongVector();
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));
        // TODO: v2.add(3, toLong(0));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnIntLongToLong() {           
            @Override
            public long call(int idx, long val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
                return val;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }
    
    @Test
    public void testIteratorNoCompact() {
        IntLongUnsortedVector v2 = new IntLongUnsortedVector();        
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));

        IntLongEntry e;
        Iterator<IntLongEntry> iter = v2.iteratorNoCompact();
        
        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(1, toInt(e.index()));
        assertEquals(11, toInt(e.get()));

        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(2, toInt(e.index()));
        assertEquals(22, toInt(e.get()));
        
        assertTrue(iter.hasNext());
        e = iter.next();
        assertEquals(1, toInt(e.index()));
        assertEquals(111, toInt(e.get()));

        assertFalse(iter.hasNext());
    }
    
    @Test
    public void testIterateNoCompact() {
        IntLongUnsortedVector v2 = new IntLongUnsortedVector();        
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterateNoCompact(new FnIntLongToVoid() {           
            @Override
            public void call(int idx, long val) {
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
            }
        });
    }

    @Test
    public void testApplyNoCompact() {
        IntLongUnsortedVector v2 = new IntLongUnsortedVector();        
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.applyNoCompact(new FnIntLongToLong() {           
            @Override
            public long call(int idx, long val) {
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
                return val;
            }
        });
    }

    protected IntLongVector getIntLongVector() {
        return new IntLongUnsortedVector();
    }
    
}    
