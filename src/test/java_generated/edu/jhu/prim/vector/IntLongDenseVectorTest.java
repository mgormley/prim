package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toLong;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.util.Lambda.FnIntLongToLong;
import edu.jhu.prim.util.Lambda.FnIntLongToVoid;


public class IntLongDenseVectorTest extends AbstractIntLongVectorTest {

    protected IntLongVector getIntLongVector() {
        return new IntLongDenseVector();
    }

    @Test
    public void testIterate() {
        IntLongVector v2 = getIntLongVector();
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));
        v2.add(3, toLong(0));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnIntLongToVoid() {           
            @Override
            public void call(int idx, long val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }

    @Test
    public void testApply() {
        IntLongVector v2 = getIntLongVector();
        v2.add(1, toLong(11));
        v2.add(2, toLong(22));
        v2.add(1, toLong(111));
        v2.add(3, toLong(0));

        final IntArrayList idxs = new IntArrayList();
        final LongArrayList vals = new LongArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnIntLongToLong() {           
            @Override
            public long call(int idx, long val) {
                assertTrue(i.v < idxs.size()); // Failing here means we are iterating over too many entries.
                assertEquals(idxs.get(i.v), idx);
                assertEquals(vals.get(i.v), val);
                i.v++;
                return val;
            }
        });
        
        // Did we iterate over the full expected set.
        assertEquals(i.v, idxs.size());
    }
    
}    
