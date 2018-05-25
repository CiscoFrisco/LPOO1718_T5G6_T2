package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.ciscominas.airhockeymania.controller.GameController;

public class FreezeHandle implements PowerUpType {

    private String lastTouch;

    @Override
    public void effect() {
        lastTouch = GameController.getInstance().getLastTouch();

        if(lastTouch == "PLAYER") {
            GameController.getInstance().getBot().setControlOn(false);
            GameController.getInstance().getBot().setLinearVelocity(0,0);
        }
        else
        {
            GameController.getInstance().getHandle().setControlOn(false);
            GameController.getInstance().getHandle().setLinearVelocity(0, 0);
        }
    }

    @Override
    public void reset() {
        if(lastTouch == "PLAYER")
            GameController.getInstance().getBot().setControlOn(true);
        else
            GameController.getInstance().getHandle().setControlOn(true);
    }

    @Override
    public boolean check() {
        return false;
    }
}
