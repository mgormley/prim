package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toDouble;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.DoubleArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.map.IntDoubleEntry;
import edu.jhu.prim.util.Lambda.FnIntDoubleToDouble;
import edu.jhu.prim.util.Lambda.FnIntDoubleToVoid;

public class IntDoubleUnsortedVectorTest extends AbstractIntDoubleVectorTest {

    @Override
    @Test
    public void testProductAll() {        
        // The .product method isn't implemented. Just pass.
        System.out.println("SKIPPING TEST: IntDoubleUnsortedVectorTest.testProductAll()");
    }
    
    @Test
    public void testIterator() {
        IntDoubleUnsortedVector v2 = new IntDoubleUnsortedVector();
        
        v2.set(1, toDouble(11));
        v2.set(3, toDouble(33));
        v2.set(4, toDouble(0));
        v2.set(5, toDouble(55));

        IntDoubleEntry e;
        Iterator<IntDoubleEntry> iter = v2.iterator();
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
        IntDoubleVector v2 = getIntDoubleVector();
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));
        // TODO: v2.add(3, toDouble(0));

        final IntArrayList idxs = new IntArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnIntDoubleToVoid() {           
            @Override
            public void call(int idx, double val) {
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
        IntDoubleVector v2 = getIntDoubleVector();
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));
        // TODO: v2.add(3, toDouble(0));

        final IntArrayList idxs = new IntArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        // TODO: idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnIntDoubleToDouble() {           
            @Override
            public double call(int idx, double val) {
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
        IntDoubleUnsortedVector v2 = new IntDoubleUnsortedVector();        
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));

        IntDoubleEntry e;
        Iterator<IntDoubleEntry> iter = v2.iteratorNoCompact();
        
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
        IntDoubleUnsortedVector v2 = new IntDoubleUnsortedVector();        
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));

        final IntArrayList idxs = new IntArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterateNoCompact(new FnIntDoubleToVoid() {           
            @Override
            public void call(int idx, double val) {
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
            }
        });
    }

    @Test
    public void testApplyNoCompact() {
        IntDoubleUnsortedVector v2 = new IntDoubleUnsortedVector();        
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));

        final IntArrayList idxs = new IntArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(1); vals.add(11);
        idxs.add(2); vals.add(22);
        idxs.add(1); vals.add(111);
        
        final MutableInt i = new MutableInt(0);
        
        v2.applyNoCompact(new FnIntDoubleToDouble() {           
            @Override
            public double call(int idx, double val) {
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
                return val;
            }
        });
    }

    protected IntDoubleVector getIntDoubleVector() {
        return new IntDoubleUnsortedVector();
    }
    
}    
