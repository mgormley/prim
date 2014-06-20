package edu.jhu.prim.tuple;

import java.util.Arrays;

public class Tuple {
    
    private final Object[] x;
    
    public Tuple(Object... args) {
        x = args;
    }
    
    public int size() {
        return x.length;
    }
    
    public Object get(int i) {
        return x[i];
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(x);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tuple other = (Tuple) obj;
        if (!Arrays.equals(x, other.x))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(x);
    }
    
}