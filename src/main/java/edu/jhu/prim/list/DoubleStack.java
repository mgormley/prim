package edu.jhu.prim.list;


public class DoubleStack extends LongArrayList {

    private static final long serialVersionUID = 1L;

    public void push(long value) {
        super.add(value);
    }
    
    public long pop() {
        if (size == 0) {
            throw new IllegalStateException("No element to pop.");
        }
        return elements[--size];
    }

}
