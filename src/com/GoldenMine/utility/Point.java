package com.GoldenMine.utility;

public class Point {
    double x;
    double y;

    int xCal;
    int yCal;

    boolean xChanged = false;
    boolean yChanged = false;

    public Point() {
        this(0,0);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        xChanged = true;
        yChanged = true;
    }

    public int getXInt() {
        if(xChanged) {
            xCal = (int) Math.round(x);
            xChanged = false;
        }
        return xCal;
    }

    public int getYInt() {
        if(yChanged) {
            yCal = (int) Math.round(y);
            yChanged = false;
        }
        return yCal;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
        xChanged = true;
    }

    public void setY(double y) {
        this.y = y;
        yChanged = true;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }
}
