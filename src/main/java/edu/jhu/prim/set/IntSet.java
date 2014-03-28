package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.IntArrayIter;
import edu.jhu.prim.iter.IntIter;
import edu.jhu.prim.map.IntDoubleHashMap;

/**
 * Set for int primitives.
 * @author mgormley
 */
public interface IntSet {
        
    void add(int key);
    boolean contains(int key);
    IntIter iterator();
    
}
