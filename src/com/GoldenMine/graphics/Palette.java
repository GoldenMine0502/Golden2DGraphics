package com.GoldenMine.graphics;

import com.GoldenMine.effects.IEffect;
import com.GoldenMine.thread.threadAPI.APISingleThread;
import com.GoldenMine.thread.threadAPI.APIThreadHandler;
import com.GoldenMine.thread.threadAPI.unit.TimeUnit;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Palette extends JFrame {
        //Effect Info
        //enabled effect
        //event effect
        //
    //BufferedImage paletteImage;

    BufferedImage buffer;
    //int nextBufferPoint = 0;

    List<ObjectSprite> sprites = new ArrayList<>();
    HashMap<ObjectSprite, BufferedImage> spriteImages = new HashMap<>();

    APISingleThread singleThread;

    boolean started = false;
    boolean update = false;

    public Palette(String title, Point size, int fps) {
        setTitle(title);
        setSize(size.getXInt(), size.getYInt());
        setVisible(true);

        Graphics mainGraphic = getGraphics();
        buffer = new BufferedImage(size.getXInt(), size.getYInt(), BufferedImage.TYPE_INT_ARGB);
        Graphics bufferGraphic = buffer.getGraphics();


        singleThread = new APISingleThread(TimeUnit.FPS, fps, new APIThreadHandler() {
            @Override
            public void onThreadExecute() throws InterruptedException {
                /* rendering */
                for(ObjectSprite sprite : sprites) {
                    //스프라이트의 활성화된 이벤트를 얻고
                    //이벤트에서 지난 tick을 계산한다.

                    HashMap<IEffect, Interval> effects = sprite.enabledEffects;
                    List<IEffect> removing = new ArrayList<>();

                    /* 이미지 이벤트에 따라 수정 */
                    for(IEffect effect : effects.keySet()) {
                        Interval interval = effects.get(effect);
                        if(interval.getCompletedWait()) {
                            if(interval.addTick()) {
                                removing.add(effect);
                            } else {
                                BufferedImage img = spriteImages.get(sprite);
                                effect.editImage(sprite.original, img, img.getGraphics(), interval.getIntervalPercent());
                     //System.out.println(new Color(spriteImages.get(sprite).getRGB(0,0)).getAlpha());
                            }
                        } else {
                            interval.addWait();
                        }
                    }

                    /* 완료된 이펙트 제거 */
                    for(IEffect effect : removing) {
                        effects.remove(effect);
                    }
                }

                /* dispose */
                bufferGraphic.clearRect(0,0,size.getXInt(), size.getYInt());
                for(ObjectSprite sprite : sprites) {
                    bufferGraphic.drawImage(spriteImages.get(sprite), sprite.point.getXInt(), sprite.point.getYInt(), null);
                }

                /* copy */
                mainGraphic.drawImage(buffer, 0, 0, null);

                if(update) {
                    singleThread.pause();
                    update = false;
                }
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
        //graphics = panel.getGraphics();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(buffer, 0, 0, null);
    }

    public void updateRender() {
        update = true;
        if(started) {
            singleThread.start();
        } else {
            singleThread.resume();
        }
    }

    public void addSprite(ObjectSprite objSprite) {
        sprites.add(objSprite);

        BufferedImage img = new BufferedImage(objSprite.original.getWidth(), objSprite.original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(objSprite.original, 0, 0, null);

        spriteImages.put(objSprite, img);
    }

    public void startRender() {
        started = true;
        singleThread.start();
    }

    public void pauseRender() {
        singleThread.pause();
    }

    public void resumeRender() {
        singleThread.resume();
    }

    public void stopRender() {
        singleThread.stop();
    }
}
