package com.GoldenMine.utility;

import com.GoldenMine.wrappers.EffectWrapper;

public class DefaultWrappers {
    public static final EffectWrapper RIGHT_COME_AND_FADEIN = new EffectWrapper();
    public static final EffectWrapper LEFT_AWAY_AND_FADEOUT = new EffectWrapper();

    static {
        RIGHT_COME_AND_FADEIN.addEffect(DefaultEffects.RIGHT_FLY_COME, 0, 120);
        RIGHT_COME_AND_FADEIN.addEffect(DefaultEffects.FADE_IN, 60, 120);

        LEFT_AWAY_AND_FADEOUT.addEffect(DefaultEffects.LEFT_FLY_AWAY, 0, 120);
        LEFT_AWAY_AND_FADEOUT.addEffect(DefaultEffects.FADE_OUT, 0, 60);
    }
}
