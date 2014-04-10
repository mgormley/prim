package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class AbstractLongIntVectorTest {

    public AbstractLongIntVectorTest() {
        super();
    }

    @Test
    public void testDotProduct() {
        LongIntVector v1 = getLongIntVector();
        LongIntVector v2 = getLongIntVector();
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
        LongIntVector v1 = getLongIntVector();
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
        LongIntVector v1 = getLongIntVector();
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
        testAddAllHelper(getLongIntVector(), new LongIntSortedVector());        
        testAddAllHelper(getLongIntVector(), new LongIntHashVector());
        testAddAllHelper(getLongIntVector(), new LongIntDenseVector());
    }

    private void testAddAllHelper(LongIntVector v1, LongIntVector v2) {
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
        testSubtractAllHelper(getLongIntVector(), new LongIntSortedVector());        
        testSubtractAllHelper(getLongIntVector(), new LongIntHashVector());
        testSubtractAllHelper(getLongIntVector(), new LongIntDenseVector());
    }

    private void testSubtractAllHelper(LongIntVector v1, LongIntVector v2) {
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
        testProductAllHelper(getLongIntVector(), new LongIntSortedVector());        
        testProductAllHelper(getLongIntVector(), new LongIntHashVector());
        testProductAllHelper(getLongIntVector(), new LongIntDenseVector());
    }

    private void testProductAllHelper(LongIntVector v1, LongIntVector v2) {
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
        LongIntVector v1 = getLongIntVector();
        assertEquals(0, v1.getNumImplicitEntries());
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        assertEquals(5, v1.getNumImplicitEntries());
    }
    
    protected abstract LongIntVector getLongIntVector();

}