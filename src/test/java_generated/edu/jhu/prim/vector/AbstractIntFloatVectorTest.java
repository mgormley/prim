package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toFloat;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class AbstractIntFloatVectorTest {

    public AbstractIntFloatVectorTest() {
        super();
    }

    @Test
    public void testDotProduct() {
        IntFloatVector v1 = getIntFloatVector();
        IntFloatVector v2 = getIntFloatVector();
        v1.set(4, toFloat(5308));
        v1.set(49, toFloat(23));
        v1.set(32, toFloat(22));
        v1.set(23, toFloat(10));
        
        v2.set(3, toFloat(204));
        v2.set(2, toFloat(11));
        v2.set(4, toFloat(11));
        v2.set(23, toFloat(24));
        v2.set(10, toFloat(0001));
        v2.set(52, toFloat(11));
        v2.set(49, toFloat(7));
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }

    @Test
    public void testAdd() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        
        v1.add(3, toFloat(33));
        v1.add(1, toFloat(11));
        v1.add(2, toFloat(22));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }

    @Test
    public void testScale() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
    
        v1.scale(toFloat(2));
        
    	assertEquals(22, toInt(v1.get(1)));
    	assertEquals(44, toInt(v1.get(2)));
    	assertEquals(66, toInt(v1.get(3)));
    }
    
    @Test
    public void testAddAll() {        
        testAddAllHelper(getIntFloatVector(), new IntFloatSortedVector());        
        testAddAllHelper(getIntFloatVector(), new IntFloatHashVector());
        testAddAllHelper(getIntFloatVector(), new IntFloatDenseVector());
    }

    private void testAddAllHelper(IntFloatVector v1, IntFloatVector v2) {
        v1.set(1, toFloat(11));
        v1.set(2, toFloat(22));
        v1.set(4, toFloat(44));
        
        v2.set(1, toFloat(11));
        v2.set(3, toFloat(33));
        v2.set(4, toFloat(0));
        v2.set(5, toFloat(55));
        
        v1.add(v2);

        assertEquals(22, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(55, toInt(v1.get(5)));
    }
    
    @Test
    public void testSubtractAll() {        
        testSubtractAllHelper(getIntFloatVector(), new IntFloatSortedVector());        
        testSubtractAllHelper(getIntFloatVector(), new IntFloatHashVector());
        testSubtractAllHelper(getIntFloatVector(), new IntFloatDenseVector());
    }

    private void testSubtractAllHelper(IntFloatVector v1, IntFloatVector v2) {
        v1.set(1, toFloat(11));
        v1.set(2, toFloat(22));
        v1.set(4, toFloat(44));
        
        v2.set(1, toFloat(11));
        v2.set(3, toFloat(33));
        v2.set(4, toFloat(0));
        v2.set(5, toFloat(55));
        
        v1.subtract(v2);

        assertEquals(0, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(-33, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));
        assertEquals(-55, toInt(v1.get(5)));        
    }
    
    @Test
    public void testProductAll() {        
        testProductAllHelper(getIntFloatVector(), new IntFloatSortedVector());        
        testProductAllHelper(getIntFloatVector(), new IntFloatHashVector());
        testProductAllHelper(getIntFloatVector(), new IntFloatDenseVector());
    }

    private void testProductAllHelper(IntFloatVector v1, IntFloatVector v2) {
        v1.set(1, toFloat(11));
        v1.set(2, toFloat(22));
        v1.set(4, toFloat(44));
        
        v2.set(1, toFloat(11));
        v2.set(3, toFloat(33));
        v2.set(4, toFloat(2));
        v2.set(5, toFloat(55));
        
        v1.product(v2);

        assertEquals(11*11, toInt(v1.get(1)));
        assertEquals(22*0, toInt(v1.get(2)));
        assertEquals(33*0, toInt(v1.get(3)));
        assertEquals(44*2, toInt(v1.get(4)));
        assertEquals(55*0, toInt(v1.get(5)));        
    }

    @Test
    public void testDimension() {
        IntFloatVector v1 = getIntFloatVector();
        assertEquals(0, v1.getNumImplicitEntries());
        v1.set(1, toFloat(11));
        v1.set(2, toFloat(22));
        v1.set(4, toFloat(44));
        assertEquals(5, v1.getNumImplicitEntries());
    }
    
    @Test
    public void testGetSum() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        
        assertEquals(11+33+22, v1.getSum(), 1e-13);
    }
    
    @Test
    public void testGetProd() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(0, toFloat(1));
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        
        assertEquals(11*33*22, v1.getProd(), 1e-13);
    }
    
    @Test
    public void testGetProdImplicits() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        // Test case where there are implicit zeros.
        assertEquals(0, v1.getProd(), 1e-13);        
    }
    
    @Test
    public void testGetMax() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        v1.set(5, toFloat(-33));

        assertEquals(33, v1.getMax(), 1e-13);
    }

    @Test
    public void testGetArgmax() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        v1.set(5, toFloat(-33));
        
        assertEquals(3, v1.getArgmax());
    }
    
    @Test
    public void testGetMin() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        v1.set(5, toFloat(-33));

        assertEquals(-33, v1.getMin(), 1e-13);
    }

    @Test
    public void testGetArgmin() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(22));
        v1.set(5, toFloat(-33));
        
        assertEquals(5, v1.getArgmin());
    }
    
    @Test
    public void testGetL2Norm() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(-22));
        v1.set(5, toFloat(-55));
        
        assertEquals(11*11 + 33*33 + 22*22 + 55*55, v1.getL2Norm(), 1e-13);
    }
    
    @Test
    public void testGetInfNorm() {
        IntFloatVector v1 = getIntFloatVector();
        v1.set(1, toFloat(11));
        v1.set(3, toFloat(33));
        v1.set(2, toFloat(-22));
        v1.set(5, toFloat(-55));
        
        assertEquals(55, v1.getInfNorm(), 1e-13);
    }
    
    protected abstract IntFloatVector getIntFloatVector();

}