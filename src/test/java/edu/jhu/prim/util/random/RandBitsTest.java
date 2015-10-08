package edu.jhu.prim.util.random;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import edu.jhu.prim.util.Timer;


public class RandBitsTest {
    
    @Test
    public void testRandBits() {
        int total = 10000000; //64*3+2;
        for (int trial = 0; trial < 1; trial++) {
            Timer t1 = new Timer();
            Timer t2 = new Timer();
            {
                t1.start();
                RandBits rand = new RandBits();
                int sum = 0;
                for (int i=0; i<total; i++) {
                    sum += rand.nextBit() ? 1 : 0;
                }
                double avg = 1.0 * sum / total;
                t1.stop();
                System.out.println(avg);
                assertEquals(0.5, avg, 1e-3);
            }
            {
                t2.start();
                int sum = 0;
                for (int i=0; i<total; i++) {
                    sum += Prng.nextBoolean() ? 1 : 0;
                }
                double avg = 1.0 * sum / total;
                t2.stop();
                System.out.println(avg);
                assertEquals(0.5, avg, 1e-3);
            }
            System.out.println("RandBits: " + t1.totMs());
            System.out.println("Random: " + t2.totMs());
            System.out.println("Ratio: " + t2.totMs() / t1.totMs());
        }
        //assert(t2.totMs() > 10 * t1.totMs());
    }
    
    /**
     * Generating the floats takes about 30 seconds -- while generating the bits takes 134 seconds.
     */
    @Ignore
    @Test
    public void testRandSpeed() {
        // for i in range((2**20)): x = np.random.rand(40000/32)
        int smax = (int) Math.pow(2, 20);
        RandBits rand = new RandBits();
        for (int s=0; s<smax; s++) {
//            float[] nums = new float[40000/32];
//            for (int i=0; i<nums.length; i++) {
//                nums[i] = Prng.nextFloat();
//            }
            for (int i=0; i<40000; i++) {
                rand.nextBit();
            }
        }
    }
    
}
