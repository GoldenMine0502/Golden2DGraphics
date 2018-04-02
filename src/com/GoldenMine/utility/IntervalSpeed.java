package com.GoldenMine.utility;

public enum IntervalSpeed {
    VERY_SLOW(0), SLOW(1), NORMAL(2), FAST(3), VERY_FAST(4);
    int speed;

    IntervalSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
