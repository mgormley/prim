package edu.jhu.prim.set;

import java.io.Serializable;

import edu.jhu.prim.iter.IntIter;
import edu.jhu.prim.list.IntArrayList;

/**
 * Set for int primitives.
 * @author mgormley
 */
public interface IntSet extends Serializable {
        
    void add(int key);
    void add(int... keys);
    void add(IntArrayList keys);
    boolean contains(int key);
    IntIter iterator();
    int size();
    
}
