package edu.jhu.prim.vector;

import edu.jhu.prim.util.Lambda.FnLongDoubleToVoid;

public abstract class AbstractLongDoubleVector {

    // TODO: Move this to a different location.
    private static class MutableLong {
        public long v;
        public MutableLong() { }
        public MutableLong(long v) { this.v = v; }
    }

    private static class MutableDouble {
        public double v;
        public MutableDouble() { }
        public MutableDouble(double v) { this.v = v; }
    }

    private static final long serialVersionUID = 1L;
    
    public abstract void iterate(FnLongDoubleToVoid function);
    
    public double getSum() {
        final MutableDouble sum = new MutableDouble(0.0);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                sum.v += val;
            }
        });
        return sum.v;
    }

    public double getProd() {
        final MutableDouble prod = new MutableDouble(1.0);
        this.iterate(new FnLongDoubleToVoid() {
            public void call(long idx, double val) {
                prod.v *= val;
            }
        });
        return prod.v;
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
