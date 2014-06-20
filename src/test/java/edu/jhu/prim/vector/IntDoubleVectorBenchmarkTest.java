package edu.jhu.prim.vector;

import java.util.Random;

import org.junit.Test;

public class IntDoubleVectorBenchmarkTest {

    private Random rand = new Random(9001);
    
    //@Test
    public void speedTest() {
        int dimension = 125000;
        double[] weights = new double[dimension];
        for(int i=0; i<dimension; i++)
            weights[i] = rand.nextGaussian();
        
        int nAdds = 1000;
        System.out.printf("on %s, %s took %.1f seconds\n", "mostlyWrites",
                "IntDoubleSortedVector",mostlyWrites(new IntDoubleSortedVector(), weights, nAdds)/1000d);
        System.out.printf("on %s, %s took %.1f seconds\n", "mostlyWrites",
                "IntDoubleUnsortedVector", mostlyWrites(new IntDoubleUnsortedVector(), weights, nAdds)/1000d);
        System.out.printf("on %s, %s took %.1f seconds\n", "mostlyWrites",
                "IntDoubleUnsortedVector opt", mostlyWrites(new IntDoubleUnsortedVector(nAdds), weights, nAdds)/1000d);
        
        System.out.printf("on %s, %s took %.1f seconds\n", "balanced",
                "IntDoubleSortedVector",balanced(new IntDoubleSortedVector(), weights)/1000d);
        System.out.printf("on %s, %s took %.1f seconds\n", "balanced",
                "IntDoubleUnsortedVector", balanced(new IntDoubleUnsortedVector(), weights)/1000d);
        System.out.printf("on %s, %s took %.1f seconds\n", "balanced",
                "IntDoubleUnsortedVector opt", balanced(new IntDoubleUnsortedVector(300), weights)/1000d);
    }
    
    public long mostlyWrites(IntDoubleVector vec, double[] dotWith, int nAdds) {
        long start = System.currentTimeMillis();
        int n = dotWith.length;
        for(int i=0; i<15000; i++) {
            IntDoubleVector w = vec.copy();
            for(int j=0; j<nAdds; j++)
                w.add(rand.nextInt(n), 1d);
            w.dot(dotWith);
        }
        return System.currentTimeMillis() - start;
    }
    
    public long balanced(IntDoubleVector vec, double[] dotWith) {
        long start = System.currentTimeMillis();
        int n = dotWith.length;
        double s;
        for(int i=0; i<25000; i++) {
            
            IntDoubleVector w = vec.copy();
            
            for(int j=0; j<200; j++)
                w.add(rand.nextInt(n), 1d);
            s = 0d;
            for(int j=0; j<50; j++)
                s += w.get(j);
            w.scale(1d / s);
            w.dot(dotWith);
            w.scale(s);
            
            for(int j=0; j<50; j++)
                w.add(rand.nextInt(n), 1d);
            s = 0d;
            for(int j=0; j<200; j++)
                s += w.get(j);
            w.scale(1d / s);
            w.dot(dotWith);
            w.scale(s);
        }
        return System.currentTimeMillis() - start;
    }
}
