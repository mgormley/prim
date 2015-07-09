package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.LongIter;
import edu.jhu.prim.list.LongArrayList;

/**
 * Set for long primitives.
 * @author mgormley
 */
public interface LongSet extends Serializable {
        
    void add(long key);
    void add(long... keys);
    void add(LongArrayList keys);
    boolean contains(long key);
    LongIter iterator();
    int size();
    
}
