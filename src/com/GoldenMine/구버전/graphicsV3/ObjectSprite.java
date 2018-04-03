package com.GoldenMine.구버전.graphicsV3;

import com.GoldenMine.구버전.DefaultActions;
import com.GoldenMine.구버전.actions.IAction;
import com.GoldenMine.구버전.actions.relative.IRelativeAction;
import com.GoldenMine.구버전.effectsV3.IEffect;
import com.GoldenMine.utility.*;
import com.GoldenMine.wrappers.EffectWrapper;
import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ObjectSprite {
    private List<BufferedImage> images = new ArrayList<BufferedImage>();
    private int currentImagePoint = 0;
    private Point point = new Point();

    List<Pair<IEffect, Interval>> enabledEffects = new LinkedList<>();

    List<Pair<IAction, Interval>> enabledActions = new LinkedList<>();

    List<Pair<IRelativeAction,Interval>> enabledRelativeActions = new LinkedList<>();

    List<EffectWrapper> enabledWrappers = new LinkedList<>();

    public ObjectSprite(BufferedImage image) {
        addImage(image);
    }

    public void enableWrapper(EffectWrapper wrapper) {

    }

    public void enableAction(DefaultActions action, IntervalSpeed speed) {
        enableAction(action.getType(), speed);
    }

    public void enableAction(DefaultActions action, int wait, int interval) {
        enableAction(action.getType(), wait, interval);
    }

    public void enableEffect(String effect, IntervalSpeed speed) {
        IEffect iEffect = Palette.getEffect(effect);
        //System.out.println(iEffect.getName());
        enabledEffects.add(new Pair<>(iEffect, Palette.getInterval(iEffect,speed)));
    }

    public void enableEffect(String effect, int wait, int interval) {
        IEffect iEffect = Palette.getEffect(effect);
        //System.out.println(iEffect.getName());
        enabledEffects.add(new Pair<>(iEffect, new Interval(wait, interval)));
    }

    public void enableAction(String action, IntervalSpeed speed) {
        IAction iAction = Palette.getAction(action);
        //System.out.println(iAction.getName());
        enabledActions.add(new Pair<>(iAction, Palette.getInterval(iAction, speed)));
    }

    public void enableAction(String action, int wait, int interval) {
        IAction iAction = Palette.getAction(action);
        //System.out.println(iAction.getName());
        enabledActions.add(new Pair<>(iAction, new Interval(wait, interval)));
    }

    public void enableRelativeAction(String action, IntervalSpeed speed) {
        IRelativeAction iAction = Palette.getRelativeAction(action);
        //System.out.println(iAction.getName());
        enabledRelativeActions.add(new Pair<>(iAction, Palette.getInterval(iAction, speed)));
    }

    public void enableRelativeAction(String action, int wait, int interval) {
        IRelativeAction iAction = Palette.getRelativeAction(action);
        //System.out.println(iAction.getName());
        enabledRelativeActions.add(new Pair<>(iAction, new Interval(wait, interval)));
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
        int x2 = x + original.getWidth();
        int y = p.getYInt();
        int y2 = y + original.getHeight();

        point.setX((x+x2)/2);
        point.setY((y+y2)/2);
    }

    public void addImage(BufferedImage image) {
        images.add(image);
    }

    public void setImagePoint(int point) {
        currentImagePoint = point;
    }

    public BufferedImage getCurrentImage() {
        return images.get(currentImagePoint);
    }

    public int getCurrentImagePoint() {
        return currentImagePoint;
    }

    public void setCurrentImagePoint(int num) {
        currentImagePoint = num;
    }

    public Point getPosition() {
        return point;
    }

    public List<BufferedImage> getImages() {
        return images;
    }
}
