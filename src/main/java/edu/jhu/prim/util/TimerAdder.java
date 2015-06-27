package edu.jhu.prim.util;

/**
 * This class provides a threadsafe method of timing a block of code with a shared timer. The usage
 * example below assumes that no other threads will be accessing the <code>sharedTimer</code> except
 * via {@link TimerAdder}s.
 * 
 * <pre>
 * <code>
 * Timer sharedTimer = new Timer();
 * try (TimerAdder t = new TimerAdder(sharedTimer) {
 *    // Code to be timed in here...
 * }
 * </code>
 * </pre>
 * 
 * @author mgormley
 */
public class TimerAdder implements AutoCloseable {

    private Timer addend;
    private Timer total;
    
    public TimerAdder(Timer t) {
        this.total = t;
        this.addend = new Timer();
        addend.start();
    }
    
    @Override
    public void close() {
        addend.stop();
        total.add(addend);
    }

}
