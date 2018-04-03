package com.GoldenMine.구버전.effectsV2;

import com.GoldenMine.utility.GoldenColor;
import com.GoldenMine.utility.Point;

public class EffectFadeOutV2 implements IEffectV2 {

    @Override
    public void getEditedPixelAt(Point paletteSize, Point imageSize, int pixelX, int pixelY, Point original, GoldenColor originalRGB, Point changed, GoldenColor currentRGB,  double percent) {
        currentRGB.setA(originalRGB.getA() - (int) (originalRGB.getA()/10000D*percent));
    }
}
