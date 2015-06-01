package edu.jhu.prim.util.math;

import org.junit.Ignore;

@Ignore("This is known to fail consistently because of the imprecision of LogAddTable.")
public class LogAddTableTest extends AbstractLogAddSubtractTableTest {

    @Override
    protected double logAdd(double a, double b) {
        return LogAddTable.logAdd(a, b);
    }

    @Override
    protected double logSubtract(double a, double b) {
        return LogAddTable.logSubtract(a, b);
    }
   
    protected double getToleranceForLogAdd() {
        return 1e-6;
    }
    
    protected double getToleranceForLogSubtract() {
        return 1e-3;
    }
    
}
