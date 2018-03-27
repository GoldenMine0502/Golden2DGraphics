package com.GoldenMine.utility;

import java.awt.image.BufferedImage;

public class Interval {
    int wait;
    int interval;

    int waitTick;
    int intervalTick;

    boolean completed = false;

    public Interval(int wait, int interval) {
        this.wait = wait;
        this.interval = interval;
    }

    public boolean addTick() {
        intervalTick++;
        //completed = intervalTick >= interval;

        return intervalTick >= interval;
    }

    public boolean addWait() {
        waitTick++;

        completed = waitTick >= wait;

        return completed;
    }

    public void initalize() {
        intervalTick = 0;
    }

    public int getIntervalTick() {
        return intervalTick;
    }

    public int getWaitTick() {
        return waitTick;
    }


    public int getInterval() {
        return interval;
    }

    public int getWait() {
        return wait;
    }

    /* range=0~10000 */
    public double getWaitPercent() {
        return (double)waitTick/wait*10000;
    }

    public double getIntervalPercent() {
        return (double)intervalTick/interval*10000;
    }

    public boolean getCompletedWait() {
        return completed;
    }
}
