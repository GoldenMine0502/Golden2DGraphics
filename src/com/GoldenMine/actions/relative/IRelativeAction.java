package com.GoldenMine.actions.relative;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;

public interface IRelativeAction {
    Point getNextPosition(Point paletteSize, Point original, Point changed, BufferedImage image, double percent, Object... parameters);

    String getName();

    int getDefaultWaitTime(IntervalSpeed speed);
    int getDefaultInterval(IntervalSpeed speed);
}
