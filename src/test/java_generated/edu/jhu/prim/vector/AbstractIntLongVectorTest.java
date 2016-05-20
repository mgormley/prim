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

public abstract class AbstractIntLongVectorTest {

    public AbstractIntLongVectorTest() {
        super();
    }

    @Test
    public void testDotProduct() {
        IntLongVector v1 = getIntLongVector();
        IntLongVector v2 = getIntLongVector();
        v1.set(4, toLong(5308));
        v1.set(49, toLong(23));
        v1.set(32, toLong(22));
        v1.set(23, toLong(10));
        
        v2.set(3, toLong(204));
        v2.set(2, toLong(11));
        v2.set(4, toLong(11));
        v2.set(23, toLong(24));
        v2.set(10, toLong(0001));
        v2.set(52, toLong(11));
        v2.set(49, toLong(7));
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }

    @Test
    public void testAdd() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(22));
        
        v1.add(3, toLong(33));
        v1.add(1, toLong(11));
        v1.add(2, toLong(22));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }

    @Test
    public void testScale() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(22));
    
        v1.scale(toLong(2));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }
    
    @Test
    public void testAddAll() {        
        testAddAllHelper(getIntLongVector(), new IntLongSortedVector());        
        testAddAllHelper(getIntLongVector(), new IntLongHashVector());
        testAddAllHelper(getIntLongVector(), new IntLongDenseVector());
    }

    private void testAddAllHelper(IntLongVector v1, IntLongVector v2) {
        v1.set(1, toLong(11));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        
        v2.set(1, toLong(11));
        v2.set(3, toLong(33));
        v2.set(4, toLong(0));
        v2.set(5, toLong(55));
        
        v1.add(v2);

        assertEquals(22, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(55, toInt(v1.get(5)));
    }
    
    @Test
    public void testSubtractAll() {        
        testSubtractAllHelper(getIntLongVector(), new IntLongSortedVector());        
        testSubtractAllHelper(getIntLongVector(), new IntLongHashVector());
        testSubtractAllHelper(getIntLongVector(), new IntLongDenseVector());
    }

    private void testSubtractAllHelper(IntLongVector v1, IntLongVector v2) {
        v1.set(1, toLong(11));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        
        v2.set(1, toLong(11));
        v2.set(3, toLong(33));
        v2.set(4, toLong(0));
        v2.set(5, toLong(55));
        
        v1.subtract(v2);

        assertEquals(0, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(-33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(-55, toInt(v1.get(5)));        
    }
    
    @Test
    public void testProductAll() {        
        testProductAllHelper(getIntLongVector(), new IntLongSortedVector());        
        testProductAllHelper(getIntLongVector(), new IntLongHashVector());
        testProductAllHelper(getIntLongVector(), new IntLongDenseVector());
    }

    private void testProductAllHelper(IntLongVector v1, IntLongVector v2) {
        v1.set(1, toLong(11));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        
        v2.set(1, toLong(11));
        v2.set(3, toLong(33));
        v2.set(4, toLong(2));
        v2.set(5, toLong(55));
        
        v1.product(v2);

        assertEquals(11*11, toInt(v1.get(1)));
        assertEquals(22*0, toInt(v1.get(2)));
        assertEquals(33*0, toInt(v1.get(3)));
        assertEquals(44*2, toInt(v1.get(4)));
        assertEquals(55*0, toInt(v1.get(5)));        
    }

    @Test
    public void testDimension() {
        IntLongVector v1 = getIntLongVector();
        assertEquals(0, v1.getNumImplicitEntries());
        v1.set(1, toLong(11));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        assertEquals(5, v1.getNumImplicitEntries());
    }
    
    @Test
    public void testGetSum() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(22));
        
        assertEquals(11+33+22, v1.getSum());
    }
        
    @Test
    public void testGetMax() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(22));
        v1.set(5, toLong(-33));

        assertEquals(33, v1.getMax());
    }

    @Test
    public void testGetArgmax() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(22));
        v1.set(5, toLong(-33));
        
        assertEquals(3, v1.getArgmax());
    }
    
    @Test
    public void testGetMin() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(22));
        v1.set(5, toLong(-33));

        assertEquals(-33, v1.getMin());
    }

    @Test
    public void testGetArgmin() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(22));
        v1.set(5, toLong(-33));
        
        assertEquals(5, v1.getArgmin());
    }
    
    @Test
    public void testGetL2Norm() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(-22));
        v1.set(5, toLong(-55));
        
        assertEquals(11*11 + 33*33 + 22*22 + 55*55, v1.getL2Norm());
    }
    
    @Test
    public void testGetInfNorm() {
        IntLongVector v1 = getIntLongVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(33));
        v1.set(2, toLong(-22));
        v1.set(5, toLong(-55));
        
        assertEquals(55, v1.getInfNorm());
    }

    @Test
    public void testIterate() {
        IntLongVector v2 = getIntLongVector();
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));
        v2.add(3, toLong(0));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);
        
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
        v2.add(3, toLong(0));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);

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
    
    // TODO: Add this test once we implement iterator in IntLongDenseVector.
    //
    //  @Test
    //  public void testIterator() {
    //      IntLongVector v2 = getIntLongVector();
    //      
    //      v2.set(1, toLong(11));
    //      v2.set(3, toLong(33));
    //      v2.set(4, toLong(0));
    //      v2.set(5, toLong(55));
    //
    //      IntLongEntry e;
    //      Iterator<IntLongEntry> iter = v2.iterator();
    //      assertTrue(iter.hasNext());
    //      e = iter.next();
    //      assertEquals(1, toInt(e.index()));
    //      assertEquals(11, toInt(e.get()));
    //
    //      assertTrue(iter.hasNext());
    //      e = iter.next();
    //      assertEquals(3, toInt(e.index()));
    //      assertEquals(33, toInt(e.get()));
    //
    //      assertTrue(iter.hasNext());
    //      e = iter.next();
    //      assertEquals(4, toInt(e.index()));
    //      assertEquals(0, toInt(e.get()));
    //
    //      assertTrue(iter.hasNext());
    //      e = iter.next();
    //      assertEquals(5, toInt(e.index()));
    //      assertEquals(55, toInt(e.get()));
    //
    //      assertFalse(iter.hasNext());                
    //  }
    
    protected abstract IntLongVector getIntLongVector();

}