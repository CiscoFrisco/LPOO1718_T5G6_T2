package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.Gdx;
import com.ciscominas.airhockeymania.controller.GameController;

/**
 * Useful constants that may be used by any other package.
 */
public class Constants {

    /**
     * File name for the database
     */
    public static final String DATABASE = "results.db";

    /**
     * Name of the table that will store the results
     */
    public static final String RESULTS_TABLE = "results";

    /**
     * Name of the ID column
     */
    public static final String ID_COLUMN = "id";

    /**
     * Name of the score1 column (player)
     */
    public static final String SCORE1_COLUMN = "score1";

    /**
     * Name of the score2 column (bot)
     */
    public static final String SCORE2_COLUMN = "score2";

    /**
     * Name of the date column
     */
    public static final String DATE_COLUMN = "date";

    /**
     * Limit for the number of results that may be retrieved from the database
     */
    public static final int LIMIT = 10;

    /**
     * Initial x coordinate of the puck in meters
     */
    public static final float PUCK_X = GameController.ARENA_WIDTH/2;

    /**
     * Initial y coordinate of the puck in meters
     */
    public static final float PUCK_Y = GameController.ARENA_HEIGHT/2;

    /**
     * Radius of the puck in meters
     */
    public static final float PUCK_RADIUS = GameController.ARENA_WIDTH/20;

    /**
     * Radius of the handle in meters
     */
    public static final float HANDLE_RADIUS = GameController.ARENA_WIDTH/16.0f;

    /**
     * Initial x coordinate of the handle in meters
     */
    public static final float HANDLE_X = GameController.ARENA_WIDTH/2;

    /**
     * Initial y coordinate of the handle in meters
     */
    public static final float HANDLE_Y = GameController.ARENA_HEIGHT/12;

    /**
     * Initial x coordinate of the bot in meters
     */
    public static final float BOT_X = GameController.ARENA_WIDTH/2;

    /**
     * Initial y coordinate of the bot in meters
     */
    public static final float BOT_Y = 11*GameController.ARENA_HEIGHT/12;

    public static final String HIT_SOUND = "hit.mp3";

    public static final String DEFAULT_DIFFICULTY = "Medium";

    public static final String PUCK_FILE = "puck.png";
    public static final String HANDLE_FILE = "handle.png";
    public static final String EDGE_FILE = "blue.png";
    public static final String POWERUP_FILE = "powerup.png";


}
