package com.ciscominas.airhockeymania.model.entities;

public abstract class EntityModel {

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public enum ModelType {PUCK, HANDLE, LINE, BOT, POWERUP}

    /**
     * The x-coordinate of this model in meters.
     */
    private float x;

    /**
     * The y-coordinate of this model in meters.
     */
    private float y;

    private float width;

    private float height;


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
