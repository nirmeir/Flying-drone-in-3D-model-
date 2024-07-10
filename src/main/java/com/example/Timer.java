package com.example;

public class Timer {

    private long startTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public int getFrame() {
        long currentTime = System.currentTimeMillis();
        return (int) ((currentTime - startTime) / 16); // Assuming 60 FPS, so 1 frame ~ 16 ms
    }
}
