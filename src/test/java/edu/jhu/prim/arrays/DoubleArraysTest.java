package edu.jhu.prim.arrays;

import java.util.Arrays;

import org.junit.Test;

import edu.jhu.prim.arrays.DoubleArrays;
import edu.jhu.util.Timer;

public class DoubleArraysTest {

    @Test
    public void testFillSpeed() {
        int trials = 10000;
        int size = 100000;
        {
            double[] array = new double[size];
            Timer timer = new Timer();
            timer.start();
            for (int t = 0; t < trials; t++) {
                for (int i=0; i<array.length; i++) {
                    array[i] = t;
                }
            }
            timer.stop();
            System.out.println("for loop total ms: " + timer.totMs());
        }
        {
            double[] array = new double[size];
            Timer timer = new Timer();
            timer.start();
            for (int t = 0; t < trials; t++) {
                Arrays.fill(array, t);
            }
            timer.stop();
            System.out.println("Arrays.fill() total ms: " + timer.totMs());
        }
        {
            double[] array = new double[size];
            Timer timer = new Timer();
            timer.start();
            for (int t = 0; t < trials; t++) {
                DoubleArrays.fill(array, t);
            }
            timer.stop();
            System.out.println("edu.jhu.util.Utilities.fill() total ms: " + timer.totMs());
        }
    }

}
