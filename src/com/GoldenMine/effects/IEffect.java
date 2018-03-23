package com.GoldenMine.effects;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IEffect {

    public abstract int getFPS();

    public abstract int getIntervalMS();

    public abstract void editImage(BufferedImage original, BufferedImage changed, Graphics changedGraphics, int percent);

}
