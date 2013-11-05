package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toLong;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class IntLongVectorSliceTest {

    @Test
    public void testDotProduct() {
        IntLongVector v1 = new IntLongDenseVector();
        IntLongVector v2 = new IntLongDenseVector();
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
        
        v1 = new IntLongVectorSlice((IntLongDenseVector)v1, 0, 50);
        v2 = new IntLongVectorSlice((IntLongDenseVector)v2, 0, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
    @Test
    public void testDotProduct2() {
        IntLongVector v1 = new IntLongDenseVector();
        IntLongVector v2 = new IntLongDenseVector();
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
        
        v1 = new IntLongVectorSlice((IntLongDenseVector)v1, 2, 50);
        v2 = new IntLongVectorSlice((IntLongDenseVector)v2, 2, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
}    
