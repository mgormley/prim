package edu.jhu.prim.util.random;

import org.junit.Test;

import edu.jhu.prim.util.random.Prng.PrngSetting;
import edu.jhu.prim.util.Timer;

public class PrngSpeedTest {

   /**
    * Results from Pacaya:
    *  Round: 2
    *        java  426.0
    *         tlr   25.0
    *          mt  862.0
    *         mtf  294.0
    *        uxor   74.0
    *         xor  820.0
    *         umt  208.0
    *         
    * Current results:
    * Round: 2
    *  java  378.0
    *  uxor   66.0
    *   tlr   22.0
    */
    @Test
    public void testSpeed() {
        final long seed = Prng.DEFAULT_SEED;
        final int NUM_DOUBLES = 10000000;
        final String format = "%10s %6.1f\n";

        Timer timer;

        for (int round = 0; round < 3; round++) {
            System.out.printf("Round: " + round + "\n");
            
            Prng.init(seed, PrngSetting.REPLICABLE_THREADSAFE);
            timer = new Timer();
            timer.start();
            for (int i = 0; i < NUM_DOUBLES; i++) {
                Prng.nextDouble();
            }
            timer.stop();
            System.out.printf(format, "java", timer.totMs());

            Prng.init(seed, PrngSetting.REPLICABLE_NOT_THREADSAFE);
            timer = new Timer();
            timer.start();
            for (int i = 0; i < NUM_DOUBLES; i++) {
                Prng.nextDouble();
            }
            timer.stop();
            System.out.printf(format, "uxor", timer.totMs());

            Prng.init(seed, PrngSetting.NOT_REPLICABLE_THREADSAFE);
            timer = new Timer();
            timer.start();
            for (int i = 0; i < NUM_DOUBLES; i++) {
                Prng.nextDouble();
            }
            timer.stop();
            System.out.printf(format, "tlr", timer.totMs());
        }
    }

}
