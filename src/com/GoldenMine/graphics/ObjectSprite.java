package com.GoldenMine.graphics;

import com.GoldenMine.effects.IEffect;
import com.GoldenMine.utility.EffectData;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;
import com.GoldenMine.wrappers.EffectWrapper;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    /*
    ImageSprite - 그냥 기본적인 스프라이트
    LabelSprite - 라벨을 담는 스프라이트(이미지화 해야함)
     */

    protected List<BufferedImage> images = new ArrayList<BufferedImage>();

    public ObjectSprite(BufferedImage image) {
        addImage(image);
    }

    public ObjectSprite(File file) {
        this(getImage(file));
    }

    public ObjectSprite(String route) {
        this(new File(route));
    }

    public ObjectSprite(Font font, String str, Color color) {
        this(makeImageFromText(font, str, color));
    }

    List<Pair<IEffect, EffectData>> effects = new LinkedList<>();
    List<EffectWrapper> wrappers = new LinkedList<>();



    private Point position = new Point();

    private int currentImagePosition;

    public void setPosition(Point point) {
        position.setX(point.getX());
        position.setY(point.getY());
    }

    public void setPosition(int x, int y) {
        position.setX(x);
        position.setY(y);
    }


    /*public void addEffect(String effect, IntervalSpeed speed, Object... parameters) {
        addEffect(effect, speed, false, parameters);
    }

    public void addEffect(String effect, int wait, int interval, Object... parameters) {
        addEffect(effect, wait, interval, false, parameters);
    }*/

    public void addEffect(String effect, IntervalSpeed speed, boolean natural, Object... parameters) {
        IEffect iEffect = Palette.getEffect(effect);

        effects.add(new Pair<>(iEffect, new EffectData(Palette.getInterval(iEffect, speed, natural), parameters)));
    }

    public void addEffect(String effect, int wait, int interval, boolean natural, Object... parameters) {
        effects.add(new Pair<>(Palette.getEffect(effect), new EffectData(new Interval(wait, interval, natural), parameters)));
    }

    public void addWrapper(EffectWrapper wrapper) {
        wrappers.add(wrapper);
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

        setPosition((int)Math.round((x + x2) / 2), (int)Math.round((y + y2) / 2));
        System.out.println(position);
    }

    private static BufferedImage getImage(File file) {
        try {
            return ImageIO.read(file);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public List<BufferedImage> getImages() {
        return images;
    }

    public BufferedImage getCurrentImage() {
        return images.get(getCurrentImagePosition());
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

    private static BufferedImage defaultImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
    private static Graphics2D defaultGraphics2D = (Graphics2D) defaultImage.getGraphics();

    public static Point getTextSize(Font font, String text) {
        FontMetrics fm = defaultGraphics2D.getFontMetrics(font);

        return new Point(fm.stringWidth(text), font.getSize());
    }

    public static BufferedImage makeImageFromText(Font font, String text, Color color) {
        Point size = getTextSize(font, text);

        BufferedImage image = new BufferedImage(size.getXInt(), size.getYInt()*2, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(color);
        g.setFont(font);

        g.drawString(text, 0, size.getYInt());

        return image;
    }
}
