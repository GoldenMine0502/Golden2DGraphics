package com.GoldenMine.effects.effectsV4;

import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;

public class ActionBottomFlyAndAway extends ActionFly {
    @Override
    public Point getStartPosition(Point paletteSize, Point original, BufferedImage image) {
        return new Point(original.getX(), original.getY());
    }

    @Override
    public Point getFinishPosition(Point paletteSize, Point original, BufferedImage image) {
        return new Point(original.getX(), paletteSize.getYInt());
    }

    @Override
    public String getName() {
        return "ActionFly-BA";
    }
}
