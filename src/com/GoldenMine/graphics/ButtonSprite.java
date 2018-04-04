package com.GoldenMine.graphics;

import com.GoldenMine.events.InvokeEvent;
import com.GoldenMine.impl.SpriteException;
import com.GoldenMine.utility.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ButtonSprite extends ObjectSprite implements InvokeEvent {
    //int currentImagePoint = 0;

    boolean clicked = false;

    public interface ButtonClickedEvent {
        void onClick(Point point);
    }

    List<ButtonClickedEvent> clickEvents = new ArrayList<>();

    public void addButtonClickedEvent(ButtonClickedEvent clicked) {
        clickEvents.add(clicked);
    }

    @Override
    public void onClick(Point position, boolean clicked) {

        if(clicked==true) {
            this.clicked = true;
        } else {
            if(this.clicked==true) {
                this.clicked = false;
                for (ButtonClickedEvent e : clickEvents) {
                    e.onClick(position);
                }
            }
        }
        //System.out.println(clicked);
    }

    @Override
    public void addImage(BufferedImage image) {
        throw new SpriteException("cannot support on ButtonSprite");
    }

    @Override
    public void addImage(File file) {
        throw new SpriteException("cannot support on ButtonSprite");
    }

    @Override
    public void addImage(String route) {
        throw new SpriteException("cannot support on ButtonSprite");
    }

    @Override
    public void addImage(Font font, String text, Color color) {
        throw new SpriteException("cannot support on ButtonSprite");
    }

    public void addImage(BufferedImage image1, BufferedImage image2) {
        super.addImage(image1);
        super.addImage(image2);
    }

    public void addImage(File file, File file2) {
        super.addImage(file);
        super.addImage(file2);
    }

    public void addImage(String route, String route2) {
        super.addImage(route);
        super.addImage(route2);
    }

    public void addImage(Font font, String text, Color color, Font font2, String text2, Color color2) {
        addImage(makeImageFromText(font,text,color), makeImageFromText(font2,text2,color2));
    }


    @Override
    public int getCurrentImagePosition() {
        if(clicked) {
            //System.out.println(1);
            return super.getCurrentImagePosition()*2+1;
        } else {
            //System.out.println(2);
            return super.getCurrentImagePosition()*2;
        }
    }
}
