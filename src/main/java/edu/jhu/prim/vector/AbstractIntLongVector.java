package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives.MutableLong;
import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.util.Lambda.FnIntLongToVoid;

public abstract class AbstractIntLongVector {

    private static final long serialVersionUID = 1L;
    
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

    public long getProd() {
        final MutableLong prod = new MutableLong(1);
        this.iterate(new FnIntLongToVoid() {
            public void call(int idx, long val) {
                prod.v *= val;
            }
        });
        return prod.v;
    }

    public long getMax() {
        final MutableLong max = new MutableLong(-9223372036854775806l);
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
        final MutableLong max = new MutableLong(-9223372036854775806l);
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
    
    public long getInfNorm() {
        final MutableLong maxAbs = new MutableLong(-9223372036854775806l);
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
