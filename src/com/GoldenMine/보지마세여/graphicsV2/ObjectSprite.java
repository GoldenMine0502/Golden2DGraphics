package com.GoldenMine.보지마세여.graphicsV2;

import com.GoldenMine.actions.IAction;
import com.GoldenMine.보지마세여.effectsV2.IEffectV2;
import com.GoldenMine.events.IEvent;
import com.GoldenMine.graphics.Palette;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.Point;
import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ObjectSprite {
    /*

    Event 등록


     */

    private List<BufferedImage> images = new ArrayList<BufferedImage>();

    private List<BufferedImage> scaledImages = new ArrayList<>();

    private double currentScale = 1.0;

    //BufferedImage original;

    private int currentImagePoint = 0;


    private Point point = new Point();

    private HashMap<IEvent, List<Pair<IEffectV2, Interval>>> eventEffects = new HashMap<>();
    private HashMap<IEffectV2, Interval> effects = new HashMap<>();

    private HashMap<IEvent, List<Pair<IAction, Interval>>> eventActions = new HashMap<>();
    private HashMap<IAction, Interval> actions = new HashMap<>();


    List<Pair<IEffectV2, Interval>> enabledEffects = new LinkedList<>();

    List<Pair<IAction, Interval>> enabledActions = new LinkedList<>();

    public ObjectSprite(Palette palette, BufferedImage image) {
        images.add(image);
        //original = image;
        currentImagePoint = 0;
        setScale(4);
    }

    public ObjectSprite() {
        currentImagePoint = -1;
        setScale(4);
    }

    public void setPoint(Point p) {
        setPoint(p.getXInt(), p.getYInt());
    }

    public void setPoint(int x, int y) {
        point.setX(x);
        point.setY(y);
    }

    public void setPointCenter(Point p) {
        BufferedImage original = getCurrentImage();

        int x = p.getXInt();
        int y = p.getYInt();
        int x2 = x + original.getWidth();
        int y2 = y + original.getHeight();

        point.setX((x+x2)/2);
        point.setY((y+y2)/2);
    }

    public void setScale(double scale) {
        currentScale = scale;
        List<BufferedImage> newImages = new ArrayList<>();

        for(int i = 0 ; i < images.size(); i++) {
            BufferedImage original = images.get(i);

            BufferedImage image = new BufferedImage((int)(original.getWidth()*scale), (int)(original.getHeight()*scale), BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().drawImage(original, (int)(original.getWidth()/(scale*2)), (int)(original.getHeight()/4*(scale)), null);
        }

        for(int i = scaledImages.size()-1; i >= 0; i--) {
            scaledImages.get(i).flush();
            scaledImages.remove(i);
        }

        for(int i = 0; i < newImages.size(); i++) {
            scaledImages.add(newImages.get(i));
        }
    }

    public double getScale() {
        return currentScale;
    }

    public Point getPosition() {
        return point;
    }

    public BufferedImage getCurrentImage() {
        return scaledImages.get(currentImagePoint);
    }

    public List<BufferedImage> getImages() {
        return scaledImages;
    }

    public void setImagePoint(int point) {
        currentImagePoint = point;
    }

    public void registerEffect(IEffectV2 effect, int wait, int interval) {
        effects.put(effect, new Interval(wait, interval));
    }

    public void registerAction(IAction action, int wait, int interval) {
        actions.put(action, new Interval(wait, interval));
    }

    public void enableEffect(IEffectV2 effect) {
        Interval interval = effects.get(effect);

        enabledEffects.add(new Pair<>(effect, new Interval(interval.getWait(), interval.getInterval())));
    }

    public void enableAction(IAction effect) {
        Interval interval = actions.get(effect);

        enabledActions.add(new Pair<>(effect, new Interval(interval.getWait(), interval.getInterval())));
    }

    public void registerEffect(IEvent event, IEffectV2 effect, int wait, int interval) {

        if(!eventEffects.containsKey(event)) {
            eventEffects.put(event, new ArrayList<>());
        }
        eventEffects.get(event).add(new Pair<>(effect, new Interval(wait, interval)));
    }

    public void enableEventEffects(IEvent event) {
        enabledEffects.addAll(eventEffects.get(event));
    }

    public void registerEvent(IEvent event) {
        eventEffects.put(event, new ArrayList<>());
    }

    public int getCurrentImagePosition() {
        return currentImagePoint;
    }
}
