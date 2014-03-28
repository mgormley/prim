package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.LongIter;

/**
 * Set for long primitives.
 * @author mgormley
 */
public interface LongSet extends Serializable {
        
    void add(long key);
    void add(long... keys);
    boolean contains(long key);
    LongIter iterator();
    
}
