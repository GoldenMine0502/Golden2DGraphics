package com.GoldenMine.구버전.effectsV1;

import com.GoldenMine.utility.Point;
import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EffectScaleBigger implements IEffect {
    @Override
    public int getFPS() {
        return 0;
    }

    @Override
    public int getIntervalMS() {
        return 0;
    }

    @Override
        public Pair<Point, Point> editImage(Point paletteSize, Point spritePos, BufferedImage original, BufferedImage changed, Graphics changedGraphics, double percent) {
            if(changedGraphics instanceof Graphics2D) {
                /* error */

                Graphics2D g2d = (Graphics2D) changedGraphics;

                double scale = percent/10000D;

                AffineTransform trans = g2d.getTransform();
                //trans.get
                trans.setToScale(scale, scale);

                //AffineTransform trans = new AffineTransform();
                //trans.translate(spritePos.getXInt(), spritePos.getYInt());
                //trans.concatenate( g2d.getTransform() );
                //trans.setToScale(scale, scale);
                //g2d.getTransform().setToScale(scale, scale);
                g2d.setTransform(trans);
                //System.out.println(scale);
            }

            return null;
    }
}
