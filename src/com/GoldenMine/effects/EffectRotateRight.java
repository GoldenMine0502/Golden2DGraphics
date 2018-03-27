package com.GoldenMine.effects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class EffectRotateRight implements IEffect{
    @Override
    public int getFPS() {
        return 0;
    }

    @Override
    public int getIntervalMS() {
        return 0;
    }

    @Override
    public BufferedImage editImage(BufferedImage original, BufferedImage changed, Graphics changedGraphics, double percent) {
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
