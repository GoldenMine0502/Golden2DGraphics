package com.GoldenMine.test;

import com.GoldenMine.graphics.ButtonSprite;
import com.GoldenMine.graphics.ObjectSprite;
import com.GoldenMine.graphics.Palette;
import com.GoldenMine.utility.DefaultEffects;
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

        Palette pal = new Palette(new Point(1500, 900), 60); // 그림을 그릴 팔레트 생성

        JFrame frame = new JFrame("test");
        frame.setSize(1500, 900);
        frame.setVisible(true);
        frame.add(pal);

        pal.setVisible(true);

        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB); // example 사진 하나 생성
        Graphics g = image.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 300, 300);

        BufferedImage image2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB); // example 사진 하나 생성
        Graphics g2 = image2.getGraphics();
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, 300, 300);


        ButtonSprite sprite = new ButtonSprite(); // 스프라이트 생성
        sprite.addImage(ObjectSprite.getImage("resources/입력칸.png"), image2);
        sprite.setPosition(new Point(100, 100));

        ObjectSprite sprite2 = new ObjectSprite();
        sprite2.addImage(new Font("나눔고딕 Light", 0, 180),
                "Hello,World!", Color.WHITE);
        sprite2.setPositionInCenter(new Point(900, 500));

        ButtonSprite sprite3 = new ButtonSprite();
        sprite3.addImage(image, image2);
        sprite3.setPosition(200, 200);

        pal.addSprite(sprite, true); // 스프라이트 추가하기, transperency = true로 할시 맨처음 시작할때 투명해짐
        pal.addSprite(sprite2, false);
        pal.addSprite(sprite3, false);
        pal.startRender(); // 렌더링 시작

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 10; i++)
        {
            //sprite.addEffect(DefaultEffects.LEFT_FLY_COME, IntervalSpeed.NORMAL, true);

            //sprite.addWrapper(DefaultWrappers.LEFT_AWAY_AND_FADEOUT);
            sprite3.addEffect(DefaultEffects.LEFT_FLY_COME, IntervalSpeed.VERY_SLOW, true);
            sprite.addEffect(DefaultEffects.FADE_IN, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.SCALE_BIGGER, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.ROTATE_RIGHT, IntervalSpeed.FAST, true);
            sprite2.addEffect(DefaultEffects.LEFT_FLY_COME, IntervalSpeed.FAST, true);
            //AffineTransform transform = new AffineTransform();

            try {
                Thread.sleep(8000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //sprite.addWrapper(DefaultWrappers.RIGHT_COME_AND_FADEIN);

            //sprite.addEffect(DefaultEffects.RIGHT_FLY_AWAY, IntervalSpeed.NORMAL, true);
            sprite.addEffect(DefaultEffects.FADE_OUT, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.SCALE_SMALLER, IntervalSpeed.FAST, true);
            sprite.addEffect(DefaultEffects.ROTATE_RIGHT, IntervalSpeed.FAST, true);
            sprite2.addEffect(DefaultEffects.RIGHT_FLY_AWAY, IntervalSpeed.FAST, true);
            sprite3.addEffect(DefaultEffects.RIGHT_FLY_AWAY, IntervalSpeed.VERY_SLOW, true);
            //AffineTransform transform = new AffineTransform();

            try {
                Thread.sleep(8000L);
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
