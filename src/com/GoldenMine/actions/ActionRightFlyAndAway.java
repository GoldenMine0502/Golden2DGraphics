package com.GoldenMine.actions;

import com.GoldenMine.utility.Point;

import java.awt.image.BufferedImage;

public class ActionRightFlyAndAway implements IAction {
    @Override
    public Point getNextPosition(Point paletteSize, Point original, BufferedImage image, double percent) {
        int startX = original.getXInt();
        //int startY = -image.getHeight();

        int goalX = paletteSize.getXInt();

        // goalX - startX = 이동해야하는 거리
        // 1차함수적으로 -> return new Point((goalX-startX)/10000D*percent, (goalY-startY)/10000D*percent)



        return new Point( startX + goalX/10000D*percent, original.getY());
    }
}
