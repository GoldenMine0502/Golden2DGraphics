package com.GoldenMine.effects.effectsV4;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;
import com.GoldenMine.utility.UtilityFade;

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
    public Point editImage(Point paletteSize, Point imagePosition, Point imagePositionChanged, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, double percent, Object... parameters) {
        double scale = percent / 10000D;

        //AffineTransform form = new AffineTransform();
        transform.scale(scale, scale);

        //transform.concatenate(form);


        //System.out.println(imagePosition.getXInt());

        //System.out.println(scale);

        return new Point(UtilityFade.getResizedScalePoint(imagePositionChanged.getXInt(), changed.getWidth(), scale)
                , UtilityFade.getResizedScalePoint(imagePositionChanged.getYInt(), changed.getHeight(), scale));
    }
}
