package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;

import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Functions;

/**
 * Represents a powerUpType that increases a player's handle size.
 */
public class SuperHandle implements PowerUpType {

    /**
     * String referring to who was the last to touch the puck. This entity is referred as the powerUp owner.
     */
    private EntityModel.ModelType lastTouch;

    /**
     * Resize ratio applied once the powerUp is activated.
     */
    private static final float EFFECT_RATIO = 2f;

    /**
     * Resize ratio applied once the powerUp is deactivated.
     */
    private static final float RESET_RATIO = 0.5f;

    /**
     * This powerUp has the purpose of helping the player that was the last to touch the puck.
     * According to the lastTouch attribute this function will increase the bot's  handle size or the player's handle size.
     */
    @Override
    public void effect() {
        lastTouch = GameModel.getInstance().getLastTouch();

        effectHandle(EFFECT_RATIO);
    }

    /**
     * Resizes bot or player handle back to it's default size according to the lastTouch attribute.
     */
    @Override
    public void reset() {
        effectHandle(RESET_RATIO);
    }

    /**
     * Changes the width of the bot/handle according to last touch variable, also changing the corresponding
     * body on GameController.
     * @param ratio the ratio by which the bot/handle's width will change
     */
    private void effectHandle(float ratio)
    {
        GameController controller = GameController.getInstance();
        GameModel model = GameModel.getInstance();

        if(lastTouch == EntityModel.ModelType.HANDLE)
        {
            Functions.destroyBody(controller.getHandle().getBody());
            model.setHandleWidth(ratio);
            controller.setHandleBody(new HandleBody(controller.getWorld(), model.getHandle(), BodyDef.BodyType.DynamicBody));
        }
        else
        {
            Functions.destroyBody(controller.getBot().getBody());
            model.setBotWidth(ratio);
            controller.setBotBody(new BotBody(controller.getWorld(), model.getBot(), BodyDef.BodyType.DynamicBody));
        }
    }
}
