package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;


public class IntIntVectorSliceTest {

    @Test
    public void testDotProduct() {
        IntIntVector v1 = new IntIntDenseVector();
        IntIntVector v2 = new IntIntDenseVector();
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
        
        v1 = new IntIntVectorSlice((IntIntDenseVector)v1, 0, 50);
        v2 = new IntIntVectorSlice((IntIntDenseVector)v2, 0, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
    @Test
    public void testDotProduct2() {
        IntIntVector v1 = new IntIntDenseVector();
        IntIntVector v2 = new IntIntDenseVector();
        v1.set(0, toInt(0));
        v1.set(1, toInt(1));
        v1.set(2, toInt(2));
        v1.set(3, toInt(3));
        v1.set(4, toInt(4));
        
        v2.set(0, toInt(10)); // this is 10
        v2.set(1, toInt(11));
        v2.set(2, toInt(22));
        v2.set(3, toInt(33));
        v2.set(4, toInt(44));
                
        v1 = new IntIntVectorSlice((IntIntDenseVector)v1, 1, 3);
        v2 = new IntIntVectorSlice((IntIntDenseVector)v2, 2, 2);
        
        assertEquals(1*22 + 2*33 + 4*0, toInt(v1.dot(v2)));
    }
    

    @Test
    public void testGetSet() {
        IntIntVector v1 = new IntIntDenseVector();

        v1.set(0, toInt(0));
        v1.set(1, toInt(1));
        v1.set(2, toInt(2));
        v1.set(3, toInt(3));
        v1.set(4, toInt(4));
        
        IntIntVectorSlice v2 = new IntIntVectorSlice((IntIntDenseVector)v1, 1, 3);
        
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
        IntIntVector v1 = new IntIntDenseVector();

        v1.set(0, toInt(2));
        v1.set(1, toInt(1));
        v1.set(2, toInt(1));
        v1.set(3, toInt(1));
        v1.set(4, toInt(3));
        
        IntIntVectorSlice v2 = new IntIntVectorSlice((IntIntDenseVector)v1, 1, 3);
        
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
        IntIntVector v1 = new IntIntDenseVector();

        v1.set(0, toInt(10));
        v1.set(1, toInt(11));
        v1.set(2, toInt(22));
        v1.set(3, toInt(33));
        v1.set(4, toInt(44));
        
        IntIntVectorSlice v2 = new IntIntVectorSlice((IntIntDenseVector)v1, 1, 3);
            
        v2.scale(toInt(2));
        
        assertEquals(10, toInt(v1.get(0)));
        assertEquals(22, toInt(v1.get(1)));
        assertEquals(44, toInt(v1.get(2)));
        assertEquals(66, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));

    }
    
}    
