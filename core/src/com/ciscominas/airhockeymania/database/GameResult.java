package com.ciscominas.airhockeymania.database;

import java.sql.Date;

/**
 * Represents the result of a game (if it was fully played).
 * Contains the player score, the bot score and the date.
 */
public class GameResult {

    /**
     * Player score
     */
    private int score1;

    /**
     * Bot score
     */
    private int score2;

    /**
     * Date of the game, in milliseconds
     */
    private long dateMillis;

    /**
     * Creates a GameResult object.
     * @param score1 player score
     * @param score2 bot score
     * @param date date of the game in milliseconds
     */
    public GameResult(int score1, int score2, long date) {
        this.score1 = score1;
        this.score2 = score2;
        this.dateMillis = date;
    }

    /**
     * Returns the player score
     * @return the player score
     */
    public int getScore1() {
        return score1;
    }

    /**
     * Returns the bot score
     * @return the bot score
     */
    public int getScore2() {
        return score2;
    }

    /**
     * Returns the date of the game in MM-DD-YY format
     * @return the date of the game
     */
    public Date getDate() {
        return new Date(dateMillis);
    }

    /**
     * Returns the date of the game
     * @return the date of the game
     */
    public long getDateMillis(){
        return dateMillis;
    }

}
