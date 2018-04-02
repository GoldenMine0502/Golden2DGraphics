package com.GoldenMine.보지마세여.effectsV2;

import com.GoldenMine.utility.GoldenColor;
import com.GoldenMine.utility.Point;

public class EffectFadeInV2 implements IEffectV2 {

    @Override
    public void getEditedPixelAt(Point paletteSize, Point imageSize, int pixelX, int pixelY, Point original, GoldenColor originalRGB, Point changed, GoldenColor currentRGB,  double percent) {
        currentRGB.setA((int) (originalRGB.getA()/10000D*percent));
    }
}
