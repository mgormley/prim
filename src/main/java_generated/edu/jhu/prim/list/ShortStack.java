package edu.jhu.prim.list;


public class ShortStack extends ShortArrayList {

    private static final long serialVersionUID = 1L;

    public void push(short value) {
        super.add(value);
    }
    
    public short pop() {
        if (size == 0) {
            throw new IllegalStateException("No element to pop.");
        }
        return elements[--size];
    }

}
