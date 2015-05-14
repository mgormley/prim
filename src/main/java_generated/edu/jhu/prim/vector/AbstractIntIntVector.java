package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.util.Lambda.FnIntIntToVoid;

public abstract class AbstractIntIntVector {

    public abstract void iterate(FnIntIntToVoid function);
    
    public int getSum() {
        final MutableInt sum = new MutableInt(0);
        this.iterate(new FnIntIntToVoid() {
            public void call(int idx, int val) {
                sum.v += val;
            }
        });
        return sum.v;
    }

    public int getMax() {
        final MutableInt max = new MutableInt(Integer.MIN_VALUE);
        this.iterate(new FnIntIntToVoid() {
            public void call(int idx, int val) {
                if (val > max.v) {
                    max.v = val;
                }
            }
        });
        return max.v;
    }
    
    public int getArgmax() {
        final MutableInt argmax = new MutableInt(-1);
        final MutableInt max = new MutableInt(Integer.MIN_VALUE);
        this.iterate(new FnIntIntToVoid() {
            public void call(int idx, int val) {
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
        this.iterate(new FnIntIntToVoid() {
            public void call(int idx, int val) {
                if (val < min.v) {
                    min.v = val;
                }
            }
        });
        return min.v;
    }
    
    public int getArgmin() {
        final MutableInt argmin = new MutableInt(-1);
        final MutableInt min = new MutableInt(Integer.MAX_VALUE);
        this.iterate(new FnIntIntToVoid() {
            public void call(int idx, int val) {
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
        this.iterate(new FnIntIntToVoid() {
            public void call(int idx, int val) {
                sum.v += val*val;
            }
        });
        return sum.v;
    }
    
    public int getInfNorm() {
        final MutableInt maxAbs = new MutableInt(Integer.MIN_VALUE);
        this.iterate(new FnIntIntToVoid() {
            public void call(int idx, int val) {
                int abs = Math.abs(val);
                if (abs > maxAbs.v) {
                    maxAbs.v = abs;
                }
            }
        });
        return maxAbs.v;
    }
    
}
