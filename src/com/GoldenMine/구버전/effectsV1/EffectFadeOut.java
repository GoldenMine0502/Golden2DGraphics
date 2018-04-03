package com.GoldenMine.구버전.effectsV1;

import com.GoldenMine.utility.Point;

import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EffectFadeOut implements IEffect {
    @Override
    public int getFPS() {
        return 0;
    }

    @Override
    public int getIntervalMS() {
        return 0;
    }

    @Override
    public Pair<Point, Point> editImage(Point paletteSize, Point spritePos, BufferedImage original, BufferedImage changed, Graphics changeGraphics, double percent) {
        if(changeGraphics instanceof Graphics2D) {
            //System.out.println("a");
            ((Graphics2D)changeGraphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-(float)percent/10000F));
        }
        /*
        int x = changed.getWidth();
        int y = changed.getHeight();


        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                //changed.setRGB(i, j, UtilityFade.alphaPlus(original.getRGB(i, j), percent));

                changed.setRGB(i, j, UtilityFade.getAlphaMinus(new Color(original.getRGB(i, j), true), percent).getRGB());

                //Color rgb = UtilityFade.getAlphaMinus(new Color(original.getRGB(i, j)), percent);
                //changed.setRGB(i, j, rgb.getRGB());
            }
        }*/


        /*
        if(changeGraphics instanceof  Graphics2D) {
            Graphics2D g2d = (Graphics2D) changeGraphics;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) percent / 100f);
            g2d.setComposite(ac);
        }*/
        return new Pair<Point, Point>(new Point(0, 0), new Point(original.getWidth(), original.getHeight()));
        //return changed;
    }


}
