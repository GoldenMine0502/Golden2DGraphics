package com.GoldenMine.effects.effectsV4;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public interface IEffect {
    /* EffectV3 + Action */
    Point editImage(Point paletteSize, Point imagePosition, Point imagePositionChanged, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, double percent, Object... parameters);

    String getName();

    int getDefaultWaitTime(IntervalSpeed speed);
    int getDefaultInterval(IntervalSpeed speed);
}
