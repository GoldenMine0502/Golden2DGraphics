package com.GoldenMine.test;

import com.GoldenMine.graphics.ObjectSprite;
import com.GoldenMine.graphics.Palette;
import com.GoldenMine.utility.DefaultEffects;
import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainV4 {
    public static void main(String[] args) {
        Palette pal = new Palette("test", new Point(500, 500), 120); // 그림을 그릴 팔레트 생성

        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB); // example 사진 하나 생성
        Graphics g = image.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 300, 300);

        ObjectSprite sprite = new ObjectSprite(image); // 스프라이트 생성
        sprite.setPosition(new Point(100, 100));

        pal.addSprite(sprite, true); // 스프라이트 추가하기, transperency = true로 할시 맨처음 시작할때 투명해짐

        pal.startRender(); // 렌더링 시작

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 5; i++) {

            sprite.addEffect(DefaultEffects.FADE_IN, IntervalSpeed.NORMAL, true);
            sprite.addEffect(DefaultEffects.SCALE_BIGGER, IntervalSpeed.NORMAL, true);
            sprite.addEffect(DefaultEffects.ROTATE_RIGHT, IntervalSpeed.NORMAL, true);

            //AffineTransform transform = new AffineTransform();

            try {
                Thread.sleep(4000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            sprite.addEffect(DefaultEffects.FADE_OUT, IntervalSpeed.NORMAL, true);
            sprite.addEffect(DefaultEffects.SCALE_SMALLER, IntervalSpeed.NORMAL, true);
            sprite.addEffect(DefaultEffects.ROTATE_LEFT, IntervalSpeed.NORMAL, true);

            //AffineTransform transform = new AffineTransform();

            try {
                Thread.sleep(4000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.exit(0);
    }
}
