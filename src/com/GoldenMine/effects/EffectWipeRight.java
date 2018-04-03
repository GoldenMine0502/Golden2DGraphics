package com.GoldenMine.effects;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EffectWipeRight implements IEffect {
    /*
    가로크기 500 = a

    현재 픽셀에 대한 알파값 A = original.getRGB().getAlpha()/n*100
    이전 픽셀에 대한 알파값 A-N = original.getRGB().getAlpha()/n*100*(N+1)%256
    이전 픽셀에만 영향을 주며
    n값은 영향을 줄 이전 픽셀

     */

    @Override
    public Point editImage(Point paletteSize, Point imagePosition, Point imagePositionChanged, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform newTransform, AffineTransform changedTransform, double percent, Object... parameters) {
        return imagePositionChanged;
    }

    @Override
    public String getName() {
        return "Wipe-R";
    }

    @Override
    public int getDefaultWaitTime(IntervalSpeed speed) {
        return 0;
    }

    @Override
    public int getDefaultInterval(IntervalSpeed speed) {
        return 200 - 40*speed.getSpeed();
    }
}
