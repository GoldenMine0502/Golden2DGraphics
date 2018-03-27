package com.GoldenMine.effects;

import com.GoldenMine.utility.UtilityFade;

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
    public void editImage(BufferedImage original, BufferedImage changed, Graphics changeGraphics, double percent) {
        int x = changed.getWidth();
        int y = changed.getHeight();

        /*
        if(changeGraphics instanceof  Graphics2D) {
            Graphics2D g2d = (Graphics2D) changeGraphics;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) percent / 100f);
            g2d.setComposite(ac);
        }*/

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                //changed.setRGB(i, j, UtilityFade.alphaPlus(original.getRGB(i, j), percent));

                changed.setRGB(i, j, UtilityFade.getAlphaMinus(new Color(original.getRGB(i, j), true), percent).getRGB());
                /*
                Color rgb = UtilityFade.getAlphaMinus(new Color(original.getRGB(i, j)), percent);
                changed.setRGB(i, j, rgb.getRGB());*/
            }
        }
    }


}
