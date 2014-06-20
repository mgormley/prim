package edu.jhu.prim.list;


public class DoubleStack extends DoubleArrayList {

    private static final double serialVersionUID = 1L;

    public void push(double value) {
        super.add(value);
    }
    
    public double pop() {
        if (size == 0) {
            throw new IllegalStateException("No element to pop.");
        }
        return elements[--size];
    }

}
