package com.GoldenMine.utility;

public class EffectData {
    Interval interval;
    Object[] parameters;

    public EffectData(Interval interval, Object... parameters) {
        this.interval = interval;
        this.parameters = parameters;
    }

    public Interval getInterval() {
        return interval;
    }

    public Object[] getParameters() {
        return parameters;
    }

}