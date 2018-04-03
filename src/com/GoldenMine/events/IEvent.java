package com.GoldenMine.events;

import com.GoldenMine.구버전.graphicsV1.Palette;

public abstract class IEvent {
    Palette palette;

    public IEvent(Palette palette) {
        this.palette = palette;
    }

    protected abstract void listener(Palette palette);

    protected void invoke() {
        palette.invokeEvent(this);
    }
    //void invokeEvent()
}
