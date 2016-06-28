package edu.jhu.prim.set;

import edu.jhu.prim.iter.LongArrayIter;
import edu.jhu.prim.iter.LongIter;
import edu.jhu.prim.list.LongArrayList;
import edu.jhu.prim.map.LongDoubleHashMap;

/**
 * Hash set for long primitives.
 * @author mgormley
 */
public class LongHashSet implements LongSet {
    
    private static final long serialVersionUID = 1L;
    private LongDoubleHashMap map;
    
    public LongHashSet() {
        this.map = new LongDoubleHashMap();
    }
    
    public LongHashSet(int expectedSize) {
        this.map = new LongDoubleHashMap(expectedSize);
    }
    
    public LongHashSet(LongHashSet other) {
        this.map = new LongDoubleHashMap(other.map);
    }
    
    public LongHashSet(LongArrayList list) {
        this();        
        add(list);
    }

    public static LongHashSet fromArray(long... keys) {
        LongHashSet set = new LongHashSet();
        set.add(keys);
        return set;
    }
    
    public void add(long key) {
        map.put(key, 1);
    }

    public void add(long... keys) {
        for (long key : keys) {
            this.add(key);
        }
    }

    public void add(LongArrayList list) {
        long[] arr = list.getInternalElements();
        for (int i=0; i<list.size(); i++) {
            this.add(arr[i]);
        }
    }

    public boolean contains(long key) {
        return map.contains(key);
    }

    public LongIter iterator() {
        return new LongArrayIter(map.getIndices());
    }

    public int size() {
        return map.size();
    }
    
    public long[] toNativeArray() {
        return map.getIndices();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LongHashSet other = (LongHashSet) obj;
        if (map == null) {
            if (other.map != null)
                return false;
        } else if (!map.equals(other.map))
            return false;
        return true;
    }
    
}
