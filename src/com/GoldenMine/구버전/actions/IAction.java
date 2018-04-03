package com.GoldenMine.구버전.actions;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;

public interface IAction {
    Point getNextPosition(Point paletteSize, Point original, Point changed, BufferedImage image, double percent);

    String getName();

    int getDefaultWaitTime(IntervalSpeed speed);
    int getDefaultInterval(IntervalSpeed speed);
}
