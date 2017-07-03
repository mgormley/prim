package edu.jhu.prim.tuple;

import java.util.Comparator;
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

	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> int compareTo(Pair<T1, T2> lhs, Pair<T1, T2> rhs) { 
	    int xvp1 = lhs.get1().compareTo(rhs.get1());
	    if (xvp1 == 0) {
	        return lhs.get2().compareTo(rhs.get2());
	    }
	    return xvp1;
	}
	
	/**
	 * creates a natural order for pairs of comparable things
	 * @return
	 */
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> Comparator<Pair<T1, T2>> naturalOrder() {
        return new Comparator<Pair<T1,T2>>() {

            @Override
            public int compare(Pair<T1, T2> lhs, Pair<T1, T2> rhs) {
                return compareTo(lhs, rhs);
            }

        };
	}

}
