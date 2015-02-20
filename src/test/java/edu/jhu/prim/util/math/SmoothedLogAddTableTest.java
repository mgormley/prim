package edu.jhu.prim.util.math;

public class SmoothedLogAddTableTest extends AbstractLogAddSubtractTableTest {

    @Override
    protected double logAdd(double a, double b) {
        return SmoothedLogAddTable.logAdd(a, b);
    }

    @Override
    protected double logSubtract(double a, double b) {
        return SmoothedLogAddTable.logSubtract(a, b);
    }
   
    protected double getToleranceForLogAddRandomTest() {
        return 1e-6;
    }
    
    protected double getToleranceForLogSubtractRandomTest() {
        return 1e-3;
    }
    
}
