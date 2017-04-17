package edu.jhu.prim.tuple;

import java.io.Serializable;

import edu.jhu.prim.util.SafeEquals;

public class Triple<X,Y,Z> implements Serializable {
    private static final long serialVersionUID = -6935430631568159681L;

    private X x;
	private Y y;
	private Z z;
	
	public Triple(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public X get1() { 
		return x;
	}
	public Y get2() {
		return y;
	}
	
	public Z get3() {
		return z;
	}
	
	@Override
	public String toString() {
	    return String.format("<%s, %s, %s>", x,y,z);
	}
	
	@Override
	public boolean equals(Object o) { 
		if (o instanceof Triple<?,?,?>) {
			Triple<?,?,?> p = (Triple<?,?,?>)o;
			if (SafeEquals.safeEquals(x, p.get1()) &&
					SafeEquals.safeEquals(y, p.get2()) &&
					SafeEquals.safeEquals(z, p.get3())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37*result + (x == null ? 0 : x.hashCode());
		result = 37*result + (y == null ? 0 : y.hashCode());
		result = 37*result + (z == null ? 0 : z.hashCode());
		return result;
	}
    
}
