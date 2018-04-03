package com.GoldenMine.구버전.actions.relative;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;
import com.GoldenMine.utility.UtilityFade;

import java.awt.image.BufferedImage;

public class ActionScaleBigger implements IRelativeAction {
    @Override
    public Point getNextPosition(Point paletteSize, Point original, Point changed, BufferedImage image, double percent, Object... parameter) {

        double scale = percent/10000D;

        //if(scale<=0.1) {
        //    return new Point(-image.getWidth(), -image.getHeight());
        //} else {

        System.out.println(changed.getXInt() +", "+ image.getWidth()+", "+ scale + ", " + UtilityFade.getResizedScalePoint(changed.getXInt(), image.getWidth(), scale));
            return new Point(UtilityFade.getResizedScalePoint(changed.getXInt(), image.getWidth(), scale)
                    , UtilityFade.getResizedScalePoint(changed.getYInt(), image.getHeight(), scale));
        //}

    }

    @Override
    public String getName() {
        return "ActionScale-B";
    }

    @Override
    public int getDefaultWaitTime(IntervalSpeed speed) {
        return 0;
    }

    @Override
    public int getDefaultInterval(IntervalSpeed speed) {
        return 200-40*speed.getSpeed();
    }
}
