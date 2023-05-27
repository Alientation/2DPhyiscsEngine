package com.util;

public class Time {
    public static final long NANOSECONDS_IN_SECOND = 1_000_000_000;
    public static final long NANOSECONDS_IN_MILLISECOND = 1_000_000;
    public static final double SECONDS_TO_NANOSECOND = 1E-9;
    public static long timeStarted = System.nanoTime();

    public static float getTimeSeconds() {
        return convertNanoToSeconds(System.nanoTime() - timeStarted);
    }

    public static float getDeltaTime(long prevTime) {
        return convertNanoToSeconds(System.nanoTime() - prevTime);
    }

    public static float getDeltaTime(long prevTime, long curTime) {
        return convertNanoToSeconds(curTime - prevTime);
    }

    public static long getTimeNano() {
        return System.nanoTime() - timeStarted;
    }

    public static float convertNanoToSeconds(long nanoTime) {
        return (float) (nanoTime * SECONDS_TO_NANOSECOND);
    }

    public static float convertNanoToMilliseconds(long nanoTime) {
        return (float) (nanoTime * SECONDS_TO_NANOSECOND / NANOSECONDS_IN_MILLISECOND);
    }
}
