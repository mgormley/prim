package edu.jhu.prim.tuple;

import java.io.Serializable;

public class ComparablePair<X extends Comparable<X>,Y extends Comparable<Y>> extends Pair<X,Y> implements Comparable<ComparablePair<X,Y>>, Serializable {
    private static final long serialVersionUID = -9010917846690758395L;

    public ComparablePair(X x, Y y) {
		super(x, y);
	}

	public int compareTo(ComparablePair<X, Y> p) {
		int xvp1 = get1().compareTo(p.get1());
		if (xvp1 == 0) {
			return get2().compareTo(p.get2());
		}
		return xvp1;
	}
	
}
