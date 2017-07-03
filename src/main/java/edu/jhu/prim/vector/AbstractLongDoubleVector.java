package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives.MutableDouble;
import edu.jhu.prim.Primitives.MutableLong;
import edu.jhu.prim.set.LongHashSet;
import edu.jhu.prim.util.Lambda.FnLongDoubleToVoid;

public abstract class AbstractLongDoubleVector {

    public abstract void iterate(FnLongDoubleToVoid function);
    
    public double getSum() {
        final MutableDouble sum = new MutableDouble(0);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                sum.v += val;
            }
        });
        return sum.v;
    }

    public double getMax() {
        final MutableDouble max = new MutableDouble(Double.NEGATIVE_INFINITY);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                if (val > max.v) {
                    max.v = val;
                }
            }
        });
        return max.v;
    }
    
    public long getArgmax() {
        final MutableLong argmax = new MutableLong(-1);
        final MutableDouble max = new MutableDouble(Double.NEGATIVE_INFINITY);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                if (val > max.v) {
                    argmax.v = idx;
                    max.v = val;
                }
            }
        });
        return argmax.v;
    }
    
    public double getMin() {
        final MutableDouble min = new MutableDouble(Double.POSITIVE_INFINITY);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                if (val < min.v) {
                    min.v = val;
                }
            }
        });
        return min.v;
    }
    
    public long getArgmin() {
        final MutableLong argmin = new MutableLong(-1);
        final MutableDouble min = new MutableDouble(Double.POSITIVE_INFINITY);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                if (val < min.v) {
                    argmin.v = idx;
                    min.v = val;
                }
            }
        });
        return argmin.v;
    }
    
    public double getL2Norm() {
        final MutableDouble sum = new MutableDouble(0);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                sum.v += val*val;
            }
        });
        return sum.sqrt();
    }
    
    public double getInfNorm() {
        final MutableDouble maxAbs = new MutableDouble(Double.NEGATIVE_INFINITY);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                double abs = Math.abs(val);
                if (abs > maxAbs.v) {
                    maxAbs.v = abs;
                }
            }
        });
        return maxAbs.v;
    }
    
}
