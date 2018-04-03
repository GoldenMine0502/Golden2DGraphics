package com.GoldenMine.effects;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EffectRotateLeft implements IEffect {
    @Override
    public Point editImage(Point paletteSize, Point imagePosition, Point imagePositionChanged, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, AffineTransform changedTransform, double percent, Object... parameters) {

        transform.rotate(Math.toRadians(-percent/10000D*360D), imagePositionChanged.getXInt() + original.getWidth() / 2, imagePositionChanged.getYInt() + original.getHeight() / 2);

        return imagePositionChanged;
    }

    @Override
    public String getName() {
        return "Rotate-L";
    }

    @Override
    public int getDefaultWaitTime(IntervalSpeed speed) {
        return 0;
    }

    @Override
    public int getDefaultInterval(IntervalSpeed speed) {
        return 200-40*speed.getSpeed();
    }
}
