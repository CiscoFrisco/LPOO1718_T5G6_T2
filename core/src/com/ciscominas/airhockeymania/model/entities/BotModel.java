package com.ciscominas.airhockeymania.model.entities;

/**
 * Holds info about a Bot.
 */
public class BotModel extends EntityModel{

    /**
     * Number of goals scored in a single game
     */
    private int score;

    /**
     * Difficulty/Intelligence of the bot, Medium by default
     */
    private String difficulty = "Medium";
    /**
     * Boolean that indicates whether or not the handle is able to move.
     */
    private boolean controlOn = true;

    /**
     * Creates a BotModel object.
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param width the width in meters
     * @param height the height in meters
     */
    public BotModel(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Gets this model's type.
     * @return this model's type.
     */
    @Override
    public ModelType getType() {
        return ModelType.BOT;
    }

    /**
     * Returns this bot's score at the current game.
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Increments this bot's score
     */
    public void incScore()
    {
        score++;
    }

    /**
     * Resets this bot's score
     */
    public void resetScore()
    {
        score = 0;
    }

    /**
     * Returns this bot difficulty
     * @return the difficulty
     */
    public String getDifficulty()
    {
        return difficulty;
    }

    /**
     * Updates hanlde's ability to move.
     * @param controlOn Indicates whether or not the handle is able to move.
     */
    public void setControlOn(boolean controlOn) {
        this.controlOn = controlOn;
    }

    /**
     * Gets controlOn attribute that indicates whether or not the handle is able to move.
     * @return Boolean that indicates whether or not the handle is able to move.
     */
    public boolean getControlOn() {
        return controlOn;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
