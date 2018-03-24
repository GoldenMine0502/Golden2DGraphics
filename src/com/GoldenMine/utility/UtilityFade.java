package com.GoldenMine.utility;

import java.awt.*;

public class UtilityFade {
    public static void main(String[] args) {
        Color color = new Color(100, 100, 100, 150);
        //Color color2 = new Color(100, 100, 100, 100);

        for(int i = 0; i < 100; i++) {
            //System.out.println(color.getAlpha());
            System.out.println(getAlphaMinus(color, i+1).getAlpha());

        }
    }

    /*
    AAAAAAAA / RRRRRRRR / GGGGGGGG / BBBBBBBB
     */

    public static int getAlphaPlus(int alpha, int percent) {
        int cal = (int) Math.round((alpha-(alpha - alpha/100D*percent)));

        return cal >= 256 ? 255 : cal;
    }

    public static Color getAlphaPlus(Color first, int percent) {
        double alpha = first.getAlpha();
        int cal = (int) Math.round((alpha-(alpha - alpha/10000D*percent)));
        //System.out.println();
        return new Color(first.getRed(), first.getGreen(), first.getBlue(),cal>=256 ? 255 : cal);
    }

    public static Color getAlphaMinus(Color first, int percent) {
        double alpha = first.getAlpha();
        int cal = (int)(alpha - alpha/10000D*percent);
        return new Color(first.getRed(), first.getGreen(), first.getBlue(),cal>=256 ? 255 : cal);
    }

    private static int calculateAlpha(int alpha, int percent) {
        return (int) Math.round(alpha - alpha/10000D*percent);
    }
}
