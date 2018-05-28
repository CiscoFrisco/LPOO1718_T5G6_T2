package com.ciscominas.airhockeymania.model.entities;

/**
 * Holds info about an Edge.
 */
public class LineModel extends EntityModel {

    /**
     * Represents the relative position of this edge on the screen.
     */
    private static int pos;

    /**
     * Creates a LineModel object.
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     * @param width the width in meters
     * @param height the height in meters
     */
    public LineModel(float x, float y, float width, float height,  int pos) {
        super(x, y, width, height);
        this.pos = pos;
    }

    /**
     * Gets this model's type.
     * @return this model's type.
     */
    @Override
    public ModelType getType() {
        return ModelType.LINE;
    }

    /**
     * Gets an id for position of this edge on the screen.
     * @return an id for position of this edge on the screen.
     */
    public int getPos() {
        return pos;
    }
}
