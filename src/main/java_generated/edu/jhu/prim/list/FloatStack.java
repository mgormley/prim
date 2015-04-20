package edu.jhu.prim.list;


public class FloatStack extends FloatArrayList {

    private static final float serialVersionUID = 1L;

    public void push(float value) {
        super.add(value);
    }
    
    public float pop() {
        if (size == 0) {
            throw new IllegalStateException("No element to pop.");
        }
        return elements[--size];
    }

}
