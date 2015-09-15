package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toDouble;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.DoubleArrayList;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;
import edu.jhu.prim.util.Lambda.FnLongDoubleToVoid;


public class LongDoubleDenseVectorTest extends AbstractLongDoubleVectorTest {

    protected LongDoubleVector getLongDoubleVector() {
        return new LongDoubleDenseVector();
    }

    @Test
    public void testIterate() {
        LongDoubleVector v2 = getLongDoubleVector();
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));
        v2.add(3, toDouble(0));

        final LongArrayList idxs = new LongArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnLongDoubleToVoid() {           
            @Override
            public void call(long idx, double val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }

    @Test
    public void testApply() {
        LongDoubleVector v2 = getLongDoubleVector();
        v2.add(1, toDouble(11));
        v2.add(2, toDouble(22));
        v2.add(1, toDouble(111));
        v2.add(3, toDouble(0));

        final LongArrayList idxs = new LongArrayList();
        final DoubleArrayList vals = new DoubleArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnLongDoubleToDouble() {           
            @Override
            public double call(long idx, double val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx, 1e-13);
                assertEquals(vals.get(i.v), val, 1e-13);
                i.v++;
                return val;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }
    
}    
