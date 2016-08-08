package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives.MutableLong;
import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.util.Lambda.FnIntLongToVoid;

public abstract class AbstractIntLongVector {

    public abstract void iterate(FnIntLongToVoid function);
    
    public long getSum() {
        final MutableLong sum = new MutableLong(0);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                sum.v += val;
            }
        });
        return sum.v;
    }

    public long getMax() {
        final MutableLong max = new MutableLong(Long.MIN_VALUE);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                if (val > max.v) {
                    max.v = val;
                }
            }
        });
        return max.v;
    }
    
    public int getArgmax() {
        final MutableInt argmax = new MutableInt(-1);
        final MutableLong max = new MutableLong(Long.MIN_VALUE);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                if (val > max.v) {
                    argmax.v = idx;
                    max.v = val;
                }
            }
        });
        return argmax.v;
    }
    
    public long getMin() {
        final MutableLong min = new MutableLong(Long.MAX_VALUE);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                if (val < min.v) {
                    min.v = val;
                }
            }
        });
        return min.v;
    }
    
    public int getArgmin() {
        final MutableInt argmin = new MutableInt(-1);
        final MutableLong min = new MutableLong(Long.MAX_VALUE);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                if (val < min.v) {
                    argmin.v = idx;
                    min.v = val;
                }
            }
        });
        return argmin.v;
    }
    
    public long getL2Norm() {
        final MutableLong sum = new MutableLong(0);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                sum.v += val*val;
            }
        });
        return sum.sqrt();
    }
    
    public long getInfNorm() {
        final MutableLong maxAbs = new MutableLong(Long.MIN_VALUE);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                long abs = Math.abs(val);
                if (abs > maxAbs.v) {
                    maxAbs.v = abs;
                }
            }
        });
        return maxAbs.v;
    }
    
}
