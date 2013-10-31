package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toLong;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

    protected abstract IntLongVector getIntLongVector();

}