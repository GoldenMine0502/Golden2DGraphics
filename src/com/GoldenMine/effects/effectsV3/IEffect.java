package com.GoldenMine.effects.effectsV3;

import com.GoldenMine.utility.IntervalSpeed;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public interface IEffect {

    class EffectParameterException extends RuntimeException {
        public EffectParameterException(String message) {
            super(message);
        }
    }
    // 이펙트 픽셀단위 ->  파일(이미지)단위로 변경
    /*
    왜냐 이미지를 픽셀단위로 다루면 setRGB에 대한 리소스를 너무 많이 먹기 때문임 (실험 = TestImage_setRGB)
    대충 보니 이미지를 전체적으로 조율하는 부분은 Graphics의 AffineTransform클래스가 꽤 좋은 기능을 해주고 있는것으로 파악됨
    이전과 같이 OriginalImage, ChangedImage를 매개변수로 넣어주고(Palette사이즈 등도 포함)
    추가로 AffineTransform 객체도 넘겨주면 좋을것 같고

    Effect에 매개변수 넣을수 있는 기능까지 넣으면 좋을것 같다.

    문제의 BufferedImage.setRGB()의 synchronized.. 으득으득..!!!!!
    얘만 뺴도 뭔가 속도 몇배는 빨라질것 같은데 ㅠㅠ

    그리고 이번엔 동시에 여러 Transform 먹이는거 성공했으면 좋겠다. ㅎㅎ

    그리고 픽셀 일부만 다룰때만 따로 setRGB 기능을 활용하면 될 것 같음
    예를들자면, 파워포인트의 닦아내기 같은것들.

     */
    String getName();

    int getDefaultWaitTime(IntervalSpeed speed);
    int getDefaultInterval(IntervalSpeed speed);

    void editImage(Point paletteSize, Point imagePosition, BufferedImage original, BufferedImage changed, Graphics2D graphics2D, AffineTransform transform, double percent, Object... parameters);
}
