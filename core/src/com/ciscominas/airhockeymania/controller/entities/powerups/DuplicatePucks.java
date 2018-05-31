package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;

import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.Functions;

public class DuplicatePucks implements PowerUpType {

    private PuckBody puck;

    @Override
    public void effect() {

        GameModel model = GameModel.getInstance();
        GameController controller = GameController.getInstance();
        PuckBody defPuck = controller.getPuckBodies().get(0);

        model.duplicatePuck();
        puck = new PuckBody(controller.getWorld(), model.getDuplicate(), BodyDef.BodyType.DynamicBody);
        puck.setTransform(defPuck.getX(), defPuck.getY(),0);
        puck.setLinearVelocity(-defPuck.getLinearVelocity().x, defPuck.getLinearVelocity().y);
        controller.getPuckBodies().add(puck);
    }

    @Override
    public void reset() {
        Functions.destroyBody(puck.getBody());
        puck.deleteBody();
        GameController.getInstance().getPuckBodies().remove(1);
    }
}
