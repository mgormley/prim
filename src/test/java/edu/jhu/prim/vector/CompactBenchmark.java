package edu.jhu.prim.vector;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class CompactBenchmark {
    
    @Test
    public void correctness() {
        
        final int n = 100;
        Random r = new Random(9001);
        
        for(int i=0; i<n; i++) {
            for(int m : Arrays.asList(2, 5, 10, 50, 150, 500, 1500, 5000, 50000)) {
            
                LongDoubleUnsortedVector oldImpl = new LongDoubleUnsortedVector(m+1);
                LongDoubleUnsortedVector newImpl = new LongDoubleUnsortedVector(m+1);

                int seed = r.nextInt();
                r.setSeed(seed);
                fill(oldImpl, r, m, true, true);
                r.setSeed(seed);
                fill(newImpl, r, m, true, true);

                oldImpl.oldCompact(false);
                newImpl.compact();

                if(!LongDoubleUnsortedVector.dbgEquals(oldImpl, newImpl))
                    assertTrue(false);
            }
        }
    }
    
    private void fill(LongDoubleUnsortedVector vec, Random r, int nAdds, boolean indexCollisions, boolean valueCollisions) {
        for(int i=0; i<nAdds; i++) {
            int idx = indexCollisions ? r.nextInt(nAdds) : r.nextInt(nAdds * 10);
            double val = valueCollisions ? r.nextInt(11) - 5 : r.nextGaussian();
            vec.add(idx, val);
        }
    }
    
    @Test
    public void speed() {
        int n = 1000;
        Random r = new Random(9001);
        int seed = r.nextInt();
        for(int m : Arrays.asList(2, 10, 100, 500, 2000, 10000)) {
            long t = System.currentTimeMillis();
            LongDoubleUnsortedVector oldImpl = new LongDoubleUnsortedVector(m+1);
            r.setSeed(seed);
            for(int i=0; i<n; i++) {
                fill(oldImpl, r, m, true, true);
                oldImpl.oldCompact(false);
                oldImpl.clear();
            }
            long tOld = System.currentTimeMillis() - t;

            t = System.currentTimeMillis();
            LongDoubleUnsortedVector newImpl = new LongDoubleUnsortedVector(m+1);
            r.setSeed(seed);
            for(int i=0; i<n; i++) {
                fill(newImpl, r, m, true, true);
                newImpl.compact();
                newImpl.clear();
            }
            long tNew = System.currentTimeMillis() - t;

            System.out.printf("for %d inserts and a compact, old took %.3f sec, new took %.3f, %.1fx speedup\n",
                    m, tOld/1000d, tNew/1000d, ((double) tOld) / tNew);
            if(tNew > 10)
                assertTrue(tOld / tNew > 0.99d);
        }
    }

}
