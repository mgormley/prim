/**
 * 
 */
package edu.jhu.prim.tuple;

import java.io.Serializable;

public class OrderedPair extends IntTuple implements Serializable {
    private static final long serialVersionUID = -4438111618394922684L;

    public OrderedPair(int facIdx, int varIdx) {
        super(facIdx, varIdx);
    }

    public int get1() {
        return get(0);
    }

    public int get2() {
        return get(1);
    }
}