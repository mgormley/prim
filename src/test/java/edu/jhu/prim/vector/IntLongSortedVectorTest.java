package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toLong;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntLongSortedVectorTest extends AbstractIntLongVectorTest {

    @Test
    public void testGetWithNoZeroValues() {
        IntLongSortedVector v1 = new IntLongSortedVector();
        v1.set(1, toLong(11));
        v1.set(3, toLong(0));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        v1.set(5, toLong(0));
        
		assertEquals(11, toInt(v1.get(1)));
		assertEquals(22, toInt(v1.get(2)));
		assertEquals(0, toInt(v1.get(3)));
		assertEquals(44, toInt(v1.get(4)));
		assertEquals(0, toInt(v1.get(5)));
		assertEquals(5, v1.getUsed());
		
        IntLongSortedVector v2 = IntLongSortedVector.getWithNoZeroValues(v1);
        assertEquals(3, v2.getUsed());
		assertEquals(11, toInt(v2.get(1)));
		assertEquals(22, toInt(v2.get(2)));
		assertEquals(44, toInt(v2.get(4)));
    }
    
    @Test
    public void testHadamardProduct() {
        IntLongSortedVector v1 = new IntLongSortedVector();
        IntLongSortedVector v2 = new IntLongSortedVector();
        
        v1.set(1, toLong(11));
        v1.set(3, toLong(0));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        v1.set(5, toLong(0));
        
        v2.set(1, toLong(11));
        v2.set(3, toLong(0));
        v2.set(2, toLong(22));
        v2.set(4, toLong(0));
        v2.set(5, toLong(55));
        
        IntLongSortedVector v3 = v1.getProd(v2);

		assertEquals(11*11, toInt(v3.get(1)));
		assertEquals(22*22, toInt(v3.get(2)));
		assertEquals(0, toInt(v3.get(3)));
		assertEquals(0, toInt(v3.get(4)));
		assertEquals(0, toInt(v3.get(5)));
    }
    
    @Test
    public void testSetAll() {
        IntLongSortedVector v1 = new IntLongSortedVector();
        IntLongSortedVector v2 = new IntLongSortedVector();
        
        v1.set(1, toLong(11));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        
        v2.set(1, toLong(11));
        v2.set(3, toLong(33));
        v2.set(4, toLong(0));
        v2.set(5, toLong(55));
        
        v1.set(v2);

        assertEquals(11, toInt(v1.get(1)));
        assertEquals(0, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals(0, toInt(v1.get(4)));
        assertEquals(55, toInt(v1.get(5)));
    }
    
    @Test
    public void testGetElementwiseSum() {
        IntLongSortedVector v1 = new IntLongSortedVector();
        IntLongSortedVector v2 = new IntLongSortedVector();
        
        v1.set(1, toLong(11));
        v1.set(2, toLong(22));
        v1.set(4, toLong(44));
        
        v2.set(1, toLong(11));
        v2.set(3, toLong(33));
        v2.set(4, toLong(0));
        v2.set(5, toLong(55));
        
        IntLongSortedVector v3 = v1.getSum(v2);

        assertEquals(22, toInt(v3.get(1)));
        assertEquals(22, toInt(v3.get(2)));
        assertEquals(33, toInt(v3.get(3)));
        assertEquals(44, toInt(v3.get(4)));
        assertEquals(55, toInt(v3.get(5)));        
    }

    protected IntLongSortedVector getIntLongVector() {
        return new IntLongSortedVector();
    }
    
}    
