package com.GoldenMine.test;

import com.GoldenMine.utility.UtilityFade;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class setScale extends JFrame {
    public setScale() {
        setTitle("test");

        setSize(500, 500);
        setVisible(true);

        Graphics2D g = (Graphics2D) getGraphics();

        //for(int i = 0; i < 200; i++)
        {
            g.setTransform(new AffineTransform());
            //double scale = i / 100D;
            double scale = 0.025;

            final int FS = 300;
            final int FSY = 300;

            BufferedImage image = new BufferedImage(FS, FSY, BufferedImage.TYPE_INT_ARGB);
            Graphics iG = image.getGraphics();
            iG.setColor(new Color(0, 255, 0, 64));
            iG.fillRect(0, 0, FS, FSY);

            final int FX = 100;
            final int FY = 100;

            g.clearRect(0, 0, 500, 500);
            g.drawImage(image, FX, FY, null);

            iG.setColor(new Color(255, 0, 0, 255));
            iG.fillRect(0, 0, FS, FSY);

            AffineTransform trans = new AffineTransform();
            trans.setToScale(scale, scale);
            g.setTransform(trans);
            g.drawImage(image, UtilityFade.getResizedScalePoint(FX, FS, scale), getResizedPoint(FY, FSY, scale), null);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getResizedPoint(int location, int imageSize, double scale) {
        int value = (int) (location * 1 / scale + imageSize * 1 / scale / 2 - imageSize/2);
        System.out.println(value);
        return value;
    }

    public static void main(String[] args) {
        new setScale();

        // 원래이미지위치 * 1/현재이미지배율 -> 원래 image 배율이 1일때 위치 (=A)
        // 원래이미지크기 * 1/현재이미지배율 -> 원래 이미지의 1/크기(=B)
        // A + B/2 - 원래이미지크기/2 -> 진짜 값이다.
        try {
            Thread.sleep(120000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
