package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.LongArrayIter;
import edu.jhu.prim.iter.LongIter;
import edu.jhu.prim.map.LongDoubleHashMap;

/**
 * Hash set for long primitives.
 * @author mgormley
 */
public class LongHashSet implements LongSet, Serializable {
    
    private static final long serialVersionUID = 1L;
    private LongDoubleHashMap map;
    
    public LongHashSet() {
        this.map = new LongDoubleHashMap();
    }
    
    public LongHashSet(LongHashSet other) {
        this.map = new LongDoubleHashMap(other.map);
    }
    
    public void add(long key) {
        map.put(key, 1);
    }

    public boolean contains(long key) {
        return map.contains(key);
    }

    public LongIter iterator() {
        return new LongArrayIter(map.getIndices());
    }
    
}
