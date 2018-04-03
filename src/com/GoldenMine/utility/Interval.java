package com.GoldenMine.utility;

public class Interval {
    /*
    1차함수 그래프를 적분하면 2차함수가 된다7+
     */

    public static void main(String[] args ) {
        final int LOOP = 1238;

        Interval interval = new Interval(0, LOOP);
        for(int i = 0; i < LOOP; i++) {
            interval.addTick();
            System.out.println(interval.getIntervalPercent() + ", " + interval.getNaturalIntervalPercent());
        }
    }

    int wait;
    int interval;

    int waitTick;
    int intervalTick;

    double naturalInterval;

    boolean waitCompleted = false;

    boolean natural = false;

    public Interval(int wait, int interval) {
        this.wait = wait;
        this.interval = interval;
    }

    public Interval(int wait, int interval, boolean natural) {
        this(wait, interval);
        this.natural = natural;
    }

    public boolean addTick() {
        naturalInterval += getCurrentValue(getInnerIntervalPercent());
        intervalTick++;
        //waitCompleted = intervalTick >= interval;

        //System.out.println(naturalInterval);

        return intervalTick > interval;
    }

    public boolean addWait() {
        waitTick++;

        waitCompleted = waitTick > wait;

        return waitCompleted;
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
        //System.out.println(natural + ", " +((double) intervalTick / interval * 10000D)  + ", " +getNaturalIntervalPercent());
        if(natural) {
            return getNaturalIntervalPercent();
        } else {
            return getInnerIntervalPercent();
        }
    }

    public boolean getCompletedWait() {
        return waitCompleted;
    }


    private static double getCurrentValue(double percent) {
        if(percent<=5000) {
            //1~5000
            //y=1/90 * x
            return 1D/2500D * percent; // percent 5000일때 기울기 2
        } else {
            return 1D/2500D * (10000-percent);
            //5001~10000
            //y=-1/90 * x + 4
            // percent 10000일때 기울기 0
        }
    }

    private double getInnerIntervalPercent() {
        return (double) intervalTick / interval * 10000D;
    }

    private double getNaturalIntervalPercent() {
        return naturalInterval/interval*10000D;
    }

    public boolean getNatural() {
        return natural;
    }
}
