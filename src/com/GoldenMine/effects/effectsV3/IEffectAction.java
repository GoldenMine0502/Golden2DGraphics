package com.GoldenMine.effects.effectsV3;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public interface IEffectAction {

    String getName();

    int getDefaultWaitTime(IntervalSpeed speed);
    int getDefaultInterval(IntervalSpeed speed);

    Point editImage(Point paletteSize, Point originalPosition, Point changedPosition, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, double percent, Object... parameters);
}
