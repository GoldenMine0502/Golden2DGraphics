package com.GoldenMine.actions;

import com.GoldenMine.graphics.ObjectSprite;
import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;

public interface IAction {
    Point getNextPosition(Point paletteSize, Point original, BufferedImage image, double percent);

}
