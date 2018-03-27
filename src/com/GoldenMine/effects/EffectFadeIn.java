package com.GoldenMine.effects;

import com.GoldenMine.utility.UtilityFade;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EffectFadeIn implements IEffect {
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
        int lastAlpha = 0;
        if(changeGraphics instanceof  Graphics2D) {
            Graphics2D graphics = changed.createGraphics();
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-(float)percent/100f);
            //System.out.println(1-(float)percent/100f);
            graphics.setComposite(ac);

        }*/

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                //int rgbi = original.getRGB(i, j);
                //Graphics2D graphics = changed.createGraphics();
                //Color rgb = UtilityFade.getAlphaPlus(new Color(original.getRGB(i, j)), percent);

                //int rgbb = rgb.getRGB();
                //lastAlpha = rgb.getRGB();
                changed.setRGB(i, j, UtilityFade.getAlphaPlus(new Color(original.getRGB(i, j), true), percent).getRGB());
                //System.out.println(rgbi + ", " + rgbb);

            }
        }

        //System.out.println(lastAlpha);
    }


}
