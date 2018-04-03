package com.GoldenMine.events;

import com.GoldenMine.구버전.graphicsV1.Palette;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DisableEvent extends IEvent {
    public DisableEvent(Palette palette) {
        super(palette);
    }

    @Override
    protected void listener(Palette palette) {
        palette.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                invoke();
            }
        });
    }


}
