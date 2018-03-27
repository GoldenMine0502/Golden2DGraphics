package com.GoldenMine.graphics;

import com.GoldenMine.actions.IAction;
import com.GoldenMine.effects.IEffect;
import com.GoldenMine.events.IEvent;
import com.GoldenMine.thread.threadAPI.APISingleThread;
import com.GoldenMine.thread.threadAPI.APIThreadHandler;
import com.GoldenMine.thread.threadAPI.unit.TimeUnit;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.Point;
import javafx.util.Pair;

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
    HashMap<ObjectSprite, List<BufferedImage>> spriteImages = new HashMap<>();
    HashMap<ObjectSprite, Point> spritePoints = new HashMap<>();
    //List<IEvent> events = new ArrayList<IEvent>();

    APISingleThread singleThread;

    boolean started = false;
    boolean update = false;

    public Palette(String title, Point size, int fps) {
        setTitle(title);
        setSize(size.getXInt(), size.getYInt());
        setVisible(true);

        /* add listeners */



        Graphics mainGraphic = getGraphics();
        buffer = new BufferedImage(size.getXInt(), size.getYInt(), BufferedImage.TYPE_INT_ARGB);
        Graphics bufferGraphic = buffer.getGraphics();


        singleThread = new APISingleThread(TimeUnit.FPS, fps, new APIThreadHandler() {
            @Override
            public void onThreadExecute() throws InterruptedException {
                /* rendering */
                for (ObjectSprite sprite : sprites) {
                    List<Pair<IEffect, Interval>> effects = sprite.enabledEffects;
                    List<Pair<IAction, Interval>> actions = sprite.enabledActions;

                    /* 이펙트 적용 */
                    for (int i = 0; i < effects.size(); i++) {
                        Pair<IEffect, Interval> effectPair = effects.get(i);

                        IEffect effect = effectPair.getKey();
                        Interval interval = effectPair.getValue();

                        //기다림이 끝났으면
                        if (interval.getCompletedWait()) {
                            //틱 계산, 시간이 다 되었으면
                            if (interval.addTick()) {
                                //제거
                                effects.remove(i);
                                i--;
                            } else {
                                // 안끝났으면 이펙트 효과 적용
                                BufferedImage img = spriteImages.get(sprite).get(sprite.getCurrentImagePosition());
                                effect.editImage(sprite.getCurrentImage(), img, img.getGraphics(), interval.getIntervalPercent());
                            }
                        } else {
                            //안끝났으면 끝나도록 유도함
                            interval.addWait();
                        }
                    }

                    /* 액션 적용 */
                    if (actions.size() > 0) {
                        Pair<IAction, Interval> actionPair = actions.get(0);
                        IAction action = actionPair.getKey();
                        Interval interval = actionPair.getValue();

                        if (interval.getCompletedWait()) {
                            //틱 계산, 시간이 다 되었으면
                            if (interval.addTick()) {
                                //제거
                                actions.remove(0);
                            } else {
                                // 안끝났으면 액션 효과 적용
                                spritePoints.put(sprite, action.getNextPosition(size, sprite.getPosition(), spritePoints.get(sprite), sprite.getCurrentImage(), interval.getIntervalPercent()));
                            }
                        } else {
                            //안끝났으면 끝나도록 유도함
                            interval.addWait();
                        }
                    }

                    //스프라이트의 활성화된 이벤트를 얻고
                    //이벤트에서 지난 tick을 계산한다.
                    /*
                    HashMap<IEffect, Interval> effects = sprite.enabledEffects;
                    List<IEffect> removingEffects = new ArrayList<>();

                    // 이미지 이벤트에 따라 수정
                    for(IEffect effect : effects.keySet()) {
                        Interval interval = effects.get(effect);
                        if(interval.getCompletedWait()) {
                            if(interval.addTick()) {
                                removingEffects.add(effect);
                            } else {
                                BufferedImage img = spriteImages.get(sprite);
                                effect.editImage(sprite.getCurrentImage(), img, img.getGraphics(), interval.getIntervalPercent());
                     //System.out.println(new Color(spriteImages.get(sprite).getRGB(0,0)).getAlpha());
                            }
                        } else {
                            interval.addWait();
                        }
                    }

                    HashMap<IAction, Interval> actions = sprite.enabledActions;
                    List<IAction> removingActions = new ArrayList<>();

                    // 이미지 액션에 따라 위치 수정
                    for(IAction action : actions.keySet()) {
                        Interval interval = actions.get(action);
                        if(interval.getCompletedWait()) {
                            if(interval.addTick()) {
                                removingActions.add(action);
                            } else {
                                spritePoints.put(sprite, action.getNextPosition(size, sprite.point, sprite.getCurrentImage(), interval.getIntervalPercent()));
                            }
                        } else {
                            interval.addWait();
                        }
                    }

                    // 완료된 이펙트 제거
                    for(IEffect effect : removingEffects) {
                        effects.remove(effect);
                    }

                    // 완료된 액션 제거
                    for(IAction action : removingActions) {
                        actions.remove(action);
                    }*/
                }

                /* dispose */
                bufferGraphic.clearRect(0, 0, size.getXInt(), size.getYInt());
                for (ObjectSprite sprite : sprites) {
                    Point spritePoint = spritePoints.get(sprite);

                    bufferGraphic.drawImage(spriteImages.get(sprite).get(sprite.getCurrentImagePosition()), spritePoint.getXInt(), spritePoint.getYInt(), null);
                }

                /* copy */
                //if(mainGraphic!=null)
                    mainGraphic.drawImage(buffer, 0, 0, null);

                if (update) {
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
        if (started) {
            singleThread.start();
        } else {
            singleThread.resume();
        }
    }

    public void updateSprite(ObjectSprite sprite) {

    }

    public void addSprite(ObjectSprite objectSprite) {
        addSprite(objectSprite, false);
    }

    public void addSprite(ObjectSprite objSprite, boolean transparent) {
        sprites.add(objSprite);

        /* copy all of original images */
        List<BufferedImage> originalImgs = objSprite.getImages();
        List<BufferedImage> imgs = new ArrayList<>();

        for(int i = 0; i < originalImgs.size(); i++) {
            BufferedImage originalImg = originalImgs.get(i);

            BufferedImage img = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            if(transparent) {
                img.getGraphics().clearRect(0, 0, objSprite.getCurrentImage().getWidth(), objSprite.getCurrentImage().getHeight());
            } else {
                img.getGraphics().drawImage(objSprite.getCurrentImage(), 0, 0, null);
            }

            imgs.add(img);
        }
        spriteImages.put(objSprite, imgs);
        spritePoints.put(objSprite, new Point(objSprite.getPosition().getX(), objSprite.getPosition().getY()));
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

    public void invokeEvent(IEvent event) {
        for(ObjectSprite sprite : sprites) {
            sprite.enableEventEffects(event);
        }
    }

    public void stopProgram() {
        stopRender();
        System.exit(0);
    }
}
