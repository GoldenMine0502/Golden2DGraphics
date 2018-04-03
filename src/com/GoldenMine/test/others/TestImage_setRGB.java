package com.GoldenMine.test.others;

import java.awt.image.BufferedImage;

public class TestImage_setRGB {
    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        BufferedImage transparency = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < 500; i++) {
            for(int j = 0; j < 500; j++) {
                transparency.setRGB(i,j,0);
            }
        }

        long start;
        start = System.currentTimeMillis();

        for(int count = 0; count < 10000; count++) {
            for (int i = 0; i < 500; i++) {
                for (int j = 0; j < 500; j++) {
                    image.setRGB(i, j, 0);
                }
            }
        }
        System.out.println(System.currentTimeMillis()-start); // 60fps 기준 500*500이미지 처리를 2개밖에 못하는 퍼포먼스 수준.(i7 6700 기준)

        start = System.currentTimeMillis();

        for(int i = 0; i < 10000; i++)
            image.getGraphics().drawImage(image, 0,0, null); // 약 64개정도 처리 가능함
        System.out.println(System.currentTimeMillis()-start);
        //GPU 연산시 퍼포먼스가 더 늘어날 것으로 예상하지만
        //그냥 CPU로 하자...
    }
}
