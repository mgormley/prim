package edu.jhu.prim.map;

import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;
import edu.jhu.prim.util.Timer;

public class LongDoubleIteratorSpeedTest {
    
    @Test
    public void testCompareIteration() {
        int trials = 1000000;
        // int max = 200;
        for (int max = 20; max < Math.pow(10, 4) * 20; max *= 10) {
            trials /= 10;
            System.out.println("max: " + max);
            {
                Timer timer = new Timer();
                long idxSum = 0;
                double valSum = 0;
                for (int t = 0; t < trials; t++) {
                    LongDoubleHashMap map = new LongDoubleHashMap();
                    for (int i = 0; i < max; i++) {
                        map.put(Primitives.hashOfInt(i) % max, i);
                    }
                    
                    timer.start();
                    for (LongDoubleEntry e : map) {
                        idxSum += e.get();
                        valSum += e.index();
                    }
                    timer.stop();
                }
                System.out.println("Primitive HashMap (iterator): " + timer.totMs());
            }
            {
                Timer timer = new Timer();
                Sum fn = new Sum();
                for (int t = 0; t < trials; t++) {
                    LongDoubleHashMap map = new LongDoubleHashMap();
                    for (int i = 0; i < max; i++) {
                        map.put(Primitives.hashOfInt(i) % max, i);
                    }
                    
                    timer.start();
                    map.apply(fn);
                    timer.stop();
                }
                System.out.println("Primitive HashMap (apply): " + timer.totMs());
            }
            {
                Timer timer = new Timer();
                long idxSum = 0;
                double valSum = 0;
                for (int t = 0; t < trials; t++) {
                    LongDoubleSortedMap map = new LongDoubleSortedMap();
                    for (int i = 0; i < max; i++) {
                        map.put(Primitives.hashOfInt(i) % max, i);
                    }
                    
                    timer.start();
                    for (LongDoubleEntry e : map) {
                        idxSum += e.get();
                        valSum += e.index();
                    }
                    timer.stop();
                }
                System.out.println("Primitive SortedMap (iterator): " + timer.totMs());
            }

            {
                Timer timer = new Timer();
                Sum fn = new Sum();
                for (int t = 0; t < trials; t++) {
                    LongDoubleSortedMap map = new LongDoubleSortedMap();
                    for (int i = 0; i < max; i++) {
                        map.put(Primitives.hashOfInt(i) % max, i);
                    }
                    
                    timer.start();
                    map.apply(fn);
                    timer.stop();
                }
                System.out.println("Primitive SortedMap (apply): " + timer.totMs());
            }
            {
                Timer timer = new Timer();
                long idxSum = 0;
                double valSum = 0;
                for (int t = 0; t < trials; t++) {
                    LongDoubleSortedMap map = new LongDoubleSortedMap();
                    for (int i = 0; i < max; i++) {
                        map.put(Primitives.hashOfInt(i) % max, i);
                    }
                    
                    timer.start();
                    int used = map.getUsed();
                    long[] indices = map.getInternalIndices();
                    double[] values = map.getInternalValues();
                    for (int i=0; i<used; i++) {
                        idxSum += indices[i];
                        valSum += values[i];
                    }
                    timer.stop();
                }
                System.out.println("Primitive SortedMap (internal): " + timer.totMs());
            }
            
        }
    }
    private static class Sum implements FnLongDoubleToDouble {
        public int idxSum = 0;
        public double valSum = 0;
        @Override
        public double call(long i, double v) {
            idxSum += i;
            valSum += v;
            return v;
        }
    }
}
