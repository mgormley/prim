package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toDouble;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
    
    protected abstract IntDoubleVector getIntDoubleVector();

}