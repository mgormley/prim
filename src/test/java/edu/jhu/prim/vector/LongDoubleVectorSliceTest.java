package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toDouble;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        v1.set(0, toDouble(0));
        v1.set(1, toDouble(1));
        v1.set(2, toDouble(2));
        v1.set(3, toDouble(3));
        v1.set(4, toDouble(4));
        
        v2.set(0, toDouble(10)); // this is 10
        v2.set(1, toDouble(11));
        v2.set(2, toDouble(22));
        v2.set(3, toDouble(33));
        v2.set(4, toDouble(44));
                
        v1 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, 1, 3);
        v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v2, 2, 2);
        
        assertEquals(1*22 + 2*33 + 4*0, toInt(v1.dot(v2)));
    }
    

    @Test
    public void testGetSet() {
        LongDoubleVector v1 = new LongDoubleDenseVector();

        v1.set(0, toDouble(0));
        v1.set(1, toDouble(1));
        v1.set(2, toDouble(2));
        v1.set(3, toDouble(3));
        v1.set(4, toDouble(4));
        
        LongDoubleVectorSlice v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, 1, 3);
        
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
        LongDoubleVector v1 = new LongDoubleDenseVector();

        v1.set(0, toDouble(2));
        v1.set(1, toDouble(1));
        v1.set(2, toDouble(1));
        v1.set(3, toDouble(1));
        v1.set(4, toDouble(3));
        
        LongDoubleVectorSlice v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, 1, 3);
        
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
        LongDoubleVector v1 = new LongDoubleDenseVector();

        v1.set(0, toDouble(10));
        v1.set(1, toDouble(11));
        v1.set(2, toDouble(22));
        v1.set(3, toDouble(33));
        v1.set(4, toDouble(44));
        
        LongDoubleVectorSlice v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, 1, 3);
            
        v2.scale(toDouble(2));
        
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
        LongDoubleVector v1 = new LongDoubleDenseVector(5);

        v1.set(0, toDouble(10));
        v1.set(1, toDouble(11));
        v1.set(2, toDouble(22));
        v1.set(3, toDouble(33));
        v1.set(4, toDouble(44));
        
        LongDoubleVectorSlice v2;
        
        // Try various slice indicies exceeding the bounds sometimes.
        for (int i=-1; i<=6; i++) {
            for (int j=-1; j<=6; j++) {
                if (0 <= i && i < 5 && 0 <= j && j <= 5 && i + j <= 5) {
                    // If the slice indices are good, we should create it without exceptions.
                    v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, i, j);
                } else {
                    // If the slice indices are bad, we should throw an exception.
                    try {
                        v2 = new LongDoubleVectorSlice((LongDoubleDenseVector)v1, i, j);
                        fail("i=" + i + " j=" + j);
                    } catch(Exception e) {
                        // pass
                    }
                }                       
            }
        }

    }
    
}    
