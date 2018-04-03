package com.GoldenMine.구버전;

public enum DefaultActions {
    LEFT_FLY_AWAY("ActionFly-LA"),
    LEFT_FLY_COME("ActionFly-LC"),
    RIGHT_FLY_AWAY("ActionFly-RA"),
    RIGHT_FLY_COME("ActionFly-RC"),
    TOP_FLY_AWAY("ActionFly-TA"),
    TOP_FLY_COME("ActionFly-TC"),
    BOTTOM_FLY_AWAY("ActionFly-BA"),
    BOTTOM_FLY_COME("ActionFly-BC"),

    SCALE_BIGGER("ActionScale-B")
    ;
    String type;

    DefaultActions(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
