package edu.jhu.prim.bimap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.jhu.prim.tuple.Pair;

public class ObjectObjectBimap<A, B> implements Serializable {

    private static final long serialVersionUID = 2170467993453781537L;
    private Map<A, B> map1;
    private Map<B, A> map2;
    private boolean isGrowing;

    public ObjectObjectBimap() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        isGrowing = true;
    }

    public ObjectObjectBimap(int initialCapacity) {
        map1 = new HashMap<>(initialCapacity);
        map2 = new HashMap<>(initialCapacity);
        isGrowing = true;
    }

    public B lookup1(A key) {
        return map1.get(key);
    }

    public A lookup2(B key) {
        return map2.get(key);
    }

    public void put(A a, B b) {
        if (!isGrowing)
            throw new IllegalStateException("not growing");
        B old1 = map1.put(a, b);
        if (old1 != null)
            throw new IllegalArgumentException("a is not unique");
        A old2 = map2.put(b, a);
        if (old2 != null)
            throw new IllegalArgumentException("b is not unique");
    }

    public void clear() {
        if (!isGrowing)
            throw new IllegalStateException("not growing");
        map1.clear();
        map2.clear();
    }

    public int size() {
        return map1.size();
    }

    public void startGrowth() {
        isGrowing = true;
    }

    public void stopGrowth() {
        isGrowing = false;
    }

    public boolean isGrowing() {
        return isGrowing;
    }

    /** Returns a new List with all the entries in this bimap */
    public List<Pair<A, B>> getEntries() {
        List<Pair<A, B>> l = new ArrayList<>(size());
        for (Map.Entry<A, B> x : map1.entrySet())
            l.add(new Pair<>(x.getKey(), x.getValue()));
        return l;
    }
}
