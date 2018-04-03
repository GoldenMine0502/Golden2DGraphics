package com.GoldenMine.구버전.graphicsV2;

import com.GoldenMine.thread.threadAPI.APISingleThread;
import com.GoldenMine.thread.threadAPI.APIThreadHandler;
import com.GoldenMine.thread.threadAPI.unit.TimeUnit;
import com.GoldenMine.utility.GoldenColor;
import com.GoldenMine.utility.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Palette extends JFrame {
    private static Color initalizeColor = new Color(0,0,0,0);
    private static int initalizeColorRGB = initalizeColor.getRGB();

    //전체 buffer에서 반복문을 굴리고
    //오브젝트 스프라이트에서 Point에 대한 RGB값을 받아내고 계산해 RGB 값 얻어냄
    //RGB 값 저장의 경우 bufferedImage에서 처리하도록 하며
    //Point값의 경우.... 직접 저장하여야 함

    /*
        저장해야할 데이터: 해당하는 x,y에 대한 Point와 RGB

        BufferedImage를 사용하되 setRGB이외 사용하지 않음
    */

    APISingleThread renderThread;

    BufferedImage buffer;

    Graphics mainGraphics;

    RGBPoint[][] pixels;
    Point size;

    class RGBPoint extends Point {
        private GoldenColor color;

        public RGBPoint(double x, double y) {
            super(x, y);
        }

        public RGBPoint(double x, double y, Color color) {
            super(x, y);
            this.color = new GoldenColor(color.getRGB());
        }

        public RGBPoint(double x, double y, GoldenColor color) {
            super(x, y);
            this.color = color;

        }

        public void setColor(GoldenColor color) {
            this.color = color;
        }

        public GoldenColor getColor() {
            return color;
        }
    }


    public Palette(int size) {
        mainGraphics = getGraphics();
        renderThread = new APISingleThread(TimeUnit.FPS, size, new APIThreadHandler() {
            @Override
            public void onThreadExecute() throws InterruptedException {

            }

            @Override
            public void onKeepUp() {

            }

            @Override
            public void onInterrupt() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onStop() {

            }
        });
    }

    public void startRender() {

    }

    public void pauseRender() {

    }

    public void stopRender() {

    }

    public void ResumeRender() {

    }

    public void updateRender() {

    }

    public void renderInBuffer() {

    }

    public void renderInScreen() {

        int xSize = size.getXInt();
        int ySize = size.getYInt();

        for(int x = 0 ; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                RGBPoint rgbPoint = pixels[x][y];

                buffer.setRGB(rgbPoint.getXInt(), rgbPoint.getYInt(), rgbPoint.getColor().getRGB());
            }
        }
    }

    public void clearBuffer() {

        int xSize = size.getXInt();
        int ySize = size.getYInt();

        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                buffer.setRGB(x, y, initalizeColorRGB);
            }
        }
    }

    public void clearRGBPoint() {

    }
}
