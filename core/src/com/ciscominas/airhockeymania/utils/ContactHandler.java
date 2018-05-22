package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ciscominas.airhockeymania.box2d.PuckUserData;
import com.ciscominas.airhockeymania.stages.GameStage;

public class ContactHandler implements ContactListener {

    private GameStage stage;

    public ContactHandler(GameStage stage)
    {
        this.stage = stage;
    }

    @Override
    public void beginContact(Contact contact) {

        Body b1 = contact.getFixtureA().getBody();
        Body b2 = contact.getFixtureB().getBody();

        if(BodyUtils.bodyIsPuck(b1)&& b2.equals(stage.getHandle().getBody()))
        {
            b1.setLinearVelocity(stage.getHandle().getVel().add(b1.getLinearVelocity()));
            stage.setLastTouch("PLAYER");
            stage.getBot().setDefended(false);
            stage.getBot().setTrajectoryFlag(false);

            ((PuckUserData) b1.getUserData()).resetWallBounce();
        }
        else if (BodyUtils.bodyIsPuck(b2) && b1.equals(stage.getHandle().getBody()))
        {
            b2.setLinearVelocity(stage.getHandle().getVel().add(b2.getLinearVelocity()));
            stage.setLastTouch("PLAYER");
            stage.getBot().setDefended(false);
            stage.getBot().setTrajectoryFlag(false);

            ((PuckUserData) b2.getUserData()).resetWallBounce();

        }
        else if(BodyUtils.bodyIsPuck(b1)&& b2.equals(stage.getBot().getBody()))
        {
            stage.setLastTouch("BOT");
            stage.getBot().setDefended(true);
            ((PuckUserData) b1.getUserData()).resetWallBounce();
        }
        else if (BodyUtils.bodyIsPuck(b2) && b1.equals(stage.getBot().getBody())) {
            stage.setLastTouch("BOT");
            stage.getBot().setDefended(true);
            ((PuckUserData) b2.getUserData()).resetWallBounce();

        }
        else if(BodyUtils.bodyIsPuck(b1) && (b2.equals(stage.getEdge("Left").getBody()) || b2.equals(stage.getEdge("Right").getBody())))
        {
            ((PuckUserData) b1.getUserData()).incWallBounce();
        }
        else if(BodyUtils.bodyIsPuck(b2) && (b1.equals(stage.getEdge("Left").getBody()) || b1.equals(stage.getEdge("Right").getBody())))
        {
            ((PuckUserData) b2.getUserData()).incWallBounce();
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
