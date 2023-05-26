package com.util;

public class Time {
    public static final long NANOSECONDS_IN_SECOND = 1_000_000_000;
    public static final long NANOSECONDS_IN_MILLISECOND = 1_000_000;
    public static final double SECONDS_TO_NANOSECOND = 1E-9;
    public static long timeStarted = System.nanoTime();

    public static float getTimeSeconds() {
        return (float) ((System.nanoTime() - timeStarted) * SECONDS_TO_NANOSECOND);
    }

    public static float getDeltaTime(long prevTime) {
        return (float) ((System.nanoTime() - prevTime) * SECONDS_TO_NANOSECOND);
    }

    public static float getDeltaTime(long prevTime, long curTime) {
        return (float) ((curTime - prevTime) * SECONDS_TO_NANOSECOND);
    }

    public static long getTimeNano() {
        return System.nanoTime() - timeStarted;
    }
}
