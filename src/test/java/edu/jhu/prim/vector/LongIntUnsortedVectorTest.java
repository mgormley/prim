package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongIntUnsortedVectorTest extends AbstractLongIntVectorTest {

    protected LongIntVector getLongIntVector() {
        return new LongIntUnsortedVector();
    }
    
}    
