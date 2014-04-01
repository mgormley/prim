package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toDouble;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntDoubleUnsortedVectorTest extends AbstractIntDoubleVectorTest {

    protected IntDoubleVector getIntDoubleVector() {
        return new IntDoubleUnsortedVector();
    }
    
}    
