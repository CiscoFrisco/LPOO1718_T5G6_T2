package com.ciscominas.airhockeymania.model.entities;

/**
 * Holds info about a Puck.
 */
public class PuckModel extends EntityModel {

    /**
     * Creates a PuckModel object.
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param width the width in meters
     * @param height the height in meters
     */
    public PuckModel(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Gets this model's type.
     * @return this model's type.
     */
    @Override
    public ModelType getType() {
        return ModelType.PUCK;
    }
}
