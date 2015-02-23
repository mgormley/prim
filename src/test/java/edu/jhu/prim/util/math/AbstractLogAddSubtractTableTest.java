package edu.jhu.prim.util.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.jhu.util.Timer;

public abstract class AbstractLogAddSubtractTableTest {

    private static final double LOG_ADD_MIN = -128;
    
    protected abstract double logAdd(double a, double b);
    protected abstract double logSubtract(double a, double b);

    protected abstract double getToleranceForLogAddRandomTest();
    protected abstract double getToleranceForLogSubtractRandomTest();

    
    @Test
    public void testLogAddEqualValues() {
        double largestNegDouble = -Double.MAX_VALUE;
        assertEquals(FastMath.logAddExact(-1, -1), logAdd(-1, -1), 1e-13);
        assertEquals(FastMath.logAddExact(largestNegDouble, largestNegDouble), logAdd(largestNegDouble, largestNegDouble), 1e-13);
    }
    
    @Test
    public void testLogSubtractEqualValues() {
        double largestNegDouble = -Double.MAX_VALUE;
        assertEquals(FastMath.logSubtractExact(-1, -1), logSubtract(-1, -1), 1e-13);
        assertEquals(FastMath.logSubtractExact(largestNegDouble, largestNegDouble), logSubtract(largestNegDouble, largestNegDouble), 1e-13);
    }        
    
    @Test
    public void testRandomRangeLogAdd() {
        Random random = new Random();

        double min = -100;
        double max = 100;
        double incr = 1;
        // Check for logAdd differences.
        double tolerance = getToleranceForLogAddRandomTest();
        double worstTolerance = 0;
        for (double i=min; i<max; i += incr) {
            for (double j=min; j<max; j += incr) {
                double a = i+(random.nextDouble()*10);
                double b = j+(random.nextDouble()*10);
                double diff = assertCorrectLogAdd(a, b, tolerance, false);
                worstTolerance = Math.max(diff, worstTolerance);
            }
        }
        System.out.println("worstTolerance (add): " + worstTolerance);
    }
    
    @Test
    public void testRandomRangeLogSubtract() {
        Random random = new Random();

        // Check for logSubtract differences.
        double min = -100;
        double max = 100;
        double incr = 1;
        double tolerance = getToleranceForLogSubtractRandomTest();
        double worstTolerance = 0;
        for (double i=min; i<max; i += incr) {
            for (double j=min; j<max; j += incr) {
                double a = i+(random.nextDouble()*10);
                double b = j+(random.nextDouble()*10);
                if (a < b) {
                    // Swap.
                    double tmp = a;
                    a = b;
                    b = tmp;
                }
                double diff = assertCorrectLogSubtract(a, b, tolerance, false);
                worstTolerance = Math.max(diff, worstTolerance);
            }
        }
        System.out.println("worstTolerance (sub): " + worstTolerance);
    }
    
    @Test
    public void testRangeLogSubtractAfterAdd() {
        // Find the point at which logAdd loses precision.
        //double j = 10d;
        for (int j : getList(1, 10, 20)) {
            for (int i = j - 40; i < j + 40; i++) {
                assertCorrectLogSubtractAfterLogAdd(i, j, 1e-4, true);                    
            }
        }        
    }
    
    /* ----------- This suite of tests checks the speedup. ----------- */
    
    @Test
    public void testSpeedupLogAdd() {
        for (int round = 0; round<5; round++) {
            Timer tableTimer = new Timer();
            Timer exactTimer = new Timer();
            double min = -100;
            double max = 100;
            double incr = 0.1;
            double total=0;
            exactTimer.start();
            for (double i=min; i<max; i += incr) {
                for (double j=min; j<max; j += incr) {
                    double a = i;
                    double b = j;
                    total += FastMath.logAddExact(a, b);
                }
            }
            exactTimer.stop();
            tableTimer.start();
            for (double i=min; i<max; i += incr) {
                for (double j=min; j<max; j += incr) {
                    double a = i;
                    double b = j;
                    total += logAdd(a, b);
                }
            }
            tableTimer.stop();
            System.out.printf("total=%f\n", total);
            System.out.printf("table(ms)=%f exact(ms)=%f speedup=%g\n", tableTimer.totMs(), exactTimer.totMs(), exactTimer.totMs() / tableTimer.totMs());
        }
    }
    
    @Test
    public void testSpeedupLogSubtract() {
        for (int round = 0; round<5; round++) {
            Timer tableTimer = new Timer();
            Timer exactTimer = new Timer();
            double min = -100;
            double max = 100;
            double incr = 0.1;
            double total=0;
            exactTimer.start();
            for (double i=min; i<max; i += incr) {
                for (double j=min; j<i; j += incr) {
                    double a = i;
                    double b = j;
                    total += FastMath.logSubtractExact(a, b);
                }
            }
            exactTimer.stop();
            tableTimer.start();
            for (double i=min; i<max; i += incr) {
                for (double j=min; j<i; j += incr) {
                    double a = i;
                    double b = j;
                    total += logSubtract(a, b);
                }
            }
            tableTimer.stop();
            System.out.printf("total=%f\n", total);
            System.out.printf("table(ms)=%f exact(ms)=%f speedup=%g\n", tableTimer.totMs(), exactTimer.totMs(), exactTimer.totMs() / tableTimer.totMs());
        }
    }
    
    /* ----------- This suite of tests ensures that the function behaves well around a specific start point. ----------- */
    @Test
    public void testRangeLogAdd_IncreasingDifferences() {
        // Check for logAdd differences.
        double maxStart = 52;
        double minStart = maxStart - 1;
        int numDivs = 100;
        double div = 2;
        assertCorrectRangeLogAdd(maxStart, minStart, numDivs, div, 1e-13);
    }

    @Test
    public void testRangeLogAdd_DecreasingDifferences() {
        // Check for logAdd differences.
        double maxStart = 52;
        double minStart = maxStart - 100;
        int numDivs = 100;
        double div = 0.5;        
        assertCorrectRangeLogAdd(maxStart, minStart, numDivs, div, 1e-13);
    } 
    
    @Test
    public void testRangeLogSubtract_IncreasingDifferences() {
        // Check for logSubtract differences.
        double maxStart = 52;
        double minStart = maxStart - 1;
        int numDivs = 100;
        double div = 2;
        assertCorrectRangeLogSubtract(maxStart, minStart, numDivs, div, 1e-13);
    }

    @Test
    public void testRangeLogSubtract_DecreasingDifferences() {
        // Check for logSubtract differences.
        double maxStart = 52;
        double minStart = maxStart - 100;
        int numDivs = 100;
        double div = 0.5;
        assertCorrectRangeLogSubtract(maxStart, minStart, numDivs, div, 1e-13);
    }
    
    /* ----------- This suite of tests ensures that the function behaves well around many start points. ----------- */
    
    @Test
    public void testRangeLogAdd_IncreasingDifferences_ManyStarts() {
        // Check for logAdd differences.
        for (double maxStart = -52; maxStart < 52; maxStart++) {
            double minStart = maxStart - 1;
            int numDivs = 100;
            double div = 2;
            assertCorrectRangeLogAdd(maxStart, minStart, numDivs, div, 1e-13);
        }
    }

    @Test
    public void testRangeLogAdd_DecreasingDifferences_ManyStarts() {
        Random random = new Random();
        // Check for logAdd differences.
        for (double maxStart = -52; maxStart < 52; maxStart++) {
            double minStart = maxStart - 100;
            int numDivs = 100;
            double mult = 0.5;
            assertCorrectRangeLogAdd(maxStart, minStart, numDivs, mult, 1e-3);
        }
    } 
    
    @Test
    public void testRangeLogSubtract_IncreasingDifferences_ManyStarts() {
        // Check for logSubtract differences.
        for (double maxStart = -52; maxStart < 52; maxStart++) {
            double minStart = maxStart - 1;
            int numDivs = 100;
            double mult = 2;
            assertCorrectRangeLogSubtract(maxStart, minStart, numDivs, mult, 1e-13);
        }
    }

    @Test
    public void testRangeLogSubtract_DecreasingDifferences_ManyStarts() {
        // Check for logSubtract differences.
        for (double maxStart = -52; maxStart < 52; maxStart++) {
            double minStart = maxStart - 100;
            int numDivs = 100;
            double mult = 0.5;
            assertCorrectRangeLogSubtract(maxStart, minStart, numDivs, mult, 1e-13);
        }
    } 
        
    protected void assertCorrectRangeLogAdd(double maxStart, double minStart, int numDivs, double div, double tolerance) {
        double diff = maxStart - minStart;
        double a = maxStart;
        double b = maxStart - diff;
        for (int i=0; i<numDivs; i++) {
            assertCorrectLogAdd(a, b, tolerance, true);
            diff *= div;
            b = maxStart - diff;
        }
    }   
    
    protected void assertCorrectRangeLogSubtract(double maxStart, double minStart, int numDivs, double div, double tolerance) {
        double diff = maxStart - minStart;
        double a = maxStart;
        double b = maxStart - diff;
        for (int i=0; i<numDivs; i++) {
            assertCorrectLogSubtract(a, b, tolerance, true);
            diff *= div;
            b = maxStart - diff;
        }
    }   
    
    private double assertCorrectLogAdd(double a, double b, double tol, boolean alwaysPrint) {
        double exact = FastMath.logAddExact(a, b);
        double table = logAdd(a, b);
        double diff = Math.abs(table - exact);
        if (table == Double.NEGATIVE_INFINITY && exact == Double.NEGATIVE_INFINITY) {
            diff = 0;
        }
        if (alwaysPrint || !(diff < tol)) {
            System.out.println(String.format("a=%g b=%g (a-b)=%7.2g diff=%7g table=%7g exact=%7g", a, b, (a-b), diff, table, exact));
        }
        assertTrue(diff < tol);
        return diff;
    } 
        
    private double assertCorrectLogSubtract(double a, double b, double tol, boolean alwaysPrint) {
        double exact = FastMath.logSubtractExact(a, b);
        double table = logSubtract(a, b);
        double diff = Math.abs(table - exact);
        if (table == Double.NEGATIVE_INFINITY && exact == Double.NEGATIVE_INFINITY) {
            diff = 0;
        }
        if (alwaysPrint || !(diff < tol)) {
            System.out.println(String.format("a=%g b=%g (a-b)=%7.2g diff=%7g table=%7g exact=%7g", a, b, (a-b), diff, table, exact));
        }
        assertTrue(diff < tol);
        return diff;
    }
    
    @Test
    public void testRangeLogSubtractAfterAddSpecialCase1() {
        // Note: double precision of the mantissa is 53 bits. This equates to ~
        // 16 decimal digits, more exactly: 53 * log_{10}(2) ~= 15.955.
        //
        // Look at a specific case:
        double i=52;
        double j=20;
        assertCorrectLogSubtractAfterLogAdd(i, j, 1e-10, true);
    }
    
    protected void assertCorrectLogSubtractAfterLogAdd(double a, double b, double tolerance, boolean alwaysPrint) {
        double logAddTable = logAdd((double) a, (double) b);
        double logAddExact = FastMath.logAddExact((double) a, (double) b);
        double logAddTableSubTable = logSubtract(logAddTable, (double) a); 
        double logAddTableSubExact = FastMath.logSubtractExact(logAddTable, (double) a); 
        double logAddExactSubTable = logSubtract(logAddExact, (double) a); 
        double logAddExactSubExact = FastMath.logSubtractExact(logAddExact, (double) a);

        double logAddExactDiff = logAddExactSubExact - logAddExactSubTable;
        double logAddTableDiff = logAddTableSubExact - logAddTableSubTable;
        if (alwaysPrint) {
            System.out.println(String.format("tableAdd: a=%g b=%g (a-b)=%7.2g diff=%7g table=%7g exact=%7g", a, b, (a-b), logAddExactDiff, logAddExactSubTable, logAddExactSubExact));
            System.out.println(String.format("exactAdd: a=%g b=%g (a-b)=%7.2g diff=%7g table=%7g exact=%7g", a, b, (a-b), logAddTableDiff, logAddTableSubTable, logAddTableSubExact));
        }
        if (Math.abs(logAddExactDiff) > tolerance
                || Math.abs(logAddTableDiff) > tolerance) {
            System.out.println(String.format("logAddExact=%.50g", logAddExact));
            System.out.println(String.format("logAddTable=%.50g", logAddTable));
            System.out.println(String.format("logAddExactSubTable=%g", logAddExactSubTable));
            System.out.println(String.format("logAddExactSubExact=%g", logAddExactSubExact));
            System.out.println(String.format("logAddTableSubTable=%g", logAddTableSubTable));
            System.out.println(String.format("logAddTableSubExact=%g", logAddTableSubExact));
        }
        assertEquals(logAddExactSubExact, logAddExactSubTable, tolerance);
        assertEquals(logAddTableSubExact, logAddTableSubTable, tolerance);
    }
    
    // Borrowed from Lists class for these tests.
    private static <T> List<T> getList(T... args) {
        return Arrays.asList(args);
    }
    
    /* -------------------- The BELOW tests only print. -------------------- */
    
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
    public void testThatOnlyPrints1() {
        double largestNegDouble = -Double.MAX_VALUE;
        System.out.println("Largest negative double: " + largestNegDouble);
        System.out.println("Log add of largest neg double with itself: " + logAdd(-1, -1));
        System.out.println("Log add of largest neg double with itself: " + logAdd(largestNegDouble, largestNegDouble));
        System.out.println("Min double: " + Double.MIN_VALUE);
        System.out.println("Log of min double: " + Math.log(Double.MIN_VALUE));
        System.out.println("Min 32-bit float: " + -Float.MAX_VALUE); //-3.4E38);
        //System.out.println("Min 32-bit float: " + -3.4E38);
    }
    
    @Test
    public void testThatOnlyPrints2() {
        System.out.println(FastMath.logAddExact(Double.NEGATIVE_INFINITY, 0));
        System.out.println(FastMath.logAddExact(0, 0));
    }
    
    @Test
    public void testFloatingPointPrecision() {
        // Original computation.
        System.out.println(89+23);
        System.out.println(7+145);
        System.out.println(logAdd(7+145, 89+23));
        System.out.println(logSubtract(logAdd(7+145, 89+23), 7+145));
        //
        // Try scaling. (doesn't help.)
        //
        System.out.println(89+23-200);
        System.out.println(7+145-200);
        System.out.println(logAdd(7+145-200, 89+23-200));
        System.out.println(logAdd(7+145-200, 89+23-200)+200);
        System.out.println(logSubtract(logAdd(7+145-200, 89+23-200), 7+145-200)+200);
        
        // Find the point at which logAdd loses precision.
        //double j = 10d;
        for (int j : new int[]{1, 10, 20}) {
            for (boolean useExact : new boolean[]{true, false}) {
                for (int i = j - 40; i < j + 40; i++) {
                    double diff;
                    if (useExact) {
                        diff = FastMath.logSubtractExact(FastMath.logAddExact((double) i, (double) j), (double) i);
                    } else {
                        diff = logSubtract(logAdd((double) i, (double) j), (double) i); 
                    }
                    System.out.println(String.format("exact=%7s j=%2d i=%2d (i-j)=%2d eq0=%7s diff=%g", ""+useExact, j, i, (i-j), 
                            ""+(diff == Double.NEGATIVE_INFINITY), diff));
                    if (diff == Double.NEGATIVE_INFINITY) {
                        //System.out.println(String.format("exact=%7s j=%2d i=%2d (i-j)=%2d eq0=%7s diff=%g", ""+useExact, j, i, (i-j), 
                                //""+(diff == Double.NEGATIVE_INFINITY), diff));
                        break;
                    }
                }
            }
        }
        
    }
    
    /* -------------------- The ABOVE tests only print. -------------------- */
    
}
