package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toLong;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        v1.set(0, toLong(0));
        v1.set(1, toLong(1));
        v1.set(2, toLong(2));
        v1.set(3, toLong(3));
        v1.set(4, toLong(4));
        
        v2.set(0, toLong(10)); // this is 10
        v2.set(1, toLong(11));
        v2.set(2, toLong(22));
        v2.set(3, toLong(33));
        v2.set(4, toLong(44));
                
        v1 = new IntLongVectorSlice((IntLongDenseVector)v1, 1, 3);
        v2 = new IntLongVectorSlice((IntLongDenseVector)v2, 2, 2);
        
        assertEquals(1*22 + 2*33 + 4*0, toInt(v1.dot(v2)));
    }
    

    @Test
    public void testGetSet() {
        IntLongVector v1 = new IntLongDenseVector();

        v1.set(0, toLong(0));
        v1.set(1, toLong(1));
        v1.set(2, toLong(2));
        v1.set(3, toLong(3));
        v1.set(4, toLong(4));
        
        IntLongVectorSlice v2 = new IntLongVectorSlice((IntLongDenseVector)v1, 1, 3);
        
        v2.set(0, 11);
        v2.set(1, 22);
        v2.set(2, 33);
        
        try {
            v2.set(-1, 0);
            fail();
        } catch(Exception e) {
            // pass
        }
        try {
            v2.set(3, 0);
            fail();
        } catch(Exception e) {
            // pass
        }
        
        // Test orig vector.
        assertEquals( 0, toInt(v1.get(0)));        
        assertEquals(11, toInt(v1.get(1)));
        assertEquals(22, toInt(v1.get(2)));
        assertEquals(33, toInt(v1.get(3)));
        assertEquals( 4, toInt(v1.get(4)));
                
        // Test slice vector.
        assertEquals(11, toInt(v2.get(0)));
        assertEquals(22, toInt(v2.get(1)));
        assertEquals(33, toInt(v2.get(2)));
        // Test out of bounds
        assertEquals( 0, toInt(v2.get(3)));
    }
    
    @Test
    public void testAdd() {
        IntLongVector v1 = new IntLongDenseVector();

        v1.set(0, toLong(2));
        v1.set(1, toLong(1));
        v1.set(2, toLong(1));
        v1.set(3, toLong(1));
        v1.set(4, toLong(3));
        
        IntLongVectorSlice v2 = new IntLongVectorSlice((IntLongDenseVector)v1, 1, 3);
        
        v2.add(0, 11);
        v2.add(1, 22);
        v2.add(2, 33);

        try {
            v2.add(-1, 0);
            fail();
        } catch(Exception e) {
            // pass
        }
        try {
            v2.add(3, 0);
            fail();
        } catch(Exception e) {
            // pass
        }
        
        assertEquals(2, toInt(v1.get(0)));
        assertEquals(12, toInt(v1.get(1)));
        assertEquals(23, toInt(v1.get(2)));
        assertEquals(34, toInt(v1.get(3)));
        assertEquals(3, toInt(v1.get(4)));
    }

    @Test
    public void testScale() {
        IntLongVector v1 = new IntLongDenseVector();

        v1.set(0, toLong(10));
        v1.set(1, toLong(11));
        v1.set(2, toLong(22));
        v1.set(3, toLong(33));
        v1.set(4, toLong(44));
        
        IntLongVectorSlice v2 = new IntLongVectorSlice((IntLongDenseVector)v1, 1, 3);
            
        v2.scale(toLong(2));
        
        assertEquals(10, toInt(v1.get(0)));
        assertEquals(22, toInt(v1.get(1)));
        assertEquals(44, toInt(v1.get(2)));
        assertEquals(66, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));

    }
    
}    
