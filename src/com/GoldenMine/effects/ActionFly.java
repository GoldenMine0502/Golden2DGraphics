package com.GoldenMine.effects;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class ActionFly implements IEffect, PositionChangable {
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
    public Point editImage(Point paletteSize, Point imagePosition, Point imagePositionChanged, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, AffineTransform changedTransform, double percent, Object... parameters) {

        Point start = getStartPosition(paletteSize, imagePosition, changed);
        Point finish = getFinishPosition(paletteSize, imagePosition, changed);

        Point distance = new Point(finish.getX() - start.getX(), finish.getY() - start.getY());

        return new Point(start.getX() + distance.getX() / 10000D * percent, start.getY() + distance.getY() / 10000D * percent);
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
        return 200 - 40 * speed.getSpeed();
    }

    public abstract Point getStartPosition(Point paletteSize, Point original, BufferedImage image);

    public abstract Point getFinishPosition(Point paletteSize, Point original, BufferedImage image);

}
