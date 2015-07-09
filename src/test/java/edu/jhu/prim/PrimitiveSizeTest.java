package edu.jhu.prim;

import org.junit.Test;

/**
 * This test confirms that the JVM is storing longs with 8 bytes, doubles with 8, ints with 4, shorts with 2, and bytes with 1.
 * 
 * The tests should be run with -Xmx64m.
 * 
 * Expected output is something like the following:
 * <pre>
 * Number of ints    on OOME: 15308800
 * Number of longs   on OOME:  7663616
 * Number of floats  on OOME: 12955648
 * Number of shorts  on OOME: 25827328
 * Number of bytes   on OOME: 51329024
 * Number of doubles on OOME:  6486016
 * </pre>
 * 
 * @author mgormley
 */
public class PrimitiveSizeTest {


    @Test
    public void testNumDoubles() {
        int bytes = 128*1024*1024;
        int doubles = bytes / 8;
        int width = 2048;
        
        double[][] as = new double[doubles/width][];
        int count = 0;
        try {
            for (int i=0; i<as.length; i++) {
                as[i] = new double[width];
                count += as[i].length;
            }
        } catch(OutOfMemoryError e) {
            System.out.println("Number of doubles on OOME: " + count);
            return;
        }
        System.out.println("No OOME");
    }    
    
    @Test
    public void testNumLongs() {
        int bytes = 128*1024*1024;
        int longs = bytes / 8;
        int width = 2048;
        
        long[][] as = new long[longs/width][];
        int count = 0;
        try {
            for (int i=0; i<as.length; i++) {
                as[i] = new long[width];
                count += as[i].length;
            }
        } catch(OutOfMemoryError e) {
            System.out.println("Number of longs on OOME: " + count);
            return;
        }
        System.out.println("No OOME");
    }
    
    @Test
    public void testNumFloats() {
        int bytes = 128*1024*1024;
        int floats = bytes / 4;
        int width = 2048;
        
        float[][] as = new float[floats/width][];
        int count = 0;
        try {
            for (int i=0; i<as.length; i++) {
                as[i] = new float[width];
                count += as[i].length;
            }
        } catch(OutOfMemoryError e) {
            System.out.println("Number of floats on OOME: " + count);
            return;
        }
        System.out.println("No OOME");
    }
    
    @Test
    public void testNumInts() {
        int bytes = 128*1024*1024;
        int ints = bytes / 4;
        int width = 2048;
        
        int[][] as = new int[ints/width][];
        int count = 0;
        try {
            for (int i=0; i<as.length; i++) {
                as[i] = new int[width];
                count += as[i].length;
            }
        } catch(OutOfMemoryError e) {
            System.out.println("Number of ints on OOME: " + count);
            return;
        }
        System.out.println("No OOME");
    }
    
    @Test
    public void testNumShorts() {
        int bytes = 128*1024*1024;
        int shorts = bytes / 2;
        int width = 2048;
        
        short[][] as = new short[shorts/width][];
        int count = 0;
        try {
            for (int i=0; i<as.length; i++) {
                as[i] = new short[width];
                count += as[i].length;
            }
        } catch(OutOfMemoryError e) {
            System.out.println("Number of shorts on OOME: " + count);
            return;
        }
        System.out.println("No OOME");
    }
    
    @Test
    public void testNumBytes() {
        int bytes = 128*1024*1024;
        int width = 2048;
        
        byte[][] as = new byte[bytes/width][];
        int count = 0;
        try {
            for (int i=0; i<as.length; i++) {
                as[i] = new byte[width];
                count += as[i].length;
            }
        } catch(OutOfMemoryError e) {
            System.out.println("Number of bytes on OOME: " + count);
            return;
        }
        System.out.println("No OOME");
    }
    
}
