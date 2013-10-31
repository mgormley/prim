package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

    protected abstract IntIntVector getIntIntVector();

}