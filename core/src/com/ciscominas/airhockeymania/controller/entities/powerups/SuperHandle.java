package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.Functions;

/**
 * Represents a powerUpType that increases a player's handle size.
 */
public class SuperHandle implements PowerUpType {

    /**
     * String referring to who was the last to touch the puck. This entity is referred as the powerUp owner.
     */
    private String lastTouch;

    /**
     * This powerUp has the purpose of helping the player that was the last to touch the puck.
     * According to the lastTouch attribute this function will increase the bot's  handle size or the player's handle size.
     */
    @Override
    public void effect() {
        lastTouch = GameController.getInstance().getLastTouch();

        if(lastTouch == "PLAYER")
        {
            Functions.destroyBody(GameController.getInstance().getHandle().getBody());
            GameModel.getInstance().setHandle(2f);
            GameController.getInstance().setHandleBody(new HandleBody(GameController.getInstance().getWorld(), GameModel.getInstance().getHandle(), BodyDef.BodyType.DynamicBody));
        }
        else
        {
            Functions.destroyBody(GameController.getInstance().getBot().getBody());
            GameModel.getInstance().setHandle(2f);
            GameController.getInstance().setBotBody(new BotBody(GameController.getInstance().getWorld(), GameModel.getInstance().getBot(), BodyDef.BodyType.DynamicBody));
        }
    }

    /**
     * Resizes bot or player handle back to it's default size according to the lastTouch attribute.
     */
    @Override
    public void reset() {
        if(lastTouch == "PLAYER")
        {
            Functions.destroyBody(GameController.getInstance().getHandle().getBody());
            GameModel.getInstance().setHandle(0.5f);
            GameController.getInstance().setHandleBody(new HandleBody(GameController.getInstance().getWorld(), GameModel.getInstance().getHandle(), BodyDef.BodyType.DynamicBody));        }
        else
        {
            Functions.destroyBody(GameController.getInstance().getBot().getBody());
            GameModel.getInstance().setHandle(0.5f);
            GameController.getInstance().setBotBody(new BotBody(GameController.getInstance().getWorld(), GameModel.getInstance().getBot(), BodyDef.BodyType.DynamicBody));
        }

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
