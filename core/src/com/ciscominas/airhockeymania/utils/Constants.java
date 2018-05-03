package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final int APP_WIDTH = 400;
    public static final int APP_HEIGHT = 600;
    public static final float WORLD_TO_SCREEN = 32;


    public static final Vector2 WORLD_GRAVITY = new Vector2(0, 0);
    public static final float L_EDGE_X = 0;
    public static final float R_EDGE_X = 20f;
    public static final float EDGE_Y = 0;
    public static final float EDGE_DENSITY = 0f;
    public static final float EDGE_WIDTH = 1f;
    public static final float EDGE_HEIGHT = 40f;

    public static final float PUCK_X = 10f;
    public static final float PUCK_Y = 7.5f;
    public static final float PUCK_RADIUS = 0.5f;
    public static final float HANDLE_RADIUS = 0.5f;
    public static final float HANDLE_X = 10f;
    public static final float HANDLE_Y = 1f;

    public static final String HANDLE_IMAGE_PATH = "handle.png";
    public static final String PUCK_IMAGE_PATH = "puck.png";
    public static final String LOGO_IMAGE_PATH = "air_hockey_mania.png";

    public static final float PUCK_DENSITY = 100f;
    public static final float RESTITUTION = 1f;

    public static final float UPL_GL_X = 0;
    public static final float UP_GL_Y = 15;
    public static final float GL_WIDTH = 15;
    public static final float GL_HEIGHT = 1;

    public static final float MID_X = 0;
    public static final float MID_Y = 7.5f;
    public static final float MID_WIDTH = 40;
    public static final float MID_HEIGHT = 0.1f;

    public static final float UPR_GL_X = 20;

    public static final float DOWNL_GL_X = 0;
    public static final float DOWN_GL_Y = 0;

    public static final float DOWNR_GL_X = 20;

    public final static short PUCK_BODY = 0x0001;
    public final static short HANDLE_BODY = 0x0002;
    public final static short LINE_BODY = 0x0004;


}
