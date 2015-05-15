package edu.jhu.prim.util.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import edu.jhu.prim.util.random.UnlockedXORShiftRNG.DeterministicSeedGenerator;

/**
 * A "no-dependencies" version of Prng.
 * @author mgormley
 */
public class Prng {
    
    public enum PrngSetting {
        REPLICABLE_THREADSAFE, 
        REPLICABLE_NOT_THREADSAFE,
        NOT_REPLICABLE_THREADSAFE,
    }
    
    public static final long DEFAULT_SEED;

    private static Random curRandom;
    private static long seed;
    private static boolean useThreadLocalRandom;

    static {
        DEFAULT_SEED = 123456789101112l;
        System.out.println("WARNING: pseudo random number generator is not thread safe");
        init(DEFAULT_SEED, PrngSetting.REPLICABLE_THREADSAFE);
    }
    
    private Prng() {
        // private constructor.
    }
    
    /* ----- Initialization ----- */

    public static void seed(long seed) {
        Prng.seed = seed;
        curRandom.setSeed(seed);
    }
    
    public static void init(long seed, PrngSetting setting) {
        System.out.println("SEED="+seed);
        Prng.seed = seed;
        Prng.useThreadLocalRandom = false;
        if (setting == PrngSetting.REPLICABLE_THREADSAFE) {
            curRandom = new Random(seed);
        } else if (setting == PrngSetting.REPLICABLE_NOT_THREADSAFE) {
            curRandom = new UnlockedXORShiftRNG(new DeterministicSeedGenerator(seed));
        } else if (setting == PrngSetting.NOT_REPLICABLE_THREADSAFE) {
            curRandom = null;
            Prng.useThreadLocalRandom = true;    
        } else {
            throw new RuntimeException("Unsupported setting: " + setting);
        }        
    }
    
    /* ----- Psuedo random number generation ----- */
    
    public static double nextDouble() {
        if (useThreadLocalRandom) {
            return ThreadLocalRandom.current().nextDouble();
        } else {
            return curRandom.nextDouble();
        }
    }
    
    public static float nextFloat() {
        if (useThreadLocalRandom) {
            return ThreadLocalRandom.current().nextFloat();
        } else {
            return curRandom.nextFloat();
        }
    }
    
    public static boolean nextBoolean() {
        if (useThreadLocalRandom) {
            return ThreadLocalRandom.current().nextBoolean();
        } else {
            return curRandom.nextBoolean();
        }
    }

    public static short nextShort() {
        if (useThreadLocalRandom) {
            return (short) ThreadLocalRandom.current().nextInt();
        } else {
            return (short) curRandom.nextInt();
        }
    }
    
    public static int nextInt() {
        if (useThreadLocalRandom) {
            return ThreadLocalRandom.current().nextInt();
        } else {
            return curRandom.nextInt();
        }
    }
    
    public static int nextInt(int n) {
        if (useThreadLocalRandom) {
            return ThreadLocalRandom.current().nextInt(n);
        } else {
            return curRandom.nextInt(n);
        }
    }

    public static long nextLong() {
        if (useThreadLocalRandom) {
            return ThreadLocalRandom.current().nextLong();
        } else {
            return curRandom.nextLong();
        }
    }
    
    /* ----- Getters / Setters ----- */
    
    public static long getSeed() {
        return seed;
    }

    public static void setRandom(Random curRandom) {
        Prng.curRandom = curRandom;
    }
    
    public static Random getRandom() {
        return curRandom;
    }    
    
}
