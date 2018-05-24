package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.BodyUtils;

public class DuplicatePucks implements PowerUpType {

    private PuckBody puck;

    @Override
    public void effect() {
        GameModel.getInstance().duplicatePuck();
        puck = new PuckBody(GameController.getInstance().getWorld(), GameModel.getInstance().getDuplicate(), BodyDef.BodyType.DynamicBody);
        puck.setTransform(GameController.getInstance().getPuckBody().getX(),GameController.getInstance().getPuckBody().getY(),0);
        puck.setLinearVelocity(-GameController.getInstance().getPuckBody().getLinearVelocity().x,GameController.getInstance().getPuckBody().getLinearVelocity().y);
    }

    @Override
    public void reset() {
        GameController.getInstance().getWorld().destroyBody(puck.getBody());
        puck.setUserData(null);
        puck.deleteBody();
        GameController.getInstance().setBegin();
    }

    public boolean checkScore() {
        if(puck != null && puck.getBody() != null)
        {
            if(puck.getBody().getPosition().y < 0)
            {
                GameController.getInstance().incScorePlayer();
                GameController.getInstance().resetBodies();
                reset();
                return true;
            } else if (puck.getBody().getPosition().y > GameController.ARENA_HEIGHT) {
                GameController.getInstance().incScoreOpponent();
                GameController.getInstance().resetBodies();
                reset();
                return true;
            }
        }

        return false;

    }

    public boolean check() {

         return checkScore();
    }
}
