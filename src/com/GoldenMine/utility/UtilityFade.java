package com.GoldenMine.utility;

import java.awt.*;

public class UtilityFade {
    public static void main(String[] args) {
        Color color = new Color(100, 100, 100, 150);
        //Color color2 = new Color(100, 100, 100, 100);

        /*
        for(int i = 0; i < 100; i++) {
            //System.out.println(color.getAlpha());
            System.out.println(getAlphaMinus(color, i+1).getAlpha());

        }*/

        /*
        for(int i = 0; i < 257; i++) {
            int rgb = (i&0xFF) << 24;

            System.out.println((i&0xFF) + ": " + rgb);
        }*/

        long start = System.nanoTime();

        //start = System.nanoTime();

        int data = (100 << 24) + (100 << 16) + (100 << 8) + 100;
        //System.out.println(data);
        //for(int i = 0; i < 100000000;i++)
        {
            //Color color = new Color(1);
            System.out.println(data);
            //System.out.println(alphaPlus(data, 1000)
            //);
        }

        //System.out.println(System.nanoTime() - start);


        /*start = System.nanoTime();

        //Color c = ;

        for(int i = 0; i < 100000000;i++) {
            getAlphaPlus(new Color(100, 100, 100, 100), 5000);
        }

        System.out.println(System.nanoTime() - start);*/
    }

    /*
    AAAAAAAA / RRRRRRRR / GGGGGGGG / BBBBBBBB
     */

    /*public static int alphaMinus(int rgb, double percent) {
        int alpha = (rgb >> 24) & 0xFF; // = 0x000000ff

        int test = (int) (alpha/10000D*percent);
        System.out.println((int) (alpha/10000D*percent) + ", " + ((int)(alpha/10000D*percent)<<24) + ", " + (rgb & 0xFFFFFF));
        return (test << 24) + (rgb & 0xFFFFFF);
    }

    public static int alphaPlus(int rgb, double percent) {
        int alpha = (rgb >> 24) & 0xFF; // = 0x000000ff
        System.out.println((int) (alpha - alpha/10000D*percent));

        return ((int) (alpha - alpha/10000D*percent)) << 24 + (rgb & 0xFFFFFF);
    }*/

    public static Color getAlphaPlus(Color first, double percent) {
        double alpha = first.getAlpha();
        int cal = (int) (alpha/10000D*percent);
        //System.out.println();
        return new Color(first.getRed(), first.getGreen(), first.getBlue(),cal>=256 ? 255 : cal);
    }

    public static Color getAlphaMinus(Color first, double percent) {
        double alpha = first.getAlpha();
        int cal = (int)(alpha - alpha/10000D*percent);
        return new Color(first.getRed(), first.getGreen(), first.getBlue(),cal>=256 ? 255 : cal);
    }

    private static int calculateAlpha(int alpha, double percent) {
        return (int) Math.round(alpha - alpha/10000D*percent);
    }

    public static Point getRotatePoint(final int centerX, final int centerY, double a, double b, double pitch) {
        // x1 = b * cos(pitch) - a * sin(pitch)
        // y1 = b * sin(pitch) + a * cos(pitch)
        //double z = this.z;

        a-=centerX;
        b-=centerY;

        double d = degree(pitch);

        double x1 = b * Math.cos(d) - a * Math.sin(d);

        double y1 = b * Math.sin(d) + a * Math.cos(d);

        //double x2 = y1 * Math.cos(yaw) - x1 * Math.sin(yaw);

        //double y2 = y1 * Math.cos(yaw) - x1 * Math.sin(yaw);



        //Point point = Rotate(new Point(x, y), pitch);

        //System.out.println(x1 +", " + y1);
        //System.out.println(x + ", " + y + ", " + Math.cos(d) + ", " + Math.sin(d) + ", " + b * Math.cos(d) + ", " + a * Math.sin(d));


        return new Point(x1 + centerX, y1 + centerY);
    }

    public static double degree(double ra) {
        return ra * Math.PI / 180;
    }

    public static int getResizedScalePoint(int location, int imageSize, double scale) {
        //System.out.println("location: " + location + ", imageSize: " + imageSize + ", Scale" + scale + ", calculated: " + (1/scale));
        return (int) Math.round(location * 1D / scale + imageSize * 1D / scale / 2D - imageSize/2D);
    }
}
