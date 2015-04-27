package edu.jhu.prim.util;

import java.util.Random;

/**
 * A "no-dependencies" version of Prng.
 * @author mgormley
 */
public class Prng {
    
    public static final long DEFAULT_SEED;


    private static Random javaRandom;        
    private static Random curRandom;
    private static long seed;
    private static boolean multiThreadAccess;
    
    private Prng() {
        // private constructor.
    }
    
    public static void seed(long seed) {
        Prng.seed = seed;
        System.out.println("SEED="+seed);
        javaRandom = new Random(seed);
        setRandom(javaRandom);
    }
    
    public static long getSeed() {
        return seed;
    }
    
    public static void init(long seed, boolean multiThreadAccess) {
        seed(seed);
        Prng.multiThreadAccess = multiThreadAccess;
    }

    public static void setRandom(Random curRandom) {
        Prng.curRandom = curRandom;
    }
    
    public static Random getRandom() {
        return curRandom;
    }

    static {
        DEFAULT_SEED = 123456789101112l;
        System.out.println("WARNING: pseudo random number generator is not thread safe");
        init(DEFAULT_SEED, false);
    }
    
    
    public static double nextDouble() {
        return curRandom.nextDouble();
    }
    
    public static double nextFloat() {
        return curRandom.nextFloat();
    }
    
    public static boolean nextBoolean() {
        return curRandom.nextBoolean();
    }

    public static short nextShort() {
        return (short) curRandom.nextInt();
    }
    
    public static int nextInt() {
        return curRandom.nextInt();
    }
    
    public static int nextInt(int n) {
        return curRandom.nextInt(n);
    }

    public static long nextLong() {
        return curRandom.nextLong();
    }
    
}
