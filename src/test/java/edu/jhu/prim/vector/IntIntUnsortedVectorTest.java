package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntIntUnsortedVectorTest extends AbstractIntIntVectorTest {

    protected IntIntVector getIntIntVector() {
        return new IntIntUnsortedVector();
    }
    
}    
