package com.GoldenMine.graphics;

import com.GoldenMine.effects.*;
import com.GoldenMine.thread.threadAPI.APISingleThread;
import com.GoldenMine.thread.threadAPI.APIThreadHandler;
import com.GoldenMine.thread.threadAPI.unit.TimeUnit;
import com.GoldenMine.utility.EffectData;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;
import com.GoldenMine.wrappers.EffectWrapper;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Palette extends JPanel {
    /*
    이펙트와 액션 통합

    새로운 이펙트 만들기
    +
    이펙트에서 Point를 리턴하는데 null이라면 이펙트라고 판단함

     */

    private static HashMap<String, IEffect> effects = new HashMap<>();

    static {
        addEffect(new EffectFadeIn());
        addEffect(new EffectFadeOut());
        addEffect(new EffectScaleBigger());
        addEffect(new EffectScaleSmaller());
        addEffect(new EffectRotateLeft());
        addEffect(new EffectRotateRight());


        addEffect(new ActionLeftFlyAndCome());
        addEffect(new ActionLeftFlyAndAway());
        addEffect(new ActionTopFlyAndCome());
        addEffect(new ActionTopFlyAndAway());
        addEffect(new ActionRightFlyAndCome());
        addEffect(new ActionRightFlyAndAway());
        addEffect(new ActionBottomFlyAndCome());
        addEffect(new ActionBottomFlyAndAway());
    }


    public static void addEffect(IEffect effect) {
        effects.put(effect.getName(), effect);
    }

    public static IEffect getEffect(String effect) {
        return effects.get(effect);
    }

    public static Interval getInterval(IEffect effect, IntervalSpeed speed) {
        return new Interval(effect.getDefaultWaitTime(speed), effect.getDefaultInterval(speed));
    }

    public static Interval getInterval(IEffect effect, IntervalSpeed speed, boolean natural) {
        return new Interval(effect.getDefaultWaitTime(speed), effect.getDefaultInterval(speed), natural);
    }

    public static Interval getInterval(int wait, int interval) {
        return new Interval(wait, interval);
    }

    public static Interval getInterval(int wait, int interval, boolean natural) {
        return new Interval(wait, interval, natural);
    }


























    private List<ObjectSprite> sprites = new ArrayList<>();
    private HashMap<ObjectSprite, SpriteData> spriteConfigs = new HashMap<>();

    private APISingleThread thread;

    private BufferedImage buffer;
    private Graphics bufferGraphics;

    private Graphics mainGraphics;

    private boolean update;

    private List<Integer> enabledNumbers = new ArrayList<>();

    private Point paletteSize;

    public void addSprite(ObjectSprite sprite) {
        addSprite(sprite, false);
    }

    public void addSprite(ObjectSprite sprite, boolean transperency) {
        sprites.add(sprite);
        spriteConfigs.put(sprite, new SpriteData(sprite, transperency));
    }

    class SpriteData {
        private List<SpriteElementData> elements = new ArrayList<>();
        private Point position = new Point();
        //private List<EffectWrapper> wrappers = new ArrayList<>();
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
            //System.out.println("set " + position);
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

            //g2d.setRenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

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

        public void setTransform(AffineTransform transform) {
            this.transform = transform;
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        mainGraphics = getGraphics();
    }

    public Palette(Point size, int fps) {
        //setTitle(title);
        setSize(size.getXInt(), size.getYInt());

        setVisible(true);

        paletteSize = new Point(size.getX(), size.getY());


        buffer = new BufferedImage(size.getXInt(), size.getYInt(), BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = buffer.getGraphics();

        mainGraphics = getGraphics();


        thread = new APISingleThread(TimeUnit.FPS, fps, new APIThreadHandler() {
            @Override
            public void onThreadExecute() throws InterruptedException {
                /*
                여기에 넘버 기반 이펙트 실행 메소드를 넣는다.
                 */
                bufferGraphics.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());

                for(ObjectSprite objectSprite : sprites) {
                    AffineTransform transform = new AffineTransform();
                    Point position = new Point(objectSprite.getPosition().getX(), objectSprite.getPosition().getY());
                    SpriteData data = spriteConfigs.get(objectSprite);



                    //Point currentPoint = data.getPosition();

                    handleList(objectSprite, objectSprite.effects, position, transform);

                    for(EffectWrapper wrapper : objectSprite.wrappers) {
                        handleList(objectSprite, wrapper.getEffects(), position, transform);
                    }
                    //System.out.println("current " + currentPoint);
                    //if(data!=null && data.getPosition()!=null)
                    //    System.out.println(data.getPosition().getXInt());

                    if(objectSprite.effects.size()>0) {
                        //SpriteData spritedata = spriteConfigs.get(objectSprite);
                        SpriteElementData elementData = data.getSpriteElements().get(objectSprite.getCurrentImagePosition());
                        elementData.setTransform(transform);
                        data.setPosition(position);
                        //System.out.println(data.getPosition());
                    }
                }

                for(ObjectSprite sprite : sprites) {
                    SpriteData data = spriteConfigs.get(sprite);
                    SpriteElementData elementData = data.getSpriteElements().get(sprite.getCurrentImagePosition());

                    Graphics2D bufferGraphics = elementData.getGraphics();

                    bufferGraphics.setTransform(elementData.getTransform());
                    Point position = data.getPosition();
                    //if(position!=null) {

                        bufferGraphics.drawImage(elementData.getImage(), position.getXInt(), position.getYInt(), null);
                    //}
                }

                writeInMain();

                if (update) {
                    thread.pause();
                    update = false;
                }
                /*
                for(Wrappers) {
                    Wrapper wrapper;

                    for(int i = 0 ; i < wrapper.effect.size(); i++) {
                        IEffect effect = wrapper.effect.get(i);
                        if(effect.isWait())
                            if(~~~) {

                            }


                    }
                }
                또는 Wrapper의 경우 Wrapper를 통합적으로 Tick을 관리하는 방법도 있음

                 */
            }
        });


    }

    public void writeInMain() {
        mainGraphics.drawImage(buffer, 0, 0, null);
    }

    public void handleList(ObjectSprite objectSprite, List<Pair<IEffect, EffectData>> effects, Point point, AffineTransform toApply) {
        Point result = point;

        for(int i = 0; i < effects.size(); i++) {
            Pair<IEffect, EffectData> effectPair = effects.get(i);
            IEffect effect = effectPair.getKey();
            Interval interval = effectPair.getValue().getInterval();
            Object[] parameter = effectPair.getValue().getParameters();

            if(handleInterval(interval)) {
                SpriteData spriteData = spriteConfigs.get(objectSprite);
                SpriteElementData elementData = spriteData.getSpriteElements().get(objectSprite.getCurrentImagePosition());

                //AffineTransform past = elementData.getTransform();
                result =  effect.editImage(paletteSize, objectSprite.getPosition(), result,
                        objectSprite.getCurrentImage(), elementData.getImage(), elementData.getGraphics(),
                        toApply, elementData.getTransform(), interval.getIntervalPercent(), parameter);
                //System.out.println(result);
                //point = handleEffect(objectSprite, effect, point, interval.getIntervalPercent(), toApply, parameter);
            } else {
                effects.remove(i);
                i--;
            }
        }

        point.setX(result.getX());
        point.setY(result.getY());
        //spriteConfigs.get(objectSprite).setPosition(point);
        //return point;
    }

    public boolean handleInterval(Interval interval) {
        if(interval.addWait()) {
            if(interval.addTick()) {
                return false;
            } else {

            }
        }

        return true;
    }

    public void handleEffect(ObjectSprite sprite, IEffect effect, Point point, double percent, AffineTransform toApplyTransform, Object... parameters) {
        SpriteData spriteData = spriteConfigs.get(sprite);
        SpriteElementData elementData = spriteData.getSpriteElements().get(sprite.getCurrentImagePosition());

        //AffineTransform past = elementData.getTransform();
        Point result =  effect.editImage(paletteSize, sprite.getPosition(), point,
                sprite.getCurrentImage(), elementData.getImage(), elementData.getGraphics(),
                toApplyTransform, elementData.getTransform(), percent, parameters);

        //elementData.setTransform(toApplyTransform);
        
        //past.concatenate(elementData.getTransform());

        //return result;
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

    public void enableEffects(int number) {

    }
}
