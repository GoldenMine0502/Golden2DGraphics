package com.GoldenMine.graphics;

import com.GoldenMine.effects.IEffect;
import com.GoldenMine.events.IEvent;
import com.GoldenMine.utility.Interval;
import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ObjectSprite {
    BufferedImage original;
    Point point = new Point();

    private HashMap<IEvent, HashMap<IEffect, Interval>> eventEffects = new HashMap<>();
    private HashMap<IEffect, Interval> effects = new HashMap<>();

    HashMap<IEffect, Interval> enabledEffects = new HashMap<>();

    public ObjectSprite(BufferedImage image) {
        this.original = image;
    }

    public void setPoint(Point p) {
        setPoint(p.getXInt(), p.getYInt());
    }

    public void setPoint(int x, int y) {
        point.setX(x);
        point.setY(y);
    }

    public void setPointCenter(Point p) {
        int x = p.getXInt();
        int y = p.getYInt();
        int x2 = x + original.getWidth();
        int y2 = y + original.getHeight();

        point.setX((x+x2)/2);
        point.setY((y+y2)/2);
    }

    public void registerEffect(IEvent event, IEffect effect, int wait, int interval) {

        if(!eventEffects.containsKey(event)) {
            eventEffects.put(event, new HashMap<>());
        }
        eventEffects.get(event).put(effect, new Interval(wait, interval));
    }

    public void registerEffect(IEffect effect, int wait, int interval) {
        effects.put(effect, new Interval(wait, interval));
    }

    public void registerEvent(IEvent event) {
        eventEffects.put(event, new HashMap<>());
    }

    public void enableEventEffects(IEvent event) {
        enabledEffects.putAll(eventEffects.get(event));
    }

    public void enableEffect(IEffect effect) {
        Interval interval = effects.get(effect);

        enabledEffects.put(effect, new Interval(interval.getWait(), interval.getInterval()));
    }
}
