package com.GoldenMine.effects;

import com.GoldenMine.utility.Point;

import com.GoldenMine.utility.UtilityFade;
import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EffectFadeIn implements IEffect {
    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        IEffect r = new EffectFadeIn();
        Point p1 = new Point(500, 500);
        Point p2 = new Point(0, 0);

        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            //image.getGraphics().drawImage(r.editImage(p1, p2, image, image, image.getGraphics(), 5000), 0, 0, null);
        }
        System.out.println(System.currentTimeMillis()-start);
        //editImage(new Point(500, 500), new Point(0, 0), new )
    }

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
        /*Graphics oriG = changed.getGraphics();

        if(oriG instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D)oriG;

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)percent/10000F));
        }*/
        if(changeGraphics instanceof Graphics2D) {
            //System.out.println("a");
            ((Graphics2D)changeGraphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)percent/10000F));
        }

        /*
        int x = changed.getWidth();
        int y = changed.getHeight();



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
        }*/
        /*
        int lastAlpha = 0;
        if(changeGraphics instanceof  Graphics2D) {
            Graphics2D graphics = changed.createGraphics();
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-(float)percent/100f);
            //System.out.println(1-(float)percent/100f);
            graphics.setComposite(ac);

        }*/

        //System.out.println(lastAlpha);
        return new Pair<Point, Point>(new Point(0, 0), new Point(original.getWidth(), original.getHeight()));
        //return changed;
    }


}
