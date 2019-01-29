package com.nitish.slateinteractivewhiteboardv3;

import java.io.Serializable;

public class Action implements Serializable{

    public static final long serialVersionUID = 1L;

    /* event constants */
    public static final int DOWN = 0;
    public static final int UP   = 1;
    public static final int MOVE = 2;

    /*Theme*/
    public static final int THEME_DEEPNIGHT = 10;
    public static final int THEME_AQUA   = 11;
    public static final int THEME_VALENTINE = 12;
    public static final int THEME_SOLARIZED = 13;
    public static final int THEME_ZOMBIE = 14;
    public static final int THEME_NEON = 15;

    /*ERASER*/
    public static final int ERASE_BY_CLIENT = 20;
    public static final int PEN_BY_CLIENT = 21;
    public static final int CLEAR = 30;


    /* properties */
    float x;
    float y;
    int type;

    Action(float x, float y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}