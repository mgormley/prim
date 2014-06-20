package edu.jhu.util;

import java.util.Random;

/**
 * A "no-dependencies" version of Prng.
 * @author mgormley
 */
public class Prng {
    
    public static final long DEFAULT_SEED;

    // Using default seed
    public static Random javaRandom;
        
    public static Random curRandom;
    public static long seed;
    
    private Prng() {
        // private constructor.
    }
    
    public static void seed(long seed) {
        Prng.seed = seed;
        System.out.println("SEED="+seed);
        javaRandom = new Random(seed);
        setRandom(javaRandom);
    }

    public static void setRandom(Random curRandom) {
        Prng.curRandom = curRandom;
    }
    
    public static Random getRandom() {
        return curRandom;
    }

    static {
        DEFAULT_SEED = 123456789101112l;
        //DEFAULT_SEED = System.currentTimeMillis();
        System.out.println("WARNING: pseudo random number generator is not thread safe");
        seed(DEFAULT_SEED);
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
