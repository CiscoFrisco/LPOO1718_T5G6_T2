package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.BodyUtils;

public class DuplicatePucks implements PowerUpType {

    private PuckBody puck;
    private boolean active;

    @Override
    public void effect() {
        puck = new PuckBody(GameController.getInstance().getWorld(), GameModel.getInstance().getPuck(), BodyDef.BodyType.DynamicBody);
        puck.setTransform(GameController.getInstance().getPuckBody().getX(),GameController.getInstance().getPuckBody().getY(),0);
        puck.setLinearVelocity(-GameController.getInstance().getPuckBody().getLinearVelocity().x,GameController.getInstance().getPuckBody().getLinearVelocity().y);
        active = true;
    }

    @Override
    public void reset() {
        GameController.getInstance().getWorld().destroyBody(puck.getBody());
        puck.setUserData(null);
        puck.deleteBody();
        active = false;
        GameController.getInstance().setBegin();
    }

    public boolean checkScore() {
        if(puck != null && puck.getBody() != null)
        {
            if(puck.getBody().getPosition().y < 0)
            {
                GameController.getInstance().scorePlayer++;
                game.resetActors();
                reset(game);
                return true;
            } else if (puck.getBody().getPosition().y > 16) {
                game.scoreOpponent++;
                game.resetActors();
                reset(game);
                return true;
            }
        }

        return false;

    }

    public void check() {

        if(checkScore())
            return true;

        return false;
    }
    }

}
