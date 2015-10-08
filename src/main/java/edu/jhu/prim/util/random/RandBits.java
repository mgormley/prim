package edu.jhu.prim.util.random;

public class RandBits {
    
    // COMMENTED FOR SPEED: private int count = 0;
    private long bits = 0;
    private long cur = 0;
    
    public boolean nextBit() {
        // COMMENTED FOR SPEED: count++;
        cur = cur << 1L;
        if (cur == 0) {
            bits = Prng.nextLong();
            cur = 1L;
        }
        // COMMENTED FOR SPEED: System.out.printf("count = %6d bits = 0x%x cur = 0x%16x bit = %d\n", count, bits, cur, ((cur & bits) == 0) ? 0 : 1);
        return ((cur & bits) != 0);
    }
    
}
