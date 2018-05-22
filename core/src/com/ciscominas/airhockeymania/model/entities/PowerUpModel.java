package com.ciscominas.airhockeymania.model.entities;

import com.ciscominas.airhockeymania.model.powerups.PowerUpType;

public class PowerUpModel extends EntityModel {

    private boolean active;
    private PowerUpType type;

    public PowerUpModel(float x, float y) {
        super(x, y);
        active = false;
    }

    @Override
    public ModelType getType() {
        return ModelType.POWERUP;
    }

    public void setType(PowerUpType type)
    {
        this.type = type;
    }

    public void effect()
    {
        type.effect();
    }

    public void reset()
    {
        type.reset();
    }

    public void check()
    {
        type.check();
    }
}
