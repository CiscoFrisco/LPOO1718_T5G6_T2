package com.ciscominas.airhockeymania.model.entities;


public class PowerUpModel extends EntityModel {

    private boolean active;

    public PowerUpModel(float x, float y) {
        super(x, y);
        active = false;
    }

    @Override
    public ModelType getType() {
        return ModelType.POWERUP;
    }
}
