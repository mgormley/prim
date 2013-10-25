package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.map.LongDoubleHashMap;

/**
 * Hash set for long primitives.
 * @author mgormley
 */
public class LongHashSet implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private LongDoubleHashMap map = new LongDoubleHashMap();

    public void add(long key) {
        map.put(key, 1);
    }

    public boolean contains(long key) {
        return map.contains(key);
    }

}
