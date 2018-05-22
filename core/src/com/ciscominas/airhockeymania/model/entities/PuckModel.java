package com.ciscominas.airhockeymania.model.entities;

public class PuckModel extends EntityModel {

    public PuckModel(float x, float y) {
        super(x, y);
    }

    @Override
    public ModelType getType() {
        return null;
    }

    public void resetWallBounce() {
    }

    public void incWallBounce() {
    }
}
