package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.ciscominas.airhockeymania.controller.GameController;

/**
 * Represents a powerUpType that freezes a player's handle
 */
public class FreezeHandle implements PowerUpType {

    /**
     * String referring to who was the last to touch the puck. This entity is referred as the powerUp owner.
     */
    private String lastTouch;

    /**
     * This powerUp has the purpose of harming the player that wasn't the last to touch the puck.
     * According to the lastTouch attribute this function will disable bot's movement or player's movement.
     */
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

    /**
     * Enables bot or player movement according to lastTouch  attribute.
     */
    @Override
    public void reset() {
        if(lastTouch == "PLAYER")
            GameController.getInstance().getBot().setControlOn(true);
        else
            GameController.getInstance().getHandle().setControlOn(true);
    }

    /**
     * Checks whether or not the PowerUp should be deactivated.
     * @return Return true if it should be deactivated, false otherwise.
     */
    @Override
    public boolean check() {
        return false;
    }
}
