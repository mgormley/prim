package edu.jhu.prim.arrays;

import java.util.Arrays;

/**
 * Utility methods and math for Object arrays of varying dimensionalities.
 * 
 * @author mgormley
 */
public class ObjectArrays {

    private ObjectArrays() {
        // private constructor
    }
    
    public static void fill(final Object[] array, final Object value) {
        for (int i=0; i<array.length; i++) {
            array[i] = value;
        }
    }
    
    public static void fill(Object[][] array, Object value) {
        for (int i=0; i<array.length; i++) {
            Arrays.fill(array[i], value);
        }
    }

    public static void fill(Object[][][] array, Object value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

    public static void fill(Object[][][][] array, Object value) {
        for (int i=0; i<array.length; i++) {
            fill(array[i], value);
        }
    }

}
