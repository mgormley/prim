package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.Primitives.MutableLong;
import edu.jhu.prim.util.Lambda.FnLongIntToVoid;

public abstract class AbstractLongIntVector {

    private static final long serialVersionUID = 1L;
    
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

    public int getProd() {
        final MutableInt prod = new MutableInt(1);
        this.iterate(new FnLongIntToVoid() {
            public void call(long idx, int val) {
                prod.v *= val;
            }
        });
        return prod.v;
    }

    public int getMax() {
        final MutableInt max = new MutableInt(-2147483646);
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
        final MutableInt max = new MutableInt(-2147483646);
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
    
    public int getInfNorm() {
        final MutableInt maxAbs = new MutableInt(-2147483646);
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
