package edu.jhu.prim.tuple;

import java.io.Serializable;

public class ComparableTriple<X extends Comparable<X>, Y extends Comparable<Y>, Z extends Comparable<Z>> extends Triple<X, Y, Z>
        implements Comparable<ComparableTriple<X, Y, Z>>, Serializable {
    private static final long serialVersionUID = -5844901187236720835L;

    public ComparableTriple(X x, Y y, Z z) {
		super(x, y, z);
	}

	public int compareTo(ComparableTriple<X, Y, Z> p) {
		int diff = get1().compareTo(p.get1());
		if (diff == 0) {
			diff = get2().compareTo(p.get2());
		}
		if (diff == 0) {
		    diff = get3().compareTo(p.get3());
		}
		return diff;
	}

}
