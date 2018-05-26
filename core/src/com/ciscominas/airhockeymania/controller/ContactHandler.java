package com.ciscominas.airhockeymania.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;

import static com.ciscominas.airhockeymania.utils.Constants.RIGHT;
import static com.ciscominas.airhockeymania.utils.Constants.LEFT;

public class ContactHandler implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Body b1 = contact.getFixtureA().getBody();
        Body b2 = contact.getFixtureB().getBody();

        GameController controller = GameController.getInstance();
        Object b1UserData = b1.getUserData();
        Object b2UserData = b2.getUserData();

        if(b1UserData instanceof PuckModel && b2UserData instanceof HandleModel)
        {
            b1.setLinearVelocity(controller.getHandle().getVel().add(b1.getLinearVelocity()));
            controller.setLastTouch("PLAYER");

            if(controller.isSoundEnabled())
                controller.getHit().play(controller.getVolume());

            ((BotModel) controller.getBot().getUserData()).setDefended(false);
            ((BotModel) controller.getBot().getUserData()).setTrajectoryFlag(false);

            ((PuckModel) b1UserData).resetWallBounce();
        }
        else if (b2UserData instanceof PuckModel && b1UserData instanceof HandleModel)
        {
            b2.setLinearVelocity(controller.getHandle().getVel().add(b2.getLinearVelocity()));
            controller.setLastTouch("PLAYER");

            if(controller.isSoundEnabled())
                controller.getHit().play(controller.getVolume());

            ((BotModel) controller.getBot().getUserData()).setDefended(false);
            ((BotModel) controller.getBot().getUserData()).setTrajectoryFlag(false);

            ((PuckModel) b2UserData).resetWallBounce();

        }
        else if(b1UserData instanceof PuckModel && b2UserData instanceof BotModel)
        {
            controller.setLastTouch("BOT");

            if(controller.isSoundEnabled())
                controller.getHit().play(controller.getVolume());

            ((BotModel) controller.getBot().getUserData()).setDefended(true);
            ((PuckModel) b1UserData).resetWallBounce();
        }
        else if (b2UserData instanceof PuckModel && b1UserData instanceof BotModel) {
            controller.setLastTouch("BOT");

            if(controller.isSoundEnabled())
                controller.getHit().play(controller.getVolume());

            ((BotModel) controller.getBot().getUserData()).setDefended(true);
            ((PuckModel) b2UserData).resetWallBounce();

        }
        else if(b1UserData instanceof PuckModel && b2UserData instanceof LineModel &&
                (((LineModel) b2UserData).getPos()==RIGHT || ((LineModel) b2UserData).getPos()==LEFT))
        {
            ((PuckModel) b1UserData).incWallBounce();
        }
        else if(b2UserData instanceof PuckModel && b1UserData instanceof LineModel &&
                (((LineModel) b1UserData).getPos()==RIGHT) || ((LineModel) b1UserData).getPos()==LEFT)
        {
            ((PuckModel) b2UserData).incWallBounce();
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
