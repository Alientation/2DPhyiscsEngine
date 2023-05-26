package com.util;

public class Time {
    public static long timeStarted = System.nanoTime();

    public static float getTimeSeconds() {
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }

    public static float getDeltaTime(long prevTime) {
        return (float) ((System.nanoTime() - prevTime) * 1E-9);
    }

    public static float getDeltaTime(long prevTime, long curTime) {
        return (float) ((curTime - prevTime) * 1E-9);
    }

    public static long getTimeNano() {
        return System.nanoTime() - timeStarted;
    }
}
