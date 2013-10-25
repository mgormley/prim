package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.map.IntDoubleHashMap;

/**
 * Hash set for int primitives.
 * @author mgormley
 */
public class IntHashSet implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private IntDoubleHashMap map = new IntDoubleHashMap();
    
    public void add(int key) {
        map.put(key, 1);
    }

    public boolean contains(int key) {
        return map.contains(key);
    }

}
