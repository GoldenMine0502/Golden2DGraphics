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

    public static Color getAlphaPlus(Color first, int percent) {
        double alpha = first.getAlpha();
        //System.out.println((int)(alpha-(int)(alpha - alpha/100D*percent)));
        return new Color(first.getRed(), first.getGreen(), first.getBlue(),(int)(alpha-(int)(alpha - alpha/100D*percent)));
    }

    public static Color getAlphaMinus(Color first, int percent) {
        double alpha = first.getAlpha();
        return new Color(first.getRed(), first.getGreen(), first.getBlue(),(int)(alpha - alpha/100D*percent));
    }
}
