package com.GoldenMine.effects;

import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IEffect {

    public abstract int getFPS();

    public abstract int getIntervalMS();

    public abstract BufferedImage editImage(Point paletteSize, Point spritePos, BufferedImage original, BufferedImage changed, Graphics changedGraphics, double percent);

}
