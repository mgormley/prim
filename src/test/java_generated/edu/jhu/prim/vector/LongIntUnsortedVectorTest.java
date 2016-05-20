package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.map.LongIntEntry;
import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.Lambda.FnLongIntToVoid;

public class LongIntUnsortedVectorTest extends AbstractLongIntVectorTest {

    @Override
    @Test
    public void testProductAll() {        
        // The .product method isn't implemented. Just pass.
        System.out.println("SKIPPING TEST: LongIntUnsortedVectorTest.testProductAll()");
    }
    
    @Test
    public void testIterator() {
        LongIntUnsortedVector v2 = new LongIntUnsortedVector();
        
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
        LongIntVector v2 = getLongIntVector();
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));
        // TODO: v2.add(3, toInt(0));

        final LongArrayList idxs = new LongArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnLongIntToVoid() {           
            @Override
            public void call(long idx, int val) {
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
        LongIntVector v2 = getLongIntVector();
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));
        // TODO: v2.add(3, toInt(0));

        final LongArrayList idxs = new LongArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnLongIntToInt() {           
            @Override
            public int call(long idx, int val) {
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
        LongIntUnsortedVector v2 = new LongIntUnsortedVector();        
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));

        LongIntEntry e;
        Iterator<LongIntEntry> iter = v2.iteratorNoCompact();
        
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
        LongIntUnsortedVector v2 = new LongIntUnsortedVector();        
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));

        final LongArrayList idxs = new LongArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterateNoCompact(new FnLongIntToVoid() {           
            @Override
            public void call(long idx, int val) {
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
            }
        });
    }

    @Test
    public void testApplyNoCompact() {
        LongIntUnsortedVector v2 = new LongIntUnsortedVector();        
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));

        final LongArrayList idxs = new LongArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.applyNoCompact(new FnLongIntToInt() {           
            @Override
            public int call(long idx, int val) {
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
                return val;
            }
        });
    }

    protected LongIntVector getLongIntVector() {
        return new LongIntUnsortedVector();
    }
    
}    
