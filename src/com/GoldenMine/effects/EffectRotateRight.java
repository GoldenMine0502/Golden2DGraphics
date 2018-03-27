package com.GoldenMine.effects;

import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class EffectRotateRight implements IEffect{
    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        EffectRotateRight r = new EffectRotateRight();
        Point p1 = new Point(500, 500);
        Point p2 = new Point(0, 0);

        long start = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            image.getGraphics().drawImage(r.editImage(p1, p2, image, image, image.getGraphics(), 5000), 0, 0, null);
        }
        System.out.println(System.currentTimeMillis()-start);
        //editImage(new Point(500, 500), new Point(0, 0), new )
    }

    @Override
    public int getFPS() {
        return 0;
    }

    @Override
    public int getIntervalMS() {
        return 0;
    }

    @Override
    public BufferedImage editImage(Point paletteSize, Point spritePos, BufferedImage original, BufferedImage changed, Graphics changedGraphics, double percent) {
        if(changedGraphics instanceof Graphics2D) {
            Graphics2D graphics2D = (Graphics2D)changedGraphics;

            AffineTransform tx = new AffineTransform();
            //System.out.println(percent/10000D*360D);
            tx.rotate(Math.toRadians(percent/10000D*360D), spritePos.getXInt() + original.getWidth() / 2, spritePos.getYInt() + original.getHeight() / 2);
            //AffineTransformOp op = new AffineTransformOp(tx,
            //        AffineTransformOp.TYPE_BILINEAR);
            graphics2D.setTransform(tx);

        }


        //System.out.println("a");

        //사진이 평소보다 sqrt(2)만큼 더 커야함, 대충 1.5배로 계산하자
        //1.5배에서


        /*
        AffineTransform tx = new AffineTransform();
        tx.rotate((percent/10000D*360D) * Math.PI / 180.0, original.getWidth() / 2, original.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        return op.filter(original, changed);*/




        return changed;
        //changed.getGraphics().drawImage(op.filter(original, changed), 0, 0, null);
    }
}

/*
class Employee {
    String name;
    String phoneNumber;
    int money;

    public static void main(String[] args) {
        System.out.println(new Employee("김태원", "01073792165", 100000));
    }

    public Employee(String name, String phoneNumber, int money) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.money = money;
    }

    @Override
    public String toString() {
        return "이름: " + name + ", 전화번호: " + phoneNumber + ", 연봉: " + money;
    }
}*/
