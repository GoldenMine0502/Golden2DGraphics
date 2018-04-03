package com.GoldenMine.wrappers;

import com.GoldenMine.effects.IEffect;
import com.GoldenMine.graphics.Palette;
import com.GoldenMine.utility.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class EffectWrapper {
    List<Pair<IEffect, EffectData>> effects = new ArrayList<>();
    //List<Pair<String, Interval>> actions = new ArrayList<>();
    //List<Pair<String, Interval>> relativeActions = new ArrayList<>();

    /*public void apply() {
        apply(sprite);
    }*/

    /*
    public void apply(ObjectSprite sprite) {

        for(int i = 0; i < effects.size(); i++) {
            Pair<String, Interval> effect = effects.get(i);
            sprite.enableEffect(effect.getKey(), effect.getValue().getWait(), effect.getValue().getInterval());
        }

        for(int i = 0; i < actions.size(); i++) {
            Pair<String, Interval> action = actions.get(i);
            sprite.enableAction(action.getKey(), action.getValue().getWait(), action.getValue().getInterval());
        }

        for(int i = 0; i < relativeActions.size(); i++) {
            Pair<String, Interval> action = relativeActions.get(i);
            sprite.enableRelativeAction(action.getKey(), action.getValue().getWait(), action.getValue().getInterval());
        }
    }*/


    public void addEffect(String effect, IntervalSpeed speed, Object... parameters) {
        effects.add(new Pair<>(Palette.getEffect(effect),
                new EffectData(Palette.getInterval(Palette.getEffect(effect), speed), parameters)));
    }

    public void addEffect(String effect, int wait, int interval, Object... parameters) {
        effects.add(new Pair<>(Palette.getEffect(effect),
                new EffectData(new Interval(wait, interval), parameters)));
    }

    public List<Pair<IEffect, EffectData>> getEffects() {
        return effects;
    }

}
