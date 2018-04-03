package com.GoldenMine.구버전.effectsV3;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EffectRotateRight implements IEffect {
    @Override
    public String getName() {
        return "Rotate-R";
    }

    @Override
    public int getDefaultWaitTime(IntervalSpeed speed) {
        return 0;
    }

    @Override
    public int getDefaultInterval(IntervalSpeed speed) {
        return 200-40*speed.getSpeed();
    }

    @Override
    public void editImage(Point paletteSize, Point imagePosition, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, double percent, Object... parameters) {
        transform.setToRotation(Math.toRadians(percent/10000D*360D), imagePosition.getXInt() + original.getWidth() / 2, imagePosition.getYInt() + original.getHeight() / 2);
    }
}
