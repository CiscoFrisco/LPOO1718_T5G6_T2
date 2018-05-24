package com.ciscominas.airhockeymania.model.entities;

public abstract class EntityModel {

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setX(float x) {
        this.x = x;
    }

    public enum ModelType {PUCK, HANDLE, LINE, BOT, POWERUP}

    /**
     * The x-coordinate of this model in meters.
     */
    protected float x;

    /**
     * The y-coordinate of this model in meters.
     */
    protected float y;

    protected float width;

    protected float height;


    public EntityModel(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract ModelType getType();
}
