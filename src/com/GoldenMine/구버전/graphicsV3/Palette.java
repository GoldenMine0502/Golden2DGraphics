package com.GoldenMine.구버전.graphicsV3;

import com.GoldenMine.구버전.actions.*;
import com.GoldenMine.구버전.actions.relative.ActionScaleBigger;
import com.GoldenMine.구버전.actions.relative.IRelativeAction;
import com.GoldenMine.구버전.effectsV3.*;
import com.GoldenMine.thread.threadAPI.APISingleThread;
import com.GoldenMine.thread.threadAPI.APIThreadHandler;
import com.GoldenMine.thread.threadAPI.unit.TimeUnit;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Palette extends JFrame {
    private static HashMap<String, IEffect> effects = new HashMap<>();
    private static HashMap<String, IAction> actions = new HashMap<>();
    private static HashMap<String, IRelativeAction> relativeActions = new HashMap<>();

    private Point paletteSize;

    private List<ObjectSprite> sprites = new ArrayList<>();
    private HashMap<ObjectSprite, SpriteData> spriteConfigs = new HashMap<>();

    private APISingleThread thread;

    private BufferedImage buffer;
    private Graphics bufferGraphics;

    private Graphics mainGraphics;

    static {
        addEffect(new EffectFadeIn());
        addEffect(new EffectFadeOut());
        addEffect(new EffectScaleBigger());
        addEffect(new EffectRotateLeft());
        addEffect(new EffectRotateRight());

        addAction(new ActionLeftFlyAndCome());
        addAction(new ActionLeftFlyAndAway());
        addAction(new ActionTopFlyAndCome());
        addAction(new ActionTopFlyAndAway());
        addAction(new ActionRightFlyAndCome());
        addAction(new ActionRightFlyAndAway());
        addAction(new ActionBottomFlyAndCome());
        addAction(new ActionBottomFlyAndAway());

        addRelativeAction(new ActionScaleBigger());
    }


    private boolean update;


    class SpriteData {
        private List<SpriteElementData> elements = new ArrayList<>();
        private Point position;
        //private List<EffectWrapper> wrappers = new ArrayList<>();

        SpriteData(ObjectSprite sprite, boolean transparency) {
            List<BufferedImage> originalImgs = sprite.getImages();

            for (BufferedImage originalImg : originalImgs) {
                elements.add(new SpriteElementData(originalImg, transparency));
            }

            position = new Point(sprite.getPosition().getX(), sprite.getPosition().getY());
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
        private AffineTransform transform;

        SpriteElementData(BufferedImage originalImg, boolean transparency) {
            Graphics2D g2d = buffer.createGraphics();

            copied = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            copied.getGraphics().drawImage(originalImg, 0, 0, null);

            if (transparency) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
            }

            this.graphics2D = g2d;
            transform = g2d.getTransform();
        }

        public BufferedImage getImage() {
            return copied;
        }

        public Graphics2D getGraphics() {
            return graphics2D;
        }

        public AffineTransform getTransform() {
            return transform;
        }
    }

    public Palette(String title, Point size, int fps) {
        setTitle(title);
        setSize(size.getXInt(), size.getYInt());

        setVisible(true);

        paletteSize = new Point();

        paletteSize.setX(size.getX());
        paletteSize.setY(size.getY());

        buffer = new BufferedImage(size.getXInt(), size.getYInt(), BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = buffer.getGraphics();

        mainGraphics = getGraphics();

        thread = new APISingleThread(TimeUnit.FPS, fps, new APIThreadHandler() {
            @Override
            public void onThreadExecute() throws InterruptedException {
                renderInBuffer();

                mainGraphics.drawImage(buffer, 0, 0, null);
                if (update) {
                    thread.pause();
                    update = false;
                }
            }
        });
    }

    public void addSprite(ObjectSprite sprite) {
        addSprite(sprite, false);
    }

    public void addSprite(ObjectSprite sprite, boolean transparency) {
        sprites.add(sprite);

        spriteConfigs.put(sprite, new SpriteData(sprite, transparency));
    }

    public void startRender() {
        thread.start();
    }

    public void pauseRender() {
        thread.pause();
    }

    public void stopRender() {
        thread.stop();
    }

    public void ResumeRender() {
        thread.resume();
    }

    public void updateRender() {
        update = true;
        thread.resume();
    }

    void renderInBuffer() {
        bufferGraphics.clearRect(0, 0, paletteSize.getXInt(), paletteSize.getYInt());

        for (ObjectSprite sprite : sprites) {
            SpriteData spriteData = spriteConfigs.get(sprite);
            SpriteElementData elementData = spriteData.getSpriteElements().get(sprite.getCurrentImagePoint());

            List<Pair<IEffect, Interval>> effects = sprite.enabledEffects;
            List<Pair<IAction, Interval>> actions = sprite.enabledActions;
            List<Pair<IRelativeAction, Interval>> relativeActions = sprite.enabledRelativeActions;

            BufferedImage img = elementData.getImage();
            Graphics2D g2dImg = elementData.getGraphics();
            AffineTransform transform = elementData.getTransform();
            //System.out.println(img + ", " + g2dImg + ", " + transform);

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
                        spriteData.setPosition(action.getNextPosition(paletteSize, sprite.getPosition(), spriteData.getPosition(), sprite.getCurrentImage(), interval.getIntervalPercent()));
                        //spritePoints.put(sprite, action.getNextPosition(size, sprite.getPosition(), spritePoints.get(sprite), sprite.getCurrentImage(), interval.getIntervalPercent()));
                    }
                } else {
                    //안끝났으면 끝나도록 유도함
                    interval.addWait();
                }
            }

            Point currentPosition = spriteData.getPosition();

            if(relativeActions.size()>0) {
                Pair<IRelativeAction, Interval> actionpair = relativeActions.get(0);
                IRelativeAction action = actionpair.getKey();
                Interval interval = actionpair.getValue();

                if(interval.addWait()) {
                    if(interval.addTick()) {
                        relativeActions.remove(0);
                    } else {
                        currentPosition = action.getNextPosition(paletteSize, sprite.getPosition(), currentPosition, sprite.getCurrentImage(), interval.getIntervalPercent());

                        //sprite.getPosition().setX(currentPosition.getX());
                        //sprite.getPosition().setY(currentPosition.getY());

                        System.out.println("set: " + currentPosition.getXInt() + ", " + currentPosition.getYInt());
                    }
                }
            }

            /* 이펙트 적용 */
            for (int i = 0; i < effects.size(); i++) {
                Pair<IEffect, Interval> effectPair = effects.get(i);

                if(handleEffect(sprite, spriteData, elementData, effectPair.getKey(), effectPair.getValue())) {
                    effects.remove(i);
                    i--;
                }
            }

            Point spritePoint;
            if(currentPosition==null) {
                spritePoint = spriteData.getPosition();
            } else {
                spritePoint = currentPosition;
            }
            //buffer에서 각각 주어진 Graphic 속성을 이용하여 이미지를 그림

            //System.out.println(g2dImg + ", " + spritePoint);

            System.out.println(spritePoint.getX() + ", " + spritePoint.getY());

            g2dImg.setTransform(transform);
            g2dImg.drawImage(elementData.getImage(), spritePoint.getXInt(), spritePoint.getYInt(), null);
        }
    }

    public void drawInMainGraphics() {

    }

    public boolean handleEffect(ObjectSprite sprite, SpriteData spriteData, SpriteElementData elementData, IEffect effect, Interval interval) {
        BufferedImage img = elementData.getImage();
        Graphics2D g2dImg = elementData.getGraphics();
        AffineTransform transform = elementData.getTransform();


        //기다림이 끝났으면
            if (interval.getCompletedWait()) {
                //틱 계산, 시간이 다 되었으면
                if (interval.addTick()) {
                    //제거
                    return true;
                    //effects.remove(i);
                    //i--;
                } else {
                    // 안끝났으면 이펙트 효과 적용
                    effect.editImage(paletteSize, sprite.getPosition(), sprite.getCurrentImage(), img, g2dImg, transform, interval.getIntervalPercent());
                }
            } else {
                //안끝났으면 끝나도록 유도함
                interval.addWait();
            }

            return false;
    }

    public static IAction getAction(String action) {
        return actions.get(action);
    }

    public static IRelativeAction getRelativeAction(String action) {
        return relativeActions.get(action);
    }

    public static void addAction(IAction action) {
        actions.put(action.getName(), action);
    }

    public static void addEffect(IEffect effect) {
        effects.put(effect.getName(), effect);
    }

    public static void addRelativeAction(IRelativeAction action) {
        relativeActions.put(action.getName(), action);
    }

    public static IEffect getEffect(String effect) {
        return effects.get(effect);
    }

    public static Interval getInterval(IEffect effect, IntervalSpeed speed) {
        return new Interval(effect.getDefaultWaitTime(speed), effect.getDefaultInterval(speed));
    }

    public static Interval getInterval(IAction effect, IntervalSpeed speed) {
        return new Interval(effect.getDefaultWaitTime(speed), effect.getDefaultInterval(speed));
    }

    public static Interval getInterval(IRelativeAction effect, IntervalSpeed speed) {
        return new Interval(effect.getDefaultWaitTime(speed), effect.getDefaultInterval(speed));
    }

    public static Interval getInterval(int wait, int interval) {
        return new Interval(wait, interval);
    }
}
