package com.GoldenMine.effects;

import com.GoldenMine.utility.Point;
import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IEffect {

    public abstract int getFPS();

    public abstract int getIntervalMS();

    public abstract Pair<Point, Point> editImage(Point paletteSize, Point spritePos, BufferedImage original, BufferedImage changed, Graphics changedGraphics, double percent);

    static void setRGBWithTransparency(BufferedImage image, int x, int y, int RGB) {
        /*
        int oRGB = image.getRGB(x, y);
        int oA = (oRGB >> 24) & 0xFF;
        int oR = (oRGB >> 16) & 0xFF;
        int oG = (oRGB >> 8)  & 0xFF;
        int oB = (oRGB)       & 0xFF;

        int RGB = rgb;
        int A = (RGB >> 24) & 0xFF;
        int R = (RGB >> 16) & 0xFF;
        int G = (RGB >> 8)  & 0xFF;
        int B = (RGB)       & 0xFF;

        int nRGB = 0;
        nRGB |= (oA | A) << 24;
        nRGB |= (oR | R) << 16;
        nRGB |= (oG | G) << 8 ;
        nRGB |= (oB | B)      ;
        */
        int oRGB = image.getRGB(x, y);
        int nRGB =  (((oRGB >> 24 & 0xFF) | (RGB >> 24 & 0xFF)) << 24) |// oRGB
                    (((oRGB >> 16 & 0xFF) | (RGB >> 16 & 0xFF)) << 16) |
                    (((oRGB >> 8 & 0xFF) | (RGB >> 8 & 0xFF)) << 8) |
                    ((oRGB & 0xFF) | (RGB & 0xFF))
        ;

        image.setRGB(x, y, nRGB);
    }
}
