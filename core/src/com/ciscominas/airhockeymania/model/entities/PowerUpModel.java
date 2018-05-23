package com.ciscominas.airhockeymania.model.entities;


public class PowerUpModel extends EntityModel {

    private boolean active;

    public PowerUpModel(float x, float y, float width, float height) {
        super(x, y, width, height);
        active = false;
    }

    @Override
    public ModelType getType() {
        return ModelType.POWERUP;
    }
}
