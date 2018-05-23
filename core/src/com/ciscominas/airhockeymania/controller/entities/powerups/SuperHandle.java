package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.WorldUtils;

public class SuperHandle implements PowerUpType {

    private String lastTouch;
    private boolean active;

    @Override
    public void effect() {
        lastTouch = GameController.getInstance().getLastTouch();

        if(lastTouch == "PLAYER")
        {
            WorldUtils.destroyBody(GameController.getInstance().getHandle().getBody());
            //por model
            GameController.getInstance().setHandleBody(new HandleBody(GameController.getInstance().getWorld(), GameModel.getInstance().getHandle(), BodyDef.BodyType.DynamicBody));
        }
        else
        {
            WorldUtils.destroyBody(GameController.getInstance().getBot().getBody());
            GameController.getInstance().setBotBody(new BotBody(GameController.getInstance().getWorld(), GameModel.getInstance().getBot(), BodyDef.BodyType.DynamicBody));
        }


        active = true;
    }

    @Override
    public void reset() {
        if(lastTouch == "PLAYER")
        {
            WorldUtils.destroyBody(GameController.getInstance().getHandle().getBody());
            GameController.getInstance().setHandleBody(new HandleBody(GameController.getInstance().getWorld(), GameModel.getInstance().getHandle(), BodyDef.BodyType.DynamicBody));        }
        else
        {
            WorldUtils.destroyBody(GameController.getInstance().getBot().getBody());
            GameController.getInstance().setBotBody(new BotBody(GameController.getInstance().getWorld(), GameModel.getInstance().getBot(), BodyDef.BodyType.DynamicBody));
        }

        active = false;
    }

    @Override
    public boolean check() {
        return false;
    }
}
