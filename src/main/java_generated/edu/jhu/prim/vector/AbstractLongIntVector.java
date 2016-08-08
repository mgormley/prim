package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.Primitives.MutableLong;
import edu.jhu.prim.util.Lambda.FnLongIntToVoid;

public abstract class AbstractLongIntVector {

    public abstract void iterate(FnLongIntToVoid function);
    
    public int getSum() {
        final MutableInt sum = new MutableInt(0);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                sum.v += val;
            }
        });
        return sum.v;
    }

    public int getMax() {
        final MutableInt max = new MutableInt(Integer.MIN_VALUE);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                if (val > max.v) {
                    max.v = val;
                }
            }
        });
        return max.v;
    }
    
    public long getArgmax() {
        final MutableLong argmax = new MutableLong(-1);
        final MutableInt max = new MutableInt(Integer.MIN_VALUE);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                if (val > max.v) {
                    argmax.v = idx;
                    max.v = val;
                }
            }
        });
        return argmax.v;
    }
    
    public int getMin() {
        final MutableInt min = new MutableInt(Integer.MAX_VALUE);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                if (val < min.v) {
                    min.v = val;
                }
            }
        });
        return min.v;
    }
    
    public long getArgmin() {
        final MutableLong argmin = new MutableLong(-1);
        final MutableInt min = new MutableInt(Integer.MAX_VALUE);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                if (val < min.v) {
                    argmin.v = idx;
                    min.v = val;
                }
            }
        });
        return argmin.v;
    }
    
    public int getL2Norm() {
        final MutableInt sum = new MutableInt(0);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                sum.v += val*val;
            }
        });
        return sum.sqrt();
    }
    
    public int getInfNorm() {
        final MutableInt maxAbs = new MutableInt(Integer.MIN_VALUE);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                int abs = Math.abs(val);
                if (abs > maxAbs.v) {
                    maxAbs.v = abs;
                }
            }
        });
        return maxAbs.v;
    }
    
}
