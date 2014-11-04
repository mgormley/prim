package edu.jhu.prim.util.math;

import static edu.jhu.prim.util.math.LogAddTable.LOG_ADD_MIN;
import static edu.jhu.prim.util.math.LogAddTable.logAdd;
import static edu.jhu.prim.util.math.LogAddTable.logSubtract;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;



public class LogAddTableTest {

    @Test
    public void testLogAddTable() {
        Random random = new Random();

        System.out.println("Log add test: ");
        double logAddDiff = 0;
        for (int i = 0; i < 100; i++) {
            double a = LOG_ADD_MIN * random.nextDouble();
            double b = a * random.nextDouble();
            System.out.println("a="+a + " b=" + b);
            double diff = logAdd(a, b) - FastMath.logAddExact(a, b);
            logAddDiff += Math.abs(diff);
        }
        System.out.println("Total log add difference: " + logAddDiff);
        System.out.println(Math.exp(-128));
    }
    
    @Test
    public void testLogSubtractTable() {
        Random random = new Random();

        System.out.println("Log subtract test: ");
        double logSubtractDiff = 0;
        for (int i = 0; i < 100; i++) {
            double b = LOG_ADD_MIN * random.nextDouble();
            double a = b * random.nextDouble();
            System.out.println("a="+a + " b=" + b);
            double diff = logSubtract(a, b) - FastMath.logSubtractExact(a, b);
            logSubtractDiff += Math.abs(diff);
        }
        System.out.println("Total log subtract difference: " + logSubtractDiff);
        System.out.println(Math.exp(-128));
    }
    
    @Test
    public void testAddSubtract() {
        double sum;
        System.out.println("sum: " + Math.log(0.2 + 0.3));
        sum = Double.NEGATIVE_INFINITY;
        sum = logAdd(sum, Math.log(0.2));
        sum = logAdd(sum, Math.log(0.3));
        System.out.println("sum: " + sum);
        sum = Double.NEGATIVE_INFINITY;
        sum = FastMath.logAddExact(sum, Math.log(0.2));
        sum = FastMath.logAddExact(sum, Math.log(0.3));
        System.out.println("sum: " + sum);
        sum = FastMath.logAddExact(sum, Math.log(0.1));
        sum = FastMath.logSubtractExact(sum, Math.log(0.1));
        System.out.println("sum: " + sum);
    }
    
    @Test
    public void test3() {
        double largestNegDouble = -Double.MAX_VALUE;
        System.out.println("Largest negative double: " + largestNegDouble);
        System.out.println("Log add of largest neg double with itself: " + logAdd(largestNegDouble, largestNegDouble));
        System.out.println("Min double: " + Double.MIN_VALUE);
        System.out.println("Log of min double: " + Math.log(Double.MIN_VALUE));
        System.out.println("Min 32-bit float: " + -Float.MAX_VALUE); //-3.4E38);
        //System.out.println("Min 32-bit float: " + -3.4E38);
    }
    
    @Test
    public void test4() {
        System.out.println(FastMath.logAddExact(Double.NEGATIVE_INFINITY, 0));
        System.out.println(FastMath.logAddExact(0, 0));
    }
    
    @Test
    public void testRangeLogAdd() {
        Random random = new Random();

        int min = -10000;
        int max = 10000;
        // Check for logAdd differences.
        double tolerance = 1e-13;
        for (int i=min; i<max; i += 10) {
            for (int j=min; j<max; j += 10) {
                double a = i+(random.nextDouble()*10);
                double b = j+(random.nextDouble()*10);
                double tableSum = LogAddTable.logAdd((double) a, (double) b); 
                double exactSum = FastMath.logAddExact((double) a, (double) b);
                double diff = Math.abs(tableSum - exactSum);
                if (diff >= tolerance) {
                    System.out.println(String.format("a=%.2f b=%.2f (a-b)=%.2f diff=%g", a, b, (a-b), diff));
                }
                assertTrue(diff < tolerance);
            }
        }
    }

    @Test
    public void testRangeLogSubtract() {
        Random random = new Random();
        
        // Check for logSubtract differences.
        int min = -100;
        int max = 100;
        double tolerance = 2.0;
        for (int i=min; i<max; i += 1) {
            for (int j=min; j<max; j += 1) {
                if (i < j) { continue; }
                double a = i;//+(random.nextDouble()*10);
                double b = j;//+(random.nextDouble()*10);
                
                double tableSum = LogAddTable.logSubtract((double) Math.max(a, b), (double) Math.min(a, b)); 
                double exactSum = FastMath.logSubtractExact((double) Math.max(a, b), (double) Math.min(a, b));
                double diff = Math.abs(tableSum - exactSum);
                if (tableSum == Double.NEGATIVE_INFINITY && exactSum == Double.NEGATIVE_INFINITY) {
                    diff = 0;
                }
                if (diff >= tolerance) {
                    System.out.println(String.format("a=%.2f b=%.2f (a-b)=%.2f diff=%g", a, b, (a-b), diff));
                }
                if (a == 52 && b == 20) {
                    System.out.println(String.format("a=%.2f b=%.2f (a-b)=%.2f diff=%g", a, b, (a-b), diff));
                    System.out.println(String.format("logSubTable=%g  logSubExact=%g", tableSum, exactSum));
                }
                assertTrue(diff < tolerance);
            }
        }
        
    }
    

    @Test
    public void testRangeLogSubtractAfterAdd() {
        // Find the point at which logAdd loses precision.
        //double j = 10d;
        for (int j : getList(1, 10, 20)) {
            for (boolean useExact : getList(true, false)) {
                for (int i = j - 40; i < j + 40; i++) {
                    double val;
                    if (useExact) {
                        val = FastMath.logSubtractExact(FastMath.logAddExact((double) i, (double) j), (double) i);
                    } else {
                        val = LogAddTable.logSubtract(LogAddTable.logAdd((double) i, (double) j), (double) i); 
                    }
                    double diff = Math.abs(val - j);
                    System.out.println(String.format("exact=%7s j=%2d i=%2d (i-j)=%2d eq0=%7s val=%g diff=%g", ""+useExact, j, i, (i-j), 
                            ""+(val == Double.NEGATIVE_INFINITY), val, diff));
                    if (val == Double.NEGATIVE_INFINITY) {
                        System.out.println(String.format("exact=%7s j=%2d i=%2d (i-j)=%2d eq0=%7s val=%g", ""+useExact, j, i, (i-j), 
                                ""+(val == Double.NEGATIVE_INFINITY), val));
                        break;
                    }
                }
            }
        }
        
    }
    
    @Test
    public void testRangeLogSubtractAfterAddSpecialCase1() {
        // Note: double precision of the mantissa is 53 bits. This equates to ~
        // 16 decimal digits, more exactly: 53 * log_{10}(2) ~= 15.955.
        //
        // Look at a specific case:
        double i=52;
        double j=20;
        double logAddTable = LogAddTable.logAdd((double) i, (double) j);
        double logSubTable = LogAddTable.logSubtract(logAddTable, (double) i); 
        double logSubExact = FastMath.logSubtractExact(logAddTable, (double) i); 
        System.out.println(String.format("logSubTable=%g logSubExact=%g logAdd=%.50g", logSubTable, logSubExact, logAddTable));
        // This is WAY off.
        assertEquals(logSubExact, logSubTable, 1.0);
    }
    
    // Borrowed from Lists class for these tests.
    private static <T> List<T> getList(T... args) {
        return Arrays.asList(args);
    }
    
}
