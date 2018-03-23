package com.GoldenMine;

import com.GoldenMine.effects.EffectFadeIn;
import com.GoldenMine.effects.EffectFadeOut;
import com.GoldenMine.effects.IEffect;
import com.GoldenMine.graphics.ObjectSprite;
import com.GoldenMine.graphics.Palette;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        Palette palette = new Palette("Test", new Point(500, 500), 60);
        /*이펙트 만들기*/
        IEffect fadeIn = new EffectFadeIn();
        IEffect fadeOut = new EffectFadeOut();


        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

        /* 중앙에 초록색 사각형을 만듬 */
        Graphics g = image.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 300, 300);

        BufferedImage image2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        /* 중앙에 초록색 사각형을 만듬 */
        Graphics g2 = image2.getGraphics();
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, 100, 100);

        /* 스프라이트 만들기 */
        ObjectSprite sprite =new ObjectSprite(image);
        sprite.registerEffect(fadeIn, 0, 300);
        sprite.registerEffect(fadeOut, 0, 300);
        sprite.setPoint(100, 100);

        ObjectSprite sprite2 =new ObjectSprite(image2);
        sprite2.registerEffect(fadeIn, 0, 1200);
        sprite2.registerEffect(fadeOut, 0, 300);
        sprite2.setPoint(200, 200);

        palette.addSprite(sprite);
        palette.addSprite(sprite2);

        palette.startRender();
        palette.setVisible(true);
        //palette.stopRender();

        sprite.enableEffect(fadeIn);

        sprite2.enableEffect(fadeIn);
        try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sprite.enableEffect(fadeOut);
        try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sprite.enableEffect(fadeIn);
        try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
