package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.LongArrayIter;
import edu.jhu.prim.iter.LongIter;
import edu.jhu.prim.map.LongDoubleHashMap;

/**
 * Set for long primitives.
 * @author mgormley
 */
public interface LongSet {
        
    void add(long key);
    boolean contains(long key);
    LongIter iterator();
    
}
