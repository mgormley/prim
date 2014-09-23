package edu.jhu.prim.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class SafeCastTest {

    @Test
    public void testIntToShort() {
        assertEquals((short)10, SafeCast.safeIntToShort(10));
        assertEquals((short)-10, SafeCast.safeIntToShort(-10));
        assertEquals(Short.MAX_VALUE, SafeCast.safeIntToShort(Short.MAX_VALUE));
        assertEquals(Short.MIN_VALUE, SafeCast.safeIntToShort(Short.MIN_VALUE));
    }

    @Test(expected = IllegalStateException.class)
    public void testIntToShortOverflow() {
        SafeCast.safeIntToShort(Short.MAX_VALUE+1);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIntToShortUnderflow() {
        SafeCast.safeIntToShort(Short.MIN_VALUE-1);
    }

    @Test
    public void testIntToByte() {
        assertEquals((byte)10, SafeCast.safeIntToByte(10));
        assertEquals((byte)-10, SafeCast.safeIntToByte(-10));
        assertEquals(Byte.MAX_VALUE, SafeCast.safeIntToByte(Byte.MAX_VALUE));
        assertEquals(Byte.MIN_VALUE, SafeCast.safeIntToByte(Byte.MIN_VALUE));
    }

    @Test(expected = IllegalStateException.class)
    public void testIntToByteOverflow() {
        SafeCast.safeIntToByte(Byte.MAX_VALUE+1);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIntToByteUnderflow() {
        SafeCast.safeIntToByte(Byte.MIN_VALUE-1);
    }
    
    @Test
    public void testIntToUnsignedShort() {
        assertEquals((short)10, SafeCast.safeIntToUnsignedShort(10));
        assertEquals(Short.MAX_VALUE, SafeCast.safeIntToUnsignedShort(Short.MAX_VALUE));
        assertEquals((short) -1, SafeCast.safeIntToUnsignedShort((int) (Math.pow(2, 16)-1)));
        assertEquals((int) 65535, SafeCast.safeIntToUnsignedShort((int) (Math.pow(2, 16)-1)) & 0xffff );
        assertEquals(0, SafeCast.safeIntToUnsignedShort(0));
    }

    @Test(expected = IllegalStateException.class)
    public void testIntToUnsignedShortOverflow() {
        SafeCast.safeIntToUnsignedShort((int) (Math.pow(2, 16)));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIntToUnsignedShortUnderflow() {
        SafeCast.safeIntToUnsignedShort(-1);
    }
        
    @Test
    public void testIntToUnsignedByte() {
        assertEquals((byte)10, SafeCast.safeIntToUnsignedByte(10));
        assertEquals(Byte.MAX_VALUE, SafeCast.safeIntToUnsignedByte(Byte.MAX_VALUE));
        assertEquals((byte) -1, SafeCast.safeIntToUnsignedByte((int) (Math.pow(2, 8)-1)));
        assertEquals((int) 255, SafeCast.safeIntToUnsignedByte((int) (Math.pow(2, 8)-1)) & 0xff );
        assertEquals(0, SafeCast.safeIntToUnsignedByte(0));
    }

    @Test(expected = IllegalStateException.class)
    public void testIntToUnsignedByteOverflow() {
        SafeCast.safeIntToUnsignedByte((int) (Math.pow(2, 16)));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIntToUnsignedByteUnderflow() {
        SafeCast.safeIntToUnsignedByte(-1);
    }
    
    @Test 
    public void testUnsignedShortToInt() {
        assertEquals(1, SafeCast.safeUnsignedShortToInt((short)1));
        assertEquals((int) 65535, SafeCast.safeUnsignedShortToInt((short)-1));
    }
    
    @Test 
    public void testUnsignedByteToInt() {
        assertEquals(1, SafeCast.safeUnsignedByteToInt((byte)1));
        assertEquals((int) 255, SafeCast.safeUnsignedByteToInt((byte)-1));
    }

}
