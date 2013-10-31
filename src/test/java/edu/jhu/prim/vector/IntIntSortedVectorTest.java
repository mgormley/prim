package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntIntSortedVectorTest extends AbstractIntIntVectorTest {

    @Test
    public void testGetWithNoZeroValues() {
        IntIntSortedVector v1 = new IntIntSortedVector();
        v1.set(1, toInt(11));
        v1.set(3, toInt(0));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        v1.set(5, toInt(0));
        
		assertEquals(11, toInt(v1.get(1)));
		assertEquals(22, toInt(v1.get(2)));
		assertEquals(0, toInt(v1.get(3)));
		assertEquals(44, toInt(v1.get(4)));
		assertEquals(0, toInt(v1.get(5)));
		assertEquals(5, v1.getUsed());
		
        IntIntSortedVector v2 = IntIntSortedVector.getWithNoZeroValues(v1);
        assertEquals(3, v2.getUsed());
		assertEquals(11, toInt(v2.get(1)));
		assertEquals(22, toInt(v2.get(2)));
		assertEquals(44, toInt(v2.get(4)));
    }
    
    @Test
    public void testHadamardProduct() {
        IntIntSortedVector v1 = new IntIntSortedVector();
        IntIntSortedVector v2 = new IntIntSortedVector();
        
        v1.set(1, toInt(11));
        v1.set(3, toInt(0));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        v1.set(5, toInt(0));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(0));
        v2.set(2, toInt(22));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        IntIntSortedVector v3 = v1.getProd(v2);

		assertEquals(11*11, toInt(v3.get(1)));
		assertEquals(22*22, toInt(v3.get(2)));
		assertEquals(0, toInt(v3.get(3)));
		assertEquals(0, toInt(v3.get(4)));
		assertEquals(0, toInt(v3.get(5)));
    }
    
    @Test
    public void testSetAll() {
        IntIntSortedVector v1 = new IntIntSortedVector();
        IntIntSortedVector v2 = new IntIntSortedVector();
        
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        v1.set(v2);

        assertEquals(11, toInt(v1.get(1)));
        assertEquals(0, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals(0, toInt(v1.get(4)));
        assertEquals(55, toInt(v1.get(5)));
    }
    
    @Test
    public void testGetElementwiseSum() {
        IntIntSortedVector v1 = new IntIntSortedVector();
        IntIntSortedVector v2 = new IntIntSortedVector();
        
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(4, toInt(44));
        
        v2.set(1, toInt(11));
        v2.set(3, toInt(33));
        v2.set(4, toInt(0));
        v2.set(5, toInt(55));
        
        IntIntSortedVector v3 = v1.getSum(v2);

        assertEquals(22, toInt(v3.get(1)));
        assertEquals(22, toInt(v3.get(2)));
        assertEquals(33, toInt(v3.get(3)));
        assertEquals(44, toInt(v3.get(4)));
        assertEquals(55, toInt(v3.get(5)));        
    }

    protected IntIntSortedVector getIntIntVector() {
        return new IntIntSortedVector();
    }
    
}    
