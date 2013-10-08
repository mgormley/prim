package edu.jhu.prim.vector;

import edu.jhu.prim.util.Lambda.FnLongDoubleToDouble;
import edu.jhu.prim.util.Lambda.LambdaBinOpDouble;

public class SparseBinaryOpApplier implements FnLongDoubleToDouble {
    
    private LongDoubleVector modifiedVector;
    private LambdaBinOpDouble lambda;
    
    public SparseBinaryOpApplier(LongDoubleVector modifiedVector, LambdaBinOpDouble lambda) {
        this.modifiedVector = modifiedVector;
        this.lambda = lambda;
    }
    
    public double call(long idx, double val) {
        modifiedVector.set(idx, lambda.call(modifiedVector.get(idx), val));
        return val;
    }
    
}