package com.ciscominas.airhockeymania.model.entities;

/**
 * Holds info about an Entity.
 */
public abstract class EntityModel {

    /**
     * An identifier for this model's type.
     */
    public enum ModelType {PUCK, HANDLE, LINE, BOT, POWERUP}

    /**
     * The x-coordinate of this model in meters.
     */
    protected float x;

    /**
     * The y-coordinate of this model in meters.
     */
    protected float y;

    /**
     * The width of this model in meters.
     */
    protected float width;

    /**
     * The height of this model in meters.
     */
    protected float height;

    /**
     * Creates an EntityModel object.
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param width the width in meters
     * @param height the height in meters
     */
    public EntityModel(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Sets this model's position.
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets this model's width.
     * @param width the width in meters
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Sets the x-coordinate.
     * @param x the x-coordinate in meters
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets this model's type.
     * @return this model's type.
     */
    public abstract ModelType getType();

    /**
     * Gets this model's x-coordinate.
     * @return this model's x-coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets this model's y-coordinate.
     * @return this model's y-coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets this model's width.
     * @return this model's width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets this model's height.
     * @return this model's height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets this model's width, multiplying it by a given ratio.
     * @param width the ratio
     */
    public void multWidth(float width) {
        this.width*= width;
    }
}
