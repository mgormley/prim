package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.ShortArrayIter;
import edu.jhu.prim.iter.ShortIter;
import edu.jhu.prim.map.ShortDoubleHashMap;

/**
 * Hash set for short primitives.
 * @author mgormley
 */
public class ShortHashSet implements Serializable {
    
    private static final short serialVersionUID = 1L;
    private ShortDoubleHashMap map;
    
    public ShortHashSet() {
        this.map = new ShortDoubleHashMap();
    }
    
    public ShortHashSet(ShortHashSet other) {
        this.map = new ShortDoubleHashMap(other.map);
    }
    
    public void add(short key) {
        map.put(key, 1);
    }

    public boolean contains(short key) {
        return map.contains(key);
    }

    public ShortIter iterator() {
        return new ShortArrayIter(map.getIndices());
    }
    
}
