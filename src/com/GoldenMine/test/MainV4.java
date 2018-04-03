package com.GoldenMine.test;

import com.GoldenMine.graphics.ObjectSprite;
import com.GoldenMine.graphics.Palette;
import com.GoldenMine.utility.DefaultEffects;
import com.GoldenMine.utility.DefaultWrappers;
import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainV4 {
    public static void main(String[] args) {

        /*
        구현해야 할 기능: 닦아내기, 조금 위로 올라오는거
         */

        Palette pal = new Palette(new Point(500, 500), 60); // 그림을 그릴 팔레트 생성

        JFrame frame = new JFrame("test");
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(pal);

        pal.setVisible(true);

        /*BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB); // example 사진 하나 생성
        Graphics g = image.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 300, 300);*/

        ObjectSprite sprite = new ObjectSprite("resources/입력칸.png"); // 스프라이트 생성
        sprite.setPosition(new Point(100, 100));

        ObjectSprite sprite2 = new ObjectSprite(new Font("돋움", 0, 50),
                "안녕!", Color.WHITE);
        sprite2.setPosition(new Point(100, 300));

        pal.addSprite(sprite, true); // 스프라이트 추가하기, transperency = true로 할시 맨처음 시작할때 투명해짐
        pal.addSprite(sprite2, false);
        pal.startRender(); // 렌더링 시작

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 5; i++)
        {
            //sprite.addEffect(DefaultEffects.LEFT_FLY_COME, IntervalSpeed.NORMAL, true);

            //sprite.addWrapper(DefaultWrappers.LEFT_AWAY_AND_FADEOUT);

            sprite.addEffect(DefaultEffects.FADE_IN, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.SCALE_BIGGER, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.ROTATE_RIGHT, IntervalSpeed.FAST, true);
            sprite2.addEffect(DefaultEffects.LEFT_FLY_COME, IntervalSpeed.FAST, true);
            //AffineTransform transform = new AffineTransform();

            try {
                Thread.sleep(3000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //sprite.addWrapper(DefaultWrappers.RIGHT_COME_AND_FADEIN);

            //sprite.addEffect(DefaultEffects.RIGHT_FLY_AWAY, IntervalSpeed.NORMAL, true);
            sprite.addEffect(DefaultEffects.FADE_OUT, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.SCALE_SMALLER, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.ROTATE_RIGHT, IntervalSpeed.FAST, true);
            sprite2.addEffect(DefaultEffects.RIGHT_FLY_AWAY, IntervalSpeed.FAST, true);

            //AffineTransform transform = new AffineTransform();

            try {
                Thread.sleep(3000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            Thread.sleep(100000L);
        } catch(Exception ex) {

        }

        System.exit(0);
    }
}
