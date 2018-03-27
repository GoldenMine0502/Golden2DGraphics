package com.GoldenMine.actions;

import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;

public class ActionTopFlyAndAway extends ActionFly {
    @Override
    public Point getStartPosition(Point paletteSize, Point original, BufferedImage image) {
        return new Point(original.getX(), original.getY());
    }

    @Override
    public Point getFinishPosition(Point paletteSize, Point original, BufferedImage image) {
        return new Point(original.getX(), -image.getHeight());
    }
}
