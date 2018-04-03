package com.GoldenMine.구버전;

import com.GoldenMine.구버전.graphicsV3.ObjectSprite;
import com.GoldenMine.구버전.graphicsV3.Palette;
import com.GoldenMine.utility.DefaultEffects;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainV3 {
    public static void main(String[] args) {
        BufferedImage image1 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        BufferedImage image2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image1.getGraphics();
        Graphics g2 = image2.getGraphics();

        g.setColor(Color.GREEN);
        g.fillRect(0,0,300,300);

        g2.setColor(Color.BLUE);
        g.fillRect(0,0,100,100);

        /* 여기서부터 라이브러리 사용 */

        Palette palette = new Palette("test", new Point(500, 500), 2);

        ObjectSprite sprite1 = new ObjectSprite(image1);
        sprite1.setPoint(100, 100);

        palette.addSprite(sprite1, false);

        palette.startRender();

        //for(int i = 0; i < 5; i++)
        {
            sprite1.enableEffect(DefaultEffects.SCALE_BIGGER, 0, 75);
            //sprite1.enableEffect(DefaultEffects.FADE_IN, 0, 75);
            //sprite1.enableAction(DefaultActions.LEFT_FLY_COME, 0, 75);
            sprite1.enableRelativeAction(DefaultEffects.SCALE_BIGGER, 0, 75);
            sleep(5000);

            //sprite1.enableEffect(DefaultEffects.FADE_OUT, 0, 15);
            //sprite1.enableAction(DefaultActions.RIGHT_FLY_AWAY, 0, 75);

            sleep(5000);
        }
        sleep(80000);
        System.exit(0);
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
