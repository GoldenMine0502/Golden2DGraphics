package com.GoldenMine.events;

import com.GoldenMine.effects.IEffect;
import com.GoldenMine.graphics.Palette;

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
