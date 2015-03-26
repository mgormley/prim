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
   
    protected double getToleranceForLogAdd() {
        return 1e-11;
    }
    
    protected double getToleranceForLogSubtract() {
        return 1e-11;
    }
    
}
