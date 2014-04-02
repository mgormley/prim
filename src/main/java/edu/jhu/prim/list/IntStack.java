package edu.jhu.prim.list;


public class IntStack extends IntArrayList {

    private static final long serialVersionUID = 1L;

    public void push(int value) {
        super.add(value);
    }
    
    public int pop() {
        if (size == 0) {
            throw new IllegalStateException("No element to pop.");
        }
        return elements[--size];
    }

}
