package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toDouble;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class LongDoubleVectorSliceTest {

    @Test
    public void testDotProduct() {
        LongDoubleVector v1 = new LongDoubleDenseVector();
        LongDoubleVector v2 = new LongDoubleDenseVector();
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
        
        v1 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, 0, 50);
        v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v2, 0, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
    @Test
    public void testDotProduct2() {
        LongDoubleVector v1 = new LongDoubleDenseVector();
        LongDoubleVector v2 = new LongDoubleDenseVector();
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
        
        v1 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, 2, 50);
        v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v2, 2, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
}    
