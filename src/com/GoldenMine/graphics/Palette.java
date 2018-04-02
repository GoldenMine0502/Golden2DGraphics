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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Palette extends JFrame {
    //Effect Info
    //enabled effect
    //event effect
    //
    //BufferedImage paletteImage;

    /*
    getCurrentRGB()
    List<ObjectSprite>에서 이미지의 모든 픽셀들의 합을 구한다.
    ObjectSprite.getPosition().add(currentpixel) 최종 좌표


     */

    class SpriteData {
        private List<SpriteElementData> elements = new ArrayList<>();
        private Point position;

        public SpriteData(ObjectSprite sprite, boolean transparency) {
            List<BufferedImage> originalImgs = sprite.getImages();

            for(int i = 0; i < originalImgs.size(); i++) {
                elements.add(new SpriteElementData(originalImgs.get(i), transparency));
            }

            Point toCpy = sprite.getPosition();

            position = new Point(toCpy.getX(), toCpy.getY());
        /* copy all of original images */
        }

        public List<SpriteElementData> getSpriteElements() {
            return elements;
        }

        public Point getPosition() {
            return position;
        }

        public void setPosition(Point position) {
            this.position = position;
        }
    }

    class SpriteElementData {
        private BufferedImage copied;
        private Graphics2D graphics2D;

        public SpriteElementData(BufferedImage originalImg, boolean transparency) {
            Graphics2D g2d = buffer.createGraphics();

            copied = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            copied.getGraphics().drawImage(originalImg, 0, 0, null);

            if(transparency) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
            }

            this.graphics2D = g2d;
        }

        public BufferedImage getImage() {
            return copied;
        }

        public Graphics2D getGraphics() {
            return graphics2D;
        }
    }


    BufferedImage buffer;
    //int nextBufferPoint = 0;

    List<ObjectSprite> sprites = new ArrayList<>();
    //HashMap<ObjectSprite, List<Pair<BufferedImage, Graphics2D>>> spriteImages = new HashMap<>();
    //HashMap<ObjectSprite, Point> spritePoints = new HashMap<>();

    HashMap<ObjectSprite, SpriteData> spriteConfigs = new HashMap<>();

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



        singleThread = new APISingleThread(TimeUnit.FPS, fps, new APIThreadHandler() {
            @Override
            public void onThreadExecute() throws InterruptedException {
                /* rendering */
                Graphics bufferGraphic = buffer.getGraphics();
                bufferGraphic.clearRect(0, 0, size.getXInt(), size.getYInt());
                //bufferGraphic.fillRect(0,0,size.getXInt(), size.getYInt());
                for (ObjectSprite sprite : sprites) {
                    SpriteData spriteData = spriteConfigs.get(sprite);
                    SpriteElementData elementData = spriteData.getSpriteElements().get(sprite.getCurrentImagePosition());

                    List<Pair<IEffect, Interval>> effects = sprite.enabledEffects;
                    List<Pair<IAction, Interval>> actions = sprite.enabledActions;
                    //List<Pair<BufferedImage, Graphics2D>> imageData = elementData.get(sprite);

                    //Pair<BufferedImage, Graphics2D> imagePair = imageData.get(sprite.getCurrentImagePosition());
                    BufferedImage img = elementData.getImage();

                    Graphics2D g2dimg = elementData.getGraphics();

                    //Graphics bufferGraphicInternal = imagePair.getValue();

                    /* 이펙트 적용 */
                    for (int i = 0; i < effects.size(); i++) {
                        Pair<IEffect, Interval> effectPair = effects.get(i);

                        IEffect effect = effectPair.getKey();
                        Interval interval = effectPair.getValue();

                        //기다림이 끝났으면
                        if (interval.getCompletedWait()) {
                            //틱 계산, 시간이 다 되었으면
                            if (interval.addTick()) {
                                //Graphics g = img.getGraphics();

                                //g.drawImage(effect.editImage(size, sprite.getPosition(), sprite.getCurrentImage(), img, g, interval.getIntervalPercent()), 0, 0, null);

                                //제거
                                effects.remove(i);
                                i--;
                            } else {
                                // 안끝났으면 이펙트 효과 적용

                                //System.out.println(effects.size());
                                //.set(sprite.getCurrentImagePosition(), new Pair<>(effect.editImage(size, sprite.getPosition(), sprite.getCurrentImage(), img, imagePair.getValue(), interval.getIntervalPercent()), imagePair.getValue()));
                                effect.editImage(size, sprite.getPosition(), sprite.getCurrentImage(), img, g2dimg, interval.getIntervalPercent());
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
                                spriteData.setPosition(action.getNextPosition(size, sprite.getPosition(), spriteData.getPosition(), sprite.getCurrentImage(), interval.getIntervalPercent()));
                                //spritePoints.put(sprite, action.getNextPosition(size, sprite.getPosition(), spritePoints.get(sprite), sprite.getCurrentImage(), interval.getIntervalPercent()));
                            }
                        } else {
                            //안끝났으면 끝나도록 유도함
                            interval.addWait();
                        }
                    }

                    Point spritePoint = spriteData.getPosition();

                    //AffineTransform tr = g2dimg.getTransform();
                    //tr.setToScale(0.5, 0.5);
                    //g2dimg.setTransform(tr);
                    g2dimg.drawImage(elementData.getImage(), spritePoint.getXInt(), spritePoint.getYInt(), null);
                    //imagePair.getValue().drawImage(spriteImages.get(sprite).get(sprite.getCurrentImagePosition()).getKey(), spritePoint.getXInt(), spritePoint.getYInt(), null);
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

    public void writeImage(ObjectSprite sprite) {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //g.drawImage(buffer, 0, 0, null);
    }

    public void updateRender() {
        update = true;
        if (started) {
            singleThread.start();
        } else {
            singleThread.resume();
        }
    }

    public void addSprite(ObjectSprite objectSprite) {
        addSprite(objectSprite, true);
    }

    public void addSprite(ObjectSprite objSprite, boolean transparent) {
        sprites.add(objSprite);

        /* copy all of original images */
        spriteConfigs.put(objSprite, new SpriteData(objSprite, transparent));
        /*List<BufferedImage> originalImgs = objSprite.getImages();
        List<Pair<BufferedImage, Graphics2D>> imgs = new LinkedList<>();

        for(int i = 0; i < originalImgs.size(); i++) {
            BufferedImage originalImg = originalImgs.get(i);

            Graphics2D g2d = buffer.createGraphics();

            BufferedImage img = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            img.getGraphics().drawImage(objSprite.getCurrentImage(), 0, 0, null);
            if(transparent) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
                //img.getGraphics().clearRect(0, 0, objSprite.getCurrentImage().getWidth(), objSprite.getCurrentImage().getHeight());
            }

            imgs.add(new Pair<>(img, g2d));
        }*/
        //spriteImages.put(objSprite, imgs);
        //spritePoints.put(objSprite, new Point(objSprite.getPosition().getX(), objSprite.getPosition().getY()));
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
