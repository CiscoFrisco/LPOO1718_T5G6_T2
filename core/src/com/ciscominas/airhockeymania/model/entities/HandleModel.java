package com.ciscominas.airhockeymania.model.entities;

/**
 * Holds info about an Handle.
 */
public class HandleModel extends EntityModel {

    /**
     * Number of goals scored in a single game
     */
    private int score;
    /**
     * Boolean that indicates whether or not the handle is able to move.
     */
    private boolean controlOn = true;

    /**
     * Creates a HandleModel object.
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param width the width in meters
     * @param height the height in meters
     */
    public HandleModel(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Gets this model's type.
     * @return this model's type.
     */
    @Override
    public ModelType getType() {
        return ModelType.HANDLE;
    }

    /**
     * Returns this handle's score in the current game.
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Increments this handle's score
     */
    public void incScore()
    {
        score++;
    }

    /**
     * Resets this handle's score.
     */
    public void resetScore()
    {
        score = 0;
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
}
