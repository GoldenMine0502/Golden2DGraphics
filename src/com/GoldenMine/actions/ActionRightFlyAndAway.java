package com.GoldenMine.actions;

import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;

public class ActionRightFlyAndAway extends ActionFly {
    @Override
    public Point getStartPosition(Point paletteSize, Point original, BufferedImage image) {
        return new Point(original.getXInt(), original.getY());
    }

    @Override
    public Point getFinishPosition(Point paletteSize, Point original, BufferedImage image) {
        return new Point(paletteSize.getXInt(), original.getY());
    }
}
