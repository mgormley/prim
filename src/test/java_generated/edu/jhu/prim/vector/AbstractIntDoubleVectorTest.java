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

public abstract class AbstractIntDoubleVectorTest {

    public AbstractIntDoubleVectorTest() {
        super();
    }

    @Test
    public void testDotProduct() {
        IntDoubleVector v1 = getIntDoubleVector();
        IntDoubleVector v2 = getIntDoubleVector();
        v1.set(4, toDouble(5308));
        v1.set(49, toDouble(23));
        v1.set(32, toDouble(22));
        v1.set(23, toDouble(10));
        
        v2.set(3, toDouble(204));
        v2.set(2, toDouble(11));
        v2.set(4, toDouble(11));
        v2.set(23, toDouble(24));
        v2.set(10, toDouble(0001));
        v2.set(52, toDouble(11));
        v2.set(49, toDouble(7));
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }

    @Test
    public void testAdd() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(22));
        
        v1.add(3, toDouble(33));
        v1.add(1, toDouble(11));
        v1.add(2, toDouble(22));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }

    @Test
    public void testScale() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(22));
    
        v1.scale(toDouble(2));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }
    
    @Test
    public void testAddAll() {        
        testAddAllHelper(getIntDoubleVector(), new IntDoubleSortedVector());        
        testAddAllHelper(getIntDoubleVector(), new IntDoubleHashVector());
        testAddAllHelper(getIntDoubleVector(), new IntDoubleDenseVector());
    }

    private void testAddAllHelper(IntDoubleVector v1, IntDoubleVector v2) {
        v1.set(1, toDouble(11));
        v1.set(2, toDouble(22));
        v1.set(4, toDouble(44));
        
        v2.set(1, toDouble(11));
        v2.set(3, toDouble(33));
        v2.set(4, toDouble(0));
        v2.set(5, toDouble(55));
        
        v1.add(v2);

        assertEquals(22, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(55, toInt(v1.get(5)));
    }
    
    @Test
    public void testSubtractAll() {        
        testSubtractAllHelper(getIntDoubleVector(), new IntDoubleSortedVector());        
        testSubtractAllHelper(getIntDoubleVector(), new IntDoubleHashVector());
        testSubtractAllHelper(getIntDoubleVector(), new IntDoubleDenseVector());
    }

    private void testSubtractAllHelper(IntDoubleVector v1, IntDoubleVector v2) {
        v1.set(1, toDouble(11));
        v1.set(2, toDouble(22));
        v1.set(4, toDouble(44));
        
        v2.set(1, toDouble(11));
        v2.set(3, toDouble(33));
        v2.set(4, toDouble(0));
        v2.set(5, toDouble(55));
        
        v1.subtract(v2);

        assertEquals(0, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(-33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(-55, toInt(v1.get(5)));        
    }
    
    @Test
    public void testProductAll() {        
        testProductAllHelper(getIntDoubleVector(), new IntDoubleSortedVector());        
        testProductAllHelper(getIntDoubleVector(), new IntDoubleHashVector());
        testProductAllHelper(getIntDoubleVector(), new IntDoubleDenseVector());
    }

    private void testProductAllHelper(IntDoubleVector v1, IntDoubleVector v2) {
        v1.set(1, toDouble(11));
        v1.set(2, toDouble(22));
        v1.set(4, toDouble(44));
        
        v2.set(1, toDouble(11));
        v2.set(3, toDouble(33));
        v2.set(4, toDouble(2));
        v2.set(5, toDouble(55));
        
        v1.product(v2);

        assertEquals(11*11, toInt(v1.get(1)));
        assertEquals(22*0, toInt(v1.get(2)));
        assertEquals(33*0, toInt(v1.get(3)));
        assertEquals(44*2, toInt(v1.get(4)));
        assertEquals(55*0, toInt(v1.get(5)));        
    }

    @Test
    public void testDimension() {
        IntDoubleVector v1 = getIntDoubleVector();
        assertEquals(0, v1.getNumImplicitEntries());
        v1.set(1, toDouble(11));
        v1.set(2, toDouble(22));
        v1.set(4, toDouble(44));
        assertEquals(5, v1.getNumImplicitEntries());
    }
    
    @Test
    public void testGetSum() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(22));
        
        assertEquals(11+33+22, v1.getSum(), 1e-13);
    }
        
    @Test
    public void testGetMax() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(22));
        v1.set(5, toDouble(-33));

        assertEquals(33, v1.getMax(), 1e-13);
    }

    @Test
    public void testGetArgmax() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(22));
        v1.set(5, toDouble(-33));
        
        assertEquals(3, v1.getArgmax());
    }
    
    @Test
    public void testGetMin() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(22));
        v1.set(5, toDouble(-33));

        assertEquals(-33, v1.getMin(), 1e-13);
    }

    @Test
    public void testGetArgmin() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(22));
        v1.set(5, toDouble(-33));
        
        assertEquals(5, v1.getArgmin());
    }
    
    @Test
    public void testGetL2Norm() {
        // TODO write some code to search for perfect squares which are the sum
        // of other perfect squares (so this test has more cases and still works
        // for the integer-valued vector variants).
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(3, toDouble(-4));
        v1.set(1, toDouble(3));
        
        assertEquals(5, (double) v1.getL2Norm(), 1e-13);
    }
    
    @Test
    public void testGetInfNorm() {
        IntDoubleVector v1 = getIntDoubleVector();
        v1.set(1, toDouble(11));
        v1.set(3, toDouble(33));
        v1.set(2, toDouble(-22));
        v1.set(5, toDouble(-55));
        
        assertEquals(55, v1.getInfNorm(), 1e-13);
    }

    @Test
    public void testIterate() {
        IntDoubleVector v2 = getIntDoubleVector();
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));
        v2.add(3, toDouble(0));

        final IntArrayList idxs = new IntArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);
        
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
        v2.add(3, toDouble(0));

        final IntArrayList idxs = new IntArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);

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
    
    // TODO: Add this test once we implement iterator in IntDoubleDenseVector.
    //
    //  @Test
    //  public void testIterator() {
    //      IntDoubleVector v2 = getIntDoubleVector();
    //      
    //      v2.set(1, toDouble(11));
    //      v2.set(3, toDouble(33));
    //      v2.set(4, toDouble(0));
    //      v2.set(5, toDouble(55));
    //
    //      IntDoubleEntry e;
    //      Iterator<IntDoubleEntry> iter = v2.iterator();
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
    
    protected abstract IntDoubleVector getIntDoubleVector();

}