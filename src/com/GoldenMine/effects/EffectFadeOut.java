package com.GoldenMine.effects;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EffectFadeOut implements IEffect {
    @Override
    public Point editImage(Point paletteSize, Point imagePosition, Point imagePositionChanged, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, AffineTransform changedTransform, double percent, Object... parameters) {
        double scale = -1;

        switch(parameters.length) {
            case 0:
                scale = 1 - percent/10000D;
                break;
            case 1:
                scale = 1 - percent/10000D*(int)parameters[0];
                break;
            case 2:
                scale = 1 - IEffect.getPercent((int)parameters[0], (int)parameters[1], percent)/10000D;
                break;
        }
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)scale));

        return imagePositionChanged;
    }

    @Override
    public String getName() {
        return "Fade-O";
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
