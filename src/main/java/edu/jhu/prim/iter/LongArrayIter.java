package edu.jhu.prim.iter;

public class LongArrayIter implements LongIter {

    private final long[] values;
    private int cur;
        
    /** 
     * Constructs a new LongIter which ranges from start (inclusive) to end (exclusive) by steps of incr.
     * 
     * @param values The values over which to iterate.
     * @param incr The increment value.
     */
    public LongArrayIter(long... values) {
        this.values = values;
        this.cur = 0;
    }
    
    @Override
    public long next() {
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
