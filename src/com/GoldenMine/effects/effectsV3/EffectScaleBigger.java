package com.GoldenMine.effects.effectsV3;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EffectScaleBigger implements IEffect {
    @Override
    public String getName() {
        return "Scale-B";
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
        double scale = percent / 10000D;
        /*transform.translate(
                (paletteSize.getXInt()/2) - (original.getWidth()*(scale))/2,
                (paletteSize.getYInt()/2) - (original.getHeight()*(scale))/2
        );
        transform.setToTranslation(500, 500);*/
        //transform.scale(scale, scale);
        transform.setToScale(scale, scale);
        //transform.scale(scale, scale);
        //System.out.println(scale);
    }
}
