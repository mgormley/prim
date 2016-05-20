package edu.jhu.prim.vector;

import static edu.jhu.prim.Primitives.toFloat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.list.FloatArrayList;
import edu.jhu.prim.list.IntArrayList;
import edu.jhu.prim.util.Lambda.FnIntFloatToFloat;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;


public class IntFloatDenseVectorTest extends AbstractIntFloatVectorTest {

    protected IntFloatVector getIntFloatVector() {
        return new IntFloatDenseVector();
    }

    @Test
    public void testIterate() {
        IntFloatVector v2 = getIntFloatVector();
        v2.add(1, toFloat(11));
        v2.add(2, toFloat(22));
        v2.add(1, toFloat(111));
        v2.add(3, toFloat(0));

        final IntArrayList idxs = new IntArrayList();
        final FloatArrayList vals = new FloatArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);
        
        final MutableInt i = new MutableInt(0);
        
        v2.iterate(new FnIntFloatToVoid() {           
            @Override
            public void call(int idx, float val) {
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
        IntFloatVector v2 = getIntFloatVector();
        v2.add(1, toFloat(11));
        v2.add(2, toFloat(22));
        v2.add(1, toFloat(111));
        v2.add(3, toFloat(0));

        final IntArrayList idxs = new IntArrayList();
        final FloatArrayList vals = new FloatArrayList();
        idxs.add(0); vals.add(0);
        idxs.add(1); vals.add(122);
        idxs.add(2); vals.add(22);
        idxs.add(3); vals.add(0);

        final MutableInt i = new MutableInt(0);
        
        v2.apply(new FnIntFloatToFloat() {           
            @Override
            public float call(int idx, float val) {
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
