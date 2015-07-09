package edu.jhu.prim.arrays;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import edu.jhu.prim.util.DoubleJUnitUtils;

public class MultinomialsTest {

    @Test
    public void testNormalize() {
        {
            double[] p = new double[] { 0.2 * 2, 0.3 * 2, 0.5 * 2 };
            Multinomials.normalizeProps(p);
            DoubleJUnitUtils.assertArrayEquals(new double[] { 0.2, 0.3, 0.5 }, p, 1e-13);
        }
        {
            double[] p = new double[]{0.0, 0.0, 0.0, 0.0};
            Multinomials.normalizeProps(p);
            DoubleJUnitUtils.assertArrayEquals(new double[]{0.25, 0.25, 0.25, 0.25}, p, 1e-13);
        }
        {
            double[] p = new double[]{0.1, 0.1, 0.1, Double.POSITIVE_INFINITY};
            Multinomials.normalizeProps(p);
            DoubleJUnitUtils.assertArrayEquals(new double[]{0.0, 0.0, 0.0, 1.0}, p, 1e-13);
        }
        {
            double[] p = new double[]{0.1, Double.POSITIVE_INFINITY, 0.1, Double.POSITIVE_INFINITY};
            Multinomials.normalizeProps(p);
            DoubleJUnitUtils.assertArrayEquals(new double[]{0.0, 0.5, 0.0, 0.5}, p, 1e-13);
        }
    }
    
    @Test
    public void testLogNormalize() {
        {
            double[] p = DoubleArrays.getLog(new double[] { 0.2 * 2, 0.3 * 2, 0.5 * 2 });            
            Multinomials.normalizeLogProps(p);
            DoubleJUnitUtils.assertArrayEquals(DoubleArrays.getLog(new double[] { 0.2, 0.3, 0.5 }), p, 1e-13);
        }
        {
            double[] p = DoubleArrays.getLog(new double[] { 0.0, 0.0, 0.0, 0.0 });            
            Multinomials.normalizeLogProps(p);
            DoubleJUnitUtils.assertArrayEquals(DoubleArrays.getLog(new double[] { 0.25, 0.25, 0.25, 0.25 }), p, 1e-13);
        }
        {
            double[] p = DoubleArrays.getLog(new double[] { 0.1, 0.1, 0.1, Double.POSITIVE_INFINITY });            
            Multinomials.normalizeLogProps(p);
            DoubleJUnitUtils.assertArrayEquals(DoubleArrays.getLog(new double[] { 0.0, 0.0, 0.0, 1.0 }), p, 1e-13);
        }
        {
            double[] p = DoubleArrays.getLog(new double[] { 0.1, Double.POSITIVE_INFINITY, 0.1, Double.POSITIVE_INFINITY });          
            System.out.println(Arrays.toString(p));
            Multinomials.normalizeLogProps(p);
            DoubleJUnitUtils.assertArrayEquals(DoubleArrays.getLog(new double[] { 0.0, 0.5, 0.0, 0.5 }), p, 1e-13);
        }
    }
    
    @Test
    public void testKLDivergence() {
        double[] p = new double[]{0.2, 0.3, 0.5};
        double[] q = new double[]{0.1, 0.4, 0.5};
        
        assertEquals(0.05232481437645474, Multinomials.klDivergence(p, q), 1e-10);
        assertEquals(0.2 * Math.log(0.2 / 0.1) + 0.3 * Math.log(0.3 / 0.4) + 0.5 * Math.log(0.5 / 0.5), 
                Multinomials.klDivergence(p, q), 1e-10);
    }

}
