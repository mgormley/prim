package edu.jhu.prim.map;

import java.util.Iterator;

import org.junit.Test;

import edu.jhu.prim.Primitives;
import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;
import edu.jhu.prim.vector.LongDoubleSortedVector;
import edu.jhu.prim.vector.LongDoubleUnsortedVector;
import edu.jhu.prim.vector.LongDoubleUnsortedVector.LongDouble;
import edu.jhu.util.Timer;

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

            {
                Timer timer = new Timer();
                long idxSum = 0;
                double valSum = 0;
                for (int t = 0; t < trials; t++) {
                    LongDoubleUnsortedVector map = new LongDoubleUnsortedVector();
                    for (int i = 0; i < max; i++) {
                        map.add(Primitives.hashOfInt(i) % max, i);
                    }
                    map.compact();
                    
                    timer.start();
                    Iterator<LongDouble> iter = map.indicesAndValues();
                    while (iter.hasNext()) {
                        LongDouble e = iter.next();
                        idxSum += e.getKey();
                        valSum += e.getValue();
                    }
                    timer.stop();
                }
                System.out.println("Primitive UnsortedMap (auto-boxing iterator): " + timer.totMs());
            }
        }
    }
    
    @Test
    public void testBinaryOpSpeed() {
        int trials = 1000000;
        // int max = 200;
        for (int max = 20; max < Math.pow(10, 4) * 20; max *= 10) {
            trials /= 10;
            System.out.println("max: " + max);
            {
                Timer timer1 = new Timer();
                Timer timer2 = new Timer();
                for (int t = 0; t < trials; t++) {
                    LongDoubleSortedVector map1 = new LongDoubleSortedVector();
                    for (int i = 0; i < max; i++) {
                        map1.put(Primitives.hashOfInt(i) % max*2, i);
                    }

                    LongDoubleSortedVector map2 = new LongDoubleSortedVector();
                    for (int i = 0; i < max; i++) {
                        map2.put(Primitives.hashOfInt(i) % max*3, i);
                    }

                    timer2.start();
                    //map1.hadamardProd(map2);
                    timer2.stop();
                    timer1.start();
                    map1.getSum(map2);
                    timer1.stop();
                }
                System.out.println("Primitive SortedVec (apply in binaryOp): " + timer1.totMs());
                System.out.println("Primitive SortedVec (for loops): " + timer2.totMs());
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
