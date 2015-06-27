package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toFloat;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;


public class IntFloatVectorSliceTest {

    @Test
    public void testDotProduct() {
        IntFloatVector v1 = new IntFloatDenseVector();
        IntFloatVector v2 = new IntFloatDenseVector();
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
        
        v1 = new IntFloatVectorSlice((IntFloatDenseVector)v1, 0, 50);
        v2 = new IntFloatVectorSlice((IntFloatDenseVector)v2, 0, 53);
        
        assertEquals(11*5308 + 10*24 + 23*7, toInt(v1.dot(v2)));
    }
    
    @Test
    public void testDotProduct2() {
        IntFloatVector v1 = new IntFloatDenseVector();
        IntFloatVector v2 = new IntFloatDenseVector();
        v1.set(0, toFloat(0));
        v1.set(1, toFloat(1));
        v1.set(2, toFloat(2));
        v1.set(3, toFloat(3));
        v1.set(4, toFloat(4));
        
        v2.set(0, toFloat(10)); // this is 10
        v2.set(1, toFloat(11));
        v2.set(2, toFloat(22));
        v2.set(3, toFloat(33));
        v2.set(4, toFloat(44));
                
        v1 = new IntFloatVectorSlice((IntFloatDenseVector)v1, 1, 3);
        v2 = new IntFloatVectorSlice((IntFloatDenseVector)v2, 2, 2);
        
        assertEquals(1*22 + 2*33 + 4*0, toInt(v1.dot(v2)));
    }
    

    @Test
    public void testGetSet() {
        IntFloatVector v1 = new IntFloatDenseVector();

        v1.set(0, toFloat(0));
        v1.set(1, toFloat(1));
        v1.set(2, toFloat(2));
        v1.set(3, toFloat(3));
        v1.set(4, toFloat(4));
        
        IntFloatVectorSlice v2 = new IntFloatVectorSlice((IntFloatDenseVector)v1, 1, 3);
        
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
        IntFloatVector v1 = new IntFloatDenseVector();

        v1.set(0, toFloat(2));
        v1.set(1, toFloat(1));
        v1.set(2, toFloat(1));
        v1.set(3, toFloat(1));
        v1.set(4, toFloat(3));
        
        IntFloatVectorSlice v2 = new IntFloatVectorSlice((IntFloatDenseVector)v1, 1, 3);
        
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
        IntFloatVector v1 = new IntFloatDenseVector();

        v1.set(0, toFloat(10));
        v1.set(1, toFloat(11));
        v1.set(2, toFloat(22));
        v1.set(3, toFloat(33));
        v1.set(4, toFloat(44));
        
        IntFloatVectorSlice v2 = new IntFloatVectorSlice((IntFloatDenseVector)v1, 1, 3);
            
        v2.scale(toFloat(2));
        
        assertEquals(10, toInt(v1.get(0)));
        assertEquals(22, toInt(v1.get(1)));
        assertEquals(44, toInt(v1.get(2)));
        assertEquals(66, toInt(v1.get(3)));
        assertEquals(44, toInt(v1.get(4)));

    }

    @Test
    public void testConstruct() {
        // TODO: There's a rather odd case we're dealing with in which the dense
        // vector may actually have a larger internal representation than what
        // it exposes. So we initialize the internal array here.
        IntFloatVector v1 = new IntFloatDenseVector(5);

        v1.set(0, toFloat(10));
        v1.set(1, toFloat(11));
        v1.set(2, toFloat(22));
        v1.set(3, toFloat(33));
        v1.set(4, toFloat(44));
        
        IntFloatVectorSlice v2;
        
        // Try various slice indicies exceeding the bounds sometimes.
        for (int i=-1; i<=6; i++) {
            for (int j=-1; j<=6; j++) {
                if (0 <= i && i < 5 && 0 <= j && j <= 5 && i + j <= 5) {
                    // If the slice indices are good, we should create it without exceptions.
                    v2 = new IntFloatVectorSlice((IntFloatDenseVector)v1, i, j);
                } else {
                    // If the slice indices are bad, we should throw an exception.
                    try {
                        v2 = new IntFloatVectorSlice((IntFloatDenseVector)v1, i, j);
                        fail("i=" + i + " j=" + j);
                    } catch(Exception e) {
                        // pass
                    }
                }                       
            }
        }

    }
    
    @Test
    public void testDimension() {
        IntFloatVector v1 = new IntFloatDenseVector();
        v1.set(1, toFloat(11));
        v1.set(2, toFloat(22));
        v1.set(4, toFloat(44));
        v1.set(5, toFloat(55));
        IntFloatVectorSlice v2 = new IntFloatVectorSlice((IntFloatDenseVector)v1, 1, 3);
        assertEquals(3, v2.getNumImplicitEntries());
    }
    
}    
