package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.IntArrayIter;
import edu.jhu.prim.iter.IntIter;
import edu.jhu.prim.map.IntDoubleHashMap;

/**
 * Hash set for int primitives.
 * @author mgormley
 */
public class IntHashSet implements IntSet, Serializable {
    
    private static final long serialVersionUID = 1L;
    private IntDoubleHashMap map;
    
    public IntHashSet() {
        this.map = new IntDoubleHashMap();
    }
    
    public IntHashSet(IntHashSet other) {
        this.map = new IntDoubleHashMap(other.map);
    }
    
    public void add(int key) {
        map.put(key, 1);
    }

    public boolean contains(int key) {
        return map.contains(key);
    }

    public IntIter iterator() {
        return new IntArrayIter(map.getIndices());
    }
    
}
