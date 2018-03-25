package com.GoldenMine;

import com.GoldenMine.actions.ActionLeftFlyAndCome;
import com.GoldenMine.actions.ActionRightFlyAndAway;
import com.GoldenMine.actions.IAction;
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

        IAction leftIn = new ActionLeftFlyAndCome();
        IAction rightOut = new ActionRightFlyAndAway();

        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

        /* 중앙에 초록색 사각형을 만듦 */
        Graphics g = image.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 300, 300);

        BufferedImage image2 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        /* 중앙에 파란색 사각형을 만듦 */
        Graphics g2 = image2.getGraphics();
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, 100, 100);

        /* 스프라이트 만들기 */
        ObjectSprite sprite = new ObjectSprite(image);
        sprite.registerEffect(fadeIn, 100, 200);
        sprite.registerEffect(fadeOut, 0, 200);
        sprite.setPoint(100, 100);
        sprite.registerAction(leftIn, 0, 300);
        sprite.registerAction(rightOut, 0, 300);

        ObjectSprite sprite2 = new ObjectSprite(image2);
        sprite2.registerEffect(fadeIn, 0, 600);
        sprite2.registerEffect(fadeOut, 0, 300);
        sprite2.setPoint(200, 200);

        palette.addSprite(sprite, true);
        palette.addSprite(sprite2, true);

        palette.startRender();
        //palette.setVisible(true);
        //palette.stopRender();

        sprite.enableEffect(fadeIn);
        sprite.enableAction(leftIn);
        sprite2.enableEffect(fadeIn);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sprite.enableEffect(fadeOut);
        sprite.enableAction(rightOut);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sprite.enableEffect(fadeIn);
        sprite.enableAction(leftIn);
        try {
            Thread.sleep(5000L);
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
