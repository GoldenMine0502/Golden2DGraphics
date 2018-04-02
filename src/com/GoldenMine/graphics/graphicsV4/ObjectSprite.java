package com.GoldenMine.graphics.graphicsV4;

import com.GoldenMine.actions.IAction;
import com.GoldenMine.actions.relative.IRelativeAction;
import com.GoldenMine.effects.effectsV4.IEffect;
import com.GoldenMine.utility.EffectData;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;
import com.GoldenMine.wrappers.EffectWrapper;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ObjectSprite {
    /*
    내가 만들어야 하는것 =
    동시에 여러 이펙트 적용

    추가하려는 Effect에 번호를 먹이고
    addEffect 등의 메소드로 번호 + Effect 저장

    enableEffect(번호) 입력시 해당하는 이펙트 작동

    Palette에서는 add된 모든 이펙트를 검사하되 해당하는 숫자의 effect만 실행

    add된 모든 이펙트가 실행 완료되었을시 검사 목록에서 제낌


    -> 현재 action, relativeaction, effect를 재사용할 최선의 방법으로 생각됨
     */



    List<Pair<IEffect, EffectData>> effects = new LinkedList<>();
    List<EffectWrapper> wrappers = new LinkedList<>();

    private List<BufferedImage> images = new ArrayList<BufferedImage>();

    private Point position = new Point();

    private int currentImagePosition;

    public ObjectSprite(BufferedImage image) {
        addImage(image);
    }

    public ObjectSprite(File file) {
        this(getImage(file));
    }

    public ObjectSprite(String route) {
        this(new File(route));
    }

    public void setPosition(Point point) {
        position.setX(point.getX());
        position.setY(point.getY());
    }

    public void setPositionInCenter(Point point) {
        double x = point.getX();
        double y = point.getY();


        double x2 = -1;
        double y2 = -1;

        for(int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);

            double x3 = image.getWidth();
            double y3 = image.getHeight();

            if(x2 < x3) {
                x2 = x3;
            }

            if(y2 < y3) {
                y2 = y3;
            }
        }

        position.setX((x + x2) / 2);
        position.setY((y + y2) / 2);
    }

    public void addImage(BufferedImage image) {
        images.add(image);
    }

    public void addImage(File file) {
        try {
            addImage(ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addImage(String route) {
        addImage(new File(route));
    }

    private static BufferedImage getImage(File file) {
        try {
            return ImageIO.read(file);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void addEffect(String effect, IntervalSpeed speed, Object... parameters) {
        IEffect iEffect = Palette.getEffect(effect);

        effects.add(new Pair<>(iEffect, new EffectData(Palette.getInterval(iEffect, speed), parameters)));
    }

    public void addEffect(String effect, int wait, int interval, Object... parameters) {
        effects.add(new Pair<>(Palette.getEffect(effect), new EffectData(new Interval(wait, interval), parameters)));
    }

    public void addWrapper(EffectWrapper wrapper) {
        wrappers.add(wrapper);
    }

    public List<BufferedImage> getImages() {
        return images;
    }

    public Point getPosition() {
        return position;
    }

    public void setCurrentImagePosition(int n) {
        currentImagePosition = n;
    }

    public int getCurrentImagePosition() {
        return currentImagePosition;
    }

    public BufferedImage getCurrentImage() {
        return images.get(getCurrentImagePosition());
    }
}
