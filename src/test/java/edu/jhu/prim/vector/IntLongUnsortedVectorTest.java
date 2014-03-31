package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toLong;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntLongUnsortedVectorTest extends AbstractIntLongVectorTest {

    protected IntLongVector getIntLongVector() {
        return new IntLongUnsortedVector();
    }
    
}    
