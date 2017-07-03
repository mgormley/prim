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
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.map.IntIntEntry;
import edu.jhu.prim.util.Lambda.FnIntIntToInt;
import edu.jhu.prim.util.Lambda.FnIntIntToVoid;

public abstract class AbstractIntIntVectorTest {

    public AbstractIntIntVectorTest() {
        super();
    }

    @Test
    public void testDotProduct() {
        IntIntVector v1 = getIntIntVector();
        IntIntVector v2 = getIntIntVector();
        v1.set(4, toInt(5308));
        v1.set(49, toInt(23));
        v1.set(32, toInt(22));
        v1.set(23, toInt(10));
        
        v2.set(3, toInt(204));
        v2.set(2, toInt(11));
        v2.set(4, toInt(11));
        v2.set(23, toInt(24));
        v2.set(10, toInt(0001));
        v2.set(52, toInt(11));
        v2.set(49, toInt(7));
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }

    @Test
    public void testAdd() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(22));
        
        v1.add(3, toInt(33));
        v1.add(1, toInt(11));
        v1.add(2, toInt(22));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }

    @Test
    public void testScale() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(22));
    
        v1.scale(toInt(2));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }
    
    @Test
    public void testAddAll() {        
        testAddAllHelper(getIntIntVector(), new IntIntSortedVector());        
        testAddAllHelper(getIntIntVector(), new IntIntHashVector());
        testAddAllHelper(getIntIntVector(), new IntIntDenseVector());
    }

    private void testAddAllHelper(IntIntVector v1, IntIntVector v2) {
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        v1.add(v2);

        assertEquals(22, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(55, toInt(v1.get(5)));
    }
    
    @Test
    public void testSubtractAll() {        
        testSubtractAllHelper(getIntIntVector(), new IntIntSortedVector());        
        testSubtractAllHelper(getIntIntVector(), new IntIntHashVector());
        testSubtractAllHelper(getIntIntVector(), new IntIntDenseVector());
    }

    private void testSubtractAllHelper(IntIntVector v1, IntIntVector v2) {
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        v1.subtract(v2);

        assertEquals(0, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(-33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(-55, toInt(v1.get(5)));        
    }
    
    @Test
    public void testProductAll() {        
        testProductAllHelper(getIntIntVector(), new IntIntSortedVector());        
        testProductAllHelper(getIntIntVector(), new IntIntHashVector());
        testProductAllHelper(getIntIntVector(), new IntIntDenseVector());
    }

    private void testProductAllHelper(IntIntVector v1, IntIntVector v2) {
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(2));
        v2.set(5, toInt(55));
        
        v1.product(v2);

        assertEquals(11*11, toInt(v1.get(1)));
        assertEquals(22*0, toInt(v1.get(2)));
        assertEquals(33*0, toInt(v1.get(3)));
        assertEquals(44*2, toInt(v1.get(4)));
        assertEquals(55*0, toInt(v1.get(5)));        
    }

    @Test
    public void testDimension() {
        IntIntVector v1 = getIntIntVector();
        assertEquals(0, v1.getNumImplicitEntries());
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        assertEquals(5, v1.getNumImplicitEntries());
    }
    
    @Test
    public void testGetSum() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(22));
        
        assertEquals(11+33+22, v1.getSum());
    }
        
    @Test
    public void testGetMax() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(22));
        v1.set(5, toInt(-33));

        assertEquals(33, v1.getMax());
    }

    @Test
    public void testGetArgmax() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(22));
        v1.set(5, toInt(-33));
        
        assertEquals(3, v1.getArgmax());
    }
    
    @Test
    public void testGetMin() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(22));
        v1.set(5, toInt(-33));

        assertEquals(-33, v1.getMin());
    }

    @Test
    public void testGetArgmin() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(22));
        v1.set(5, toInt(-33));
        
        assertEquals(5, v1.getArgmin());
    }
    
    @Test
    public void testGetL2Norm() {
        // TODO write some code to search for perfect squares which are the sum
        // of other perfect squares (so this test has more cases and still works
        // for the integer-valued vector variants).
        IntIntVector v1 = getIntIntVector();
        v1.set(3, toInt(-4));
        v1.set(1, toInt(3));
        
        assertEquals(5, (int) v1.getL2Norm());
    }
    
    @Test
    public void testGetInfNorm() {
        IntIntVector v1 = getIntIntVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(33));
        v1.set(2, toInt(-22));
        v1.set(5, toInt(-55));
        
        assertEquals(55, v1.getInfNorm());
    }

    @Test
    public void testIterate() {
        IntIntVector v2 = getIntIntVector();
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));
        v2.add(3, toInt(0));

        final IntArrayList idxs = new IntArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnIntIntToVoid() {           
            @Override
            public void call(int idx, int val) {
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
        IntIntVector v2 = getIntIntVector();
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));
        v2.add(3, toInt(0));

        final IntArrayList idxs = new IntArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnIntIntToInt() {           
            @Override
            public int call(int idx, int val) {
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
    
    // TODO: Add this test once we implement iterator in IntIntDenseVector.
    //
    //  @Test
    //  public void testIterator() {
    //      IntIntVector v2 = getIntIntVector();
    //      
    //      v2.set(1, toInt(11));
    //      v2.set(3, toInt(33));
    //      v2.set(4, toInt(0));
    //      v2.set(5, toInt(55));
    //
    //      IntIntEntry e;
    //      Iterator<IntIntEntry> iter = v2.iterator();
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
    
    protected abstract IntIntVector getIntIntVector();

}