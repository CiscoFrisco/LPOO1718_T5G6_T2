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

    public int getScore() {
        return score;
    }

    public void incScore()
    {
        score++;
    }

    public void resetScore()
    {
        score = 0;
    }
}
