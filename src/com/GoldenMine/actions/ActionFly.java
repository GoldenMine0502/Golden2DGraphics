package com.GoldenMine.actions;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class ActionFly implements IAction {
    /*
    private static List<Double> regex = new ArrayList<>(); //거리가 100일 때, 가야할 거리

    static {
        double sum = 0;
        for(int i = 0; i < 10000; i++) {
            double value = get(i/1000, 10);
            regex.add(sum);
            sum+=value;
            System.out.println(sum);
        }
    }*/

    @Override
    public Point getNextPosition(Point paletteSize, Point original, Point changed, BufferedImage image, double percent) {
        Point start = getStartPosition(paletteSize, original, image);
        Point finish = getFinishPosition(paletteSize, original, image);

        Point distance = new Point(finish.getX()-start.getX(), finish.getY()-start.getY());

        //Point rPoint = new Point(changed.getX() + Math.abs(get(distance.getXInt()/10000D*percent, distance.getXInt()))
        //        , changed.getY() + Math.abs(get(distance.getYInt()/10000D*percent, distance.getYInt())));

        //System.out.println(distance.getXInt()/10000D*percent + ", " + distance.getX() + ", " + rPoint.getX() + ", " + get(distance.getXInt()/10000D*percent, distance.getXInt()));

        //System.out.println(rPoint.getX());

        //return rPoint;
        return new Point( start.getX() + distance.getX()/10000D*percent, start.getY() + distance.getY()/10000D*percent);
    }

    /*
    public static void main(String[] args) {
        System.out.println(get(200, 300));
    }

    private static double get(double percent, int distance) {
        // y = -x^2/1000

        double r = -(1D/1000D)*(percent*(percent-distance));

        return Math.abs(r);
    }*/

    @Override
    public int getDefaultWaitTime(IntervalSpeed speed) {
        return 0;
    }

    @Override
    public int getDefaultInterval(IntervalSpeed speed) {
        return 200-40*speed.getSpeed();
    }

    public abstract Point getStartPosition(Point paletteSize, Point original, BufferedImage image);

    public abstract Point getFinishPosition(Point paletteSize, Point original, BufferedImage image);

}
