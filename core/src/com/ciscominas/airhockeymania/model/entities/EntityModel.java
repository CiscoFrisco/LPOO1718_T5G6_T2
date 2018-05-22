package com.ciscominas.airhockeymania.model.entities;

public abstract class EntityModel {

    public enum ModelType {PUCK, HANDLE, LINE, BOT, POWERUP}

    ;

    /**
     * The x-coordinate of this model in meters.
     */
    private float x;

    /**
     * The y-coordinate of this model in meters.
     */
    private float y;


    public EntityModel(float x, float y) {
        this.x = x;
        this.y = y;
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
