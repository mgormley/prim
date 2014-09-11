package edu.jhu.util;

import java.io.Serializable;

public class Timer implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private boolean isRunning;
    private long startTime;
    private int numStarts;
    private long totMs;
    // The sum of the squares of the splits.
    private long sumSqMs;
    // The maximum split time.
    private long maxSplitMs;
    
    public Timer() {
        reset();
    }

    public void reset() {
        numStarts = 0;
        totMs = 0;
        startTime = 0;
        isRunning = false;
        sumSqMs = 0;
        maxSplitMs = 0;
    }

    public void start() {
        if (isRunning == true) {
            throw new IllegalStateException("Timer is already running");
        }
        startTime = System.currentTimeMillis();
        isRunning = true;
        numStarts++;
    }

    public void stop() {
        if (isRunning != true) {
            throw new IllegalStateException("Timer is not running");
        }
        long split = elapsedSinceLastStart();
        totMs += split;
        sumSqMs += split*split;
        if (split > maxSplitMs) { maxSplitMs = split; }
        isRunning = false;
    }

    public void split() {
        stop();
        start();
    }
    
    public long elapsedSinceLastStart() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Gets the number of times that timer has been started or split.
     */
    public int getCount() {
        return numStarts;
    }

    /**
     * Queries whether or not the timer is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Gets the total number of milliseconds.
     */
    public double totMs() {
        if (isRunning) {
            return totMs + elapsedSinceLastStart();
        } else {
            return totMs;
        }
    }

    /**
     * Gets the average number of milliseconds.
     */
    public double avgMs() {
        return totMs() / numStarts;
    }

    /**
     * Gets the total number of seconds.
     */
    public double totSec() {
        return totMs() / 1000.0;
    }

    /**
     * Gets the average number of seconds.
     */
    public double avgSec() {
        return totMs() / numStarts / 1000.0;
    }

    public double maxSplitMs() {
        return maxSplitMs;
    }
    
    public double maxSplitSec() {
        return maxSplitMs / 1000.0;
    }
    
    public double stdDevMs() {
        double avgMs = avgMs();
        return Math.sqrt(numStarts / (numStarts - 1.0) * (sumSqMs / numStarts - avgMs*avgMs));
    }
    
    public double stdDevSec() {
        return stdDevMs() / 1000.0;
    }

    public static String durAsStr(double milliseconds) {
        long timeInSeconds = (long)(milliseconds / 1000.0);
        long hours, minutes, seconds;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = timeInSeconds;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /** Adds the stats from the other timer to this one. (Threadsafe) */
    public synchronized void add(Timer other) {
        this.numStarts += other.numStarts;
        this.totMs += other.totMs;
        this.maxSplitMs = Math.max(this.maxSplitMs, other.maxSplitMs);
        this.sumSqMs += other.sumSqMs;
    }

}
