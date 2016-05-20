package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.util.Lambda.FnLongIntToInt;
import edu.jhu.prim.util.Lambda.FnLongIntToVoid;


public class LongIntDenseVectorTest extends AbstractLongIntVectorTest {

    protected LongIntVector getLongIntVector() {
        return new LongIntDenseVector();
    }

    @Test
    public void testIterate() {
        LongIntVector v2 = getLongIntVector();
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));
        v2.add(3, toInt(0));

        final LongArrayList idxs = new LongArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnLongIntToVoid() {           
            @Override
            public void call(long idx, int val) {
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
        LongIntVector v2 = getLongIntVector();
        v2.add(1, toInt(11));
        v2.add(2, toInt(22));
        v2.add(1, toInt(111));
        v2.add(3, toInt(0));

        final LongArrayList idxs = new LongArrayList();
        final IntArrayList vals = new IntArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnLongIntToInt() {           
            @Override
            public int call(long idx, int val) {
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
