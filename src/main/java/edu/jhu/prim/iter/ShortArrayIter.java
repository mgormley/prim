package edu.jhu.prim.iter;

public class ShortArrayIter implements ShortIter {

    private final short[] values;
    private int cur;
        
    /** 
     * Constructs a new ShortIter which ranges from start (inclusive) to end (exclusive) by steps of incr.
     * 
     * @param values The values over which to iterate.
     * @param incr The increment value.
     */
    public ShortArrayIter(short... values) {
        this.values = values;
        this.cur = 0;
    }
    
    @Override
    public short next() {
        if (!hasNext()) {
            throw new IllegalStateException();
        }
        return values[cur++];
    }

    @Override
    public boolean hasNext() {
        return cur < values.length;
    }

    @Override
    public void reset() {
        this.cur = 0;
    }
    
}
