package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;

public class DuplicatePucks implements PowerUpType {

    private PuckBody puck;

    @Override
    public void effect() {
        GameModel.getInstance().duplicatePuck();
        puck = new PuckBody(GameController.getInstance().getWorld(), GameModel.getInstance().getDuplicate(), BodyDef.BodyType.DynamicBody);
        puck.setTransform(GameController.getInstance().getPuckBodies().get(0).getX(),GameController.getInstance().getPuckBodies().get(0).getY(),0);
        puck.setLinearVelocity(-GameController.getInstance().getPuckBodies().get(0).getLinearVelocity().x,GameController.getInstance().getPuckBodies().get(0).getLinearVelocity().y);
        GameController.getInstance().getPuckBodies().add(puck);
    }

    @Override
    public void reset() {
        GameController.getInstance().getWorld().destroyBody(puck.getBody());
        puck.setUserData(null);
        puck.deleteBody();
        GameController.getInstance().getPuckBodies().remove(1);
        GameController.getInstance().setBegin();
    }
}
