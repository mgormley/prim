package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toFloat;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.FloatArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.map.IntFloatEntry;
import edu.jhu.prim.util.Lambda.FnIntFloatToFloat;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;

public class IntFloatUnsortedVectorTest extends AbstractIntFloatVectorTest {

    @Override
    @Test
    public void testProductAll() {        
        // The .product method isn't implemented. Just pass.
        System.out.println("SKIPPING TEST: IntFloatUnsortedVectorTest.testProductAll()");
    }
    
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
        IntFloatVector v2 = getIntFloatVector();
        v2.add(1, toFloat(11));
        v2.add(2, toFloat(22));
        v2.add(1, toFloat(111));
        // TODO: v2.add(3, toFloat(0));

        final IntArrayList idxs = new IntArrayList();
        final FloatArrayList vals = new FloatArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnIntFloatToVoid() {           
            @Override
            public void call(int idx, float val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }

    @Test
    public void testApply() {
        IntFloatVector v2 = getIntFloatVector();
        v2.add(1, toFloat(11));
        v2.add(2, toFloat(22));
        v2.add(1, toFloat(111));
        // TODO: v2.add(3, toFloat(0));

        final IntArrayList idxs = new IntArrayList();
        final FloatArrayList vals = new FloatArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnIntFloatToFloat() {           
            @Override
            public float call(int idx, float val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
                return val;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }
    
    @Test
    public void testIteratorNoCompact() {
        IntFloatUnsortedVector v2 = new IntFloatUnsortedVector();        
        v2.add(1, toFloat(11));
        v2.add(2, toFloat(22));
        v2.add(1, toFloat(111));

        IntFloatEntry e;
        Iterator<IntFloatEntry> iter = v2.iteratorNoCompact();
        
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
        IntFloatUnsortedVector v2 = new IntFloatUnsortedVector();        
        v2.add(1, toFloat(11));
        v2.add(2, toFloat(22));
        v2.add(1, toFloat(111));

        final IntArrayList idxs = new IntArrayList();
        final FloatArrayList vals = new FloatArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterateNoCompact(new FnIntFloatToVoid() {           
            @Override
            public void call(int idx, float val) {
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
            }
        });
    }

    @Test
    public void testApplyNoCompact() {
        IntFloatUnsortedVector v2 = new IntFloatUnsortedVector();        
        v2.add(1, toFloat(11));
        v2.add(2, toFloat(22));
        v2.add(1, toFloat(111));

        final IntArrayList idxs = new IntArrayList();
        final FloatArrayList vals = new FloatArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.applyNoCompact(new FnIntFloatToFloat() {           
            @Override
            public float call(int idx, float val) {
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
                return val;
            }
        });
    }

    protected IntFloatVector getIntFloatVector() {
        return new IntFloatUnsortedVector();
    }
    
}    
