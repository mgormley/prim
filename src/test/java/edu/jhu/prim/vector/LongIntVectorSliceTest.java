package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class LongIntVectorSliceTest {

    @Test
    public void testDotProduct() {
        LongIntVector v1 = new LongIntDenseVector();
        LongIntVector v2 = new LongIntDenseVector();
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
        
        v1 = new LongIntVectorSlice((LongIntDenseVector)v1, 0, 50);
        v2 = new LongIntVectorSlice((LongIntDenseVector)v2, 0, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
    @Test
    public void testDotProduct2() {
        LongIntVector v1 = new LongIntDenseVector();
        LongIntVector v2 = new LongIntDenseVector();
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
        
        v1 = new LongIntVectorSlice((LongIntDenseVector)v1, 2, 50);
        v2 = new LongIntVectorSlice((LongIntDenseVector)v2, 2, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
}    
