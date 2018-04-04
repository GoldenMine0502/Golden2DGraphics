package com.GoldenMine.graphics;

import com.GoldenMine.effects.*;
import com.GoldenMine.events.InvokeEvent;
import com.GoldenMine.thread.threadAPI.APIMultiThread;
import com.GoldenMine.thread.threadAPI.APIThreadHandler;
import com.GoldenMine.thread.threadAPI.unit.TimeUnit;
import com.GoldenMine.utility.EffectData;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private APIMultiThread thread;

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

    public void removeSprite(int index) {
        spriteConfigs.remove(sprites.remove(index));
    }

    public void removeSprite(ObjectSprite sprite) {
        sprites.remove(sprite);
        spriteConfigs.remove(sprite);
    }

    class SpriteData {
        private List<SpriteElementData> elements = new ArrayList<>();
        private Point position = new Point();
        private Point positionAllowed = new Point();
        private Graphics2D graphics2D;
        private AffineTransform transform;

        SpriteData(ObjectSprite sprite, boolean transparency) {
            Graphics2D g2d = buffer.createGraphics();
            this.graphics2D = g2d;


            List<BufferedImage> originalImgs = sprite.getImages();

            for (BufferedImage originalImg : originalImgs) {
                elements.add(new SpriteElementData(originalImg, transparency));
            }

            position = new Point(sprite.getPosition().getX(), sprite.getPosition().getY());
            positionAllowed = new Point(position.getX(), position.getY());

            transform = g2d.getTransform();
        }


        public AffineTransform getTransform() {
            return transform;
        }

        public void setTransform(AffineTransform transform) {
            this.transform = transform;
        }

        public List<SpriteElementData> getSpriteElements() {
            return elements;
        }

        public Point getPosition() {
            return position;
        }

        public Point getAllowedPosition() {
            return positionAllowed;
        }

        public void setPosition(Point position) {
            this.position = position;
        }

        public void setAllowedPosition(Point allowedPosition) {
            this.positionAllowed = allowedPosition;
        }

        public Graphics2D getGraphics() {
            return graphics2D;
        }
    }

    class SpriteElementData {
        private BufferedImage copied;

        SpriteElementData(BufferedImage originalImg, boolean transparency) {
            Graphics2D g2d = buffer.createGraphics();

            copied = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            copied.getGraphics().drawImage(originalImg, 0, 0, null);

            if (transparency) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
            }

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        }

        public BufferedImage getImage() {
            return copied;
        }

    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        mainGraphics = getGraphics();
    }

    public Palette(Point size, int fps) {
        this(size, fps, 1);
    }

    public Palette(Point size, int fps, int time) {
        setSize(size.getXInt(), size.getYInt());

        setVisible(true);

        paletteSize = new Point(size.getX(), size.getY());


        buffer = new BufferedImage(size.getXInt(), size.getYInt(), BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = buffer.getGraphics();

        mainGraphics = getGraphics();


        thread = new APIMultiThread(TimeUnit.FPS, fps, time, new APIThreadHandler() {

            @Override
            public void onThreadExecute() throws InterruptedException {
                /*
                여기에 넘버 기반 이펙트 실행 메소드를 넣는다.
                 */
                /*if(lastDimension==null) {
                    lastDimension = getSize();
                }

                Dimension d = getSize();
                if(lastDimension.height!=d.height || lastDimension.width!=d.width) {
                    buffer.flush();
                    buffer = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
                }

                Point magnification = new Point(d.getWidth()/size.getX(), d.getHeight()/size.getY());*/


                bufferGraphics.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());

                for (ObjectSprite objectSprite : sprites) {
                    AffineTransform transform = new AffineTransform();
                    Point position = new Point(objectSprite.getPosition().getX(), objectSprite.getPosition().getY());

                    SpriteData data = spriteConfigs.get(objectSprite);
                    Point positionAllowed = new Point(data.getAllowedPosition().getX(), data.getAllowedPosition().getY());


                    handleList(objectSprite, objectSprite.effects, position, positionAllowed, transform);

                    /*
                    for (EffectWrapper wrapper : objectSprite.wrappers) {
                        handleList(objectSprite, wrapper.getEffects(), position, transform);
                    }*/

                    if (objectSprite.effects.size() > 0) {
                        //SpriteElementData elementData = data.getSpriteElements().get(objectSprite.getCurrentImagePosition());
                        data.setTransform(transform);
                        data.setPosition(position);
                    }
                    data.setAllowedPosition(positionAllowed);
                }


                for (ObjectSprite sprite : sprites) {
                    SpriteData data = spriteConfigs.get(sprite);
                    SpriteElementData elementData = data.getSpriteElements().get(sprite.getCurrentImagePosition());

                    Graphics2D bufferGraphics = data.getGraphics();

                    bufferGraphics.setTransform(data.getTransform());
                    Point position = data.getPosition();

                    bufferGraphics.drawImage(elementData.getImage(), (int) (position.getXInt()), (int) (position.getYInt()), null);

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                java.awt.Point mouseP = e.getPoint();
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    ObjectSprite sprite = sprites.get(i);
                    SpriteData data = spriteConfigs.get(sprite);

                    if (sprite instanceof InvokeEvent) {
                        Point spriteP = data.getAllowedPosition();
                        Point spriteP2 = new Point(spriteP.getX() + sprite.getCurrentImage().getWidth(), spriteP.getY() + sprite.getCurrentImage().getHeight());


                        if (spriteP.getX() <= mouseP.getX() && spriteP.getY() <= mouseP.getY()) {
                            if (spriteP2.getX() >= mouseP.getX() && spriteP2.getY() >= mouseP.getY()) {
                                ((InvokeEvent) sprite).onClick(new Point(mouseP.getX(), mouseP.getY()), true);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                java.awt.Point p = e.getPoint();

                for (ObjectSprite sprite : sprites) {
                    if (sprite instanceof InvokeEvent) {
                        ((InvokeEvent) sprite).onClick(new Point(p.getX(), p.getY()), false);
                    }
                }
                //checkClicked(e.getPoint(), false);
            }
        });
    }

    public void writeInMain() {
        mainGraphics.drawImage(buffer, 0, 0, null);
    }

    public void handleList(ObjectSprite objectSprite, List<Pair<IEffect, EffectData>> effects, Point point, Point pointAllowed, AffineTransform toApply) {
        Point result = point;

        for (int i = 0; i < effects.size(); i++) {
            Pair<IEffect, EffectData> effectPair = effects.get(i);
            IEffect effect = effectPair.getKey();
            Interval interval = effectPair.getValue().getInterval();
            Object[] parameter = effectPair.getValue().getParameters();

            if (handleInterval(interval)) {
                SpriteData spriteData = spriteConfigs.get(objectSprite);
                SpriteElementData elementData = spriteData.getSpriteElements().get(objectSprite.getCurrentImagePosition());


                result = effect.editImage(paletteSize, objectSprite.getPosition(), result,
                        objectSprite.getCurrentImage(), elementData.getImage(), spriteData.getGraphics(),
                        toApply, spriteData.getTransform(), interval.getIntervalPercent(), parameter);

                if(effect instanceof PositionChangable) {
                    pointAllowed.setX(result.getX());
                    pointAllowed.setY(result.getY());
                }
            } else {
                effects.remove(i);
                i--;
            }
        }

        point.setX(result.getX());
        point.setY(result.getY());
    }

    public boolean handleInterval(Interval interval) {
        if (interval.addWait()) {
            if (interval.addTick()) {
                return false;
            } else {

            }
        }

        return true;
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
