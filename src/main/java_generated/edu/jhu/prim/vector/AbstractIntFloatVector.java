package edu.jhu.prim.vector;

import edu.jhu.prim.Primitives.MutableFloat;
import edu.jhu.prim.Primitives.MutableInt;
import edu.jhu.prim.util.Lambda.FnIntFloatToVoid;

public abstract class AbstractIntFloatVector {

    public abstract void iterate(FnIntFloatToVoid function);
    
    public float getSum() {
        final MutableFloat sum = new MutableFloat(0);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                sum.v += val;
            }
        });
        return sum.v;
    }

    public float getProd() {
        final MutableFloat prod = new MutableFloat(1);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                prod.v *= val;
            }
        });
        return prod.v;
    }

    public float getMax() {
        final MutableFloat max = new MutableFloat(Float.NEGATIVE_INFINITY);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                if (val > max.v) {
                    max.v = val;
                }
            }
        });
        return max.v;
    }
    
    public int getArgmax() {
        final MutableInt argmax = new MutableInt(-1);
        final MutableFloat max = new MutableFloat(Float.NEGATIVE_INFINITY);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                if (val > max.v) {
                    argmax.v = idx;
                    max.v = val;
                }
            }
        });
        return argmax.v;
    }
    
    public float getMin() {
        final MutableFloat min = new MutableFloat(Float.POSITIVE_INFINITY);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                if (val < min.v) {
                    min.v = val;
                }
            }
        });
        return min.v;
    }
    
    public int getArgmin() {
        final MutableInt argmin = new MutableInt(-1);
        final MutableFloat min = new MutableFloat(Float.POSITIVE_INFINITY);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                if (val < min.v) {
                    argmin.v = idx;
                    min.v = val;
                }
            }
        });
        return argmin.v;
    }
    
    public float getL2Norm() {
        final MutableFloat sum = new MutableFloat(0);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                sum.v += val*val;
            }
        });
        return sum.v;
    }
    
    public float getInfNorm() {
        final MutableFloat maxAbs = new MutableFloat(Float.NEGATIVE_INFINITY);
        this.iterate(new FnIntFloatToVoid() {
            public void call(int idx, float val) {
                float abs = Math.abs(val);
                if (abs > maxAbs.v) {
                    maxAbs.v = abs;
                }
            }
        });
        return maxAbs.v;
    }
    
}
