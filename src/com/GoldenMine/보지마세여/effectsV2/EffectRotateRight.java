package com.GoldenMine.보지마세여.effectsV2;

import com.GoldenMine.utility.GoldenColor;
import com.GoldenMine.utility.Point;
import com.GoldenMine.utility.UtilityFade;

public class EffectRotateRight implements IEffectV2 {
    @Override
    public void getEditedPixelAt(Point paletteSize, Point imageSize, int pixelX, int pixelY, Point original, GoldenColor originalRGB, Point changed, GoldenColor currentRGB, double percent) {
        Point p = UtilityFade.getRotatePoint((int)(imageSize.getX() + original.getX()/2), (int)(imageSize.getY() + original.getY()/2), original.getX(), original.getY(), -percent/10000D*360);
        changed.setX(p.getX());
        changed.setY(p.getY());
    }
}
