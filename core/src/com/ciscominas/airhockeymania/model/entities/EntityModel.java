package com.ciscominas.airhockeymania.model.entities;

public abstract class EntityModel {

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public enum ModelType {PUCK, HANDLE, LINE, POWERUP};

    /**
     * The x-coordinate of this model in meters.
     */
    private float x;

    /**
     * The y-coordinate of this model in meters.
     */
    private float y;

    EntityModel(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract ModelType getType();



}
