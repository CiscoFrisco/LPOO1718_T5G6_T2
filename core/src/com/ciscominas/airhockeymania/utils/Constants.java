package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import com.ciscominas.airhockeymania.controller.GameController;

public class Constants {

    public static final int APP_WIDTH = 400;
    public static final int APP_HEIGHT = 600;

    public static final String DATABASE = "results.db";
    public static final String RESULTS_TABLE = "results";

    public static final String ID_COLUMN = "id";
    public static final String SCORE1_COLUMN = "score1";
    public static final String SCORE2_COLUMN = "score2";
    public static final String DATE_COLUMN = "date";

    public static final int LIMIT = 10;

    public static final String MENU_MUSIC = "menu.mp3";
    public static final String MENU_SKIN =  "skin/glassy-ui.json";

    public static final float PUCK_X = GameController.ARENA_WIDTH/2;
    public static final float PUCK_Y = GameController.ARENA_HEIGHT/2;
    public static final float PUCK_RADIUS = GameController.ARENA_WIDTH/20;
    public static final float HANDLE_RADIUS = GameController.ARENA_WIDTH/16;
    public static final float HANDLE_X = GameController.ARENA_WIDTH/2;
    public static final float HANDLE_Y = GameController.ARENA_HEIGHT/12;


    public static final float BOT_X = GameController.ARENA_WIDTH/2;
    public static final float BOT_Y = 11*GameController.ARENA_HEIGHT/12;
    public static final float HARD_BOT_SENS = 5f;
    public static final float NORMAL_BOT_SENS = 3f;
    public static final float EASY_BOT_SENS = 1f;
    public static final float HARD_BOT_MAX_OFF = 0.5f;
    public static final float NORMAL_BOT_MAX_OFFSET = 1.0f;
    public static final float EASY_BOT_MAX_OFFSET = 2.0f;




    public static final String HANDLE_IMAGE_PATH = "handle.png";
    public static final String PUCK_IMAGE_PATH = "puck.png";
    public static final String LOGO_IMAGE_PATH = "air_hockey_mania.png";


    public static final int POWERUP_FREQUENCY = 5;

    public static final int WIN_SCORE = 5;


    public static final int UPPER_RIGHT = 0;
    public static final int UPPER_LEFT = 1;
    public static final int LOWER_RIGHT = 2;
    public static final int LOWER_LEFT = 3;
    public static final int RIGHT = 4;
    public static final int LEFT = 5;
    public static final int MID = 6;
    public static final int DOWN = 7;
    public static final int UP = 8;




}
