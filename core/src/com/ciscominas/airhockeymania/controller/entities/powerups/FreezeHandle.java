package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

import static com.ciscominas.airhockeymania.model.entities.EntityModel.ModelType.HANDLE;

/**
 * Represents a powerUpType that freezes a player's handle
 */
public class FreezeHandle implements PowerUpType {

    /**
     * String referring to who was the last to touch the puck. This entity is referred as the powerUp owner.
     */
    private EntityModel.ModelType lastTouch;

    /**
     * This powerUp has the purpose of harming the player that wasn't the last to touch the puck.
     * According to the lastTouch attribute this function will disable bot's movement or player's movement.
     */
    @Override
    public void effect() {
        lastTouch = GameModel.getInstance().getLastTouch();
        GameController controller = GameController.getInstance();
        if (lastTouch == HANDLE) {
            controller.getBot().setControlOn(false);
            controller.getBot().setLinearVelocity(0, 0);
        } else {
            controller.getHandle().setControlOn(false);
            controller.getHandle().setLinearVelocity(0, 0);
        }
    }

    /**
     * Enables bot or player movement according to lastTouch  attribute.
     */
    @Override
    public void reset() {
        if (lastTouch == HANDLE)
            GameController.getInstance().getBot().setControlOn(true);
        else
            GameController.getInstance().getHandle().setControlOn(true);
    }
}
