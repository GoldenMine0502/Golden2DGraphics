package com.GoldenMine;

import com.GoldenMine.actions.ActionLeftFlyAndCome;
import com.GoldenMine.actions.ActionRightFlyAndAway;
import com.GoldenMine.actions.ActionTopFlyAndCome;
import com.GoldenMine.actions.IAction;
import com.GoldenMine.effects.EffectFadeIn;
import com.GoldenMine.effects.EffectFadeOut;
import com.GoldenMine.effects.IEffect;
import com.GoldenMine.graphics.ObjectSprite;
import com.GoldenMine.graphics.Palette;
import com.GoldenMine.utility.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        /*
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setVisible(true);
        f.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println('a');
            }
        });
        f.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println('b');
            }
        });

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);*/

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
        ObjectSprite sprite = new ObjectSprite(palette, image);
        sprite.registerEffect(fadeIn, 40, 35);
        sprite.registerEffect(fadeOut, 0, 35);
        sprite.setPoint(100, 100);
        sprite.registerAction(leftIn, 0, 75);
        sprite.registerAction(rightOut, 0, 75);

        ObjectSprite sprite2 = new ObjectSprite(palette, image2);
        sprite2.registerEffect(fadeIn, 0, 1200);
        sprite2.registerEffect(fadeOut, 0, 1200);
        sprite2.setPoint(200, 200);

        palette.addSprite(sprite, true);
        palette.addSprite(sprite2, true);

        palette.startRender();
        //palette.stopRender();

            sprite.enableEffect(fadeIn);
            sprite.enableAction(leftIn);
            sprite2.enableEffect(fadeIn);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        for(int i = 0; i < 5; i++) {
            sprite.enableAction(rightOut);
            sprite.enableEffect(fadeOut);
            try {
                Thread.sleep(2500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sprite.enableEffect(fadeIn);
            sprite.enableAction(leftIn);
            try {
                Thread.sleep(2500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
