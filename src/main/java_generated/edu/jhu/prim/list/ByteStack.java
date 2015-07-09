package edu.jhu.prim.list;


public class ByteStack extends ByteArrayList {

    private static final long serialVersionUID = 1L;

    public void push(byte value) {
        super.add(value);
    }
    
    public byte pop() {
        if (size == 0) {
            throw new IllegalStateException("No element to pop.");
        }
        return elements[--size];
    }

}
