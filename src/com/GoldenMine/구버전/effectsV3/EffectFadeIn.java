package com.GoldenMine.구버전.effectsV3;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EffectFadeIn implements IEffect {
    @Override
    public String getName() {
        return "Fade-I";
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
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)percent/10000F));
    }
}
