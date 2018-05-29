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

/**
 * Controls the collision of the World
 */
public class ContactHandler implements ContactListener {
    /**
     * Checks the contact between different world elements and acts according to the entities that collided.
     * When a puck colides with a handle, lastTouch is updated for powerUp purposes.
     * Also if bot collides with the puck, the bot's behaviour state attribute is updated (RESET).
     * @param contact COntact established between world elements.
     */
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
            }
        else if (b2UserData instanceof PuckModel && b1UserData instanceof HandleModel)
        {
            b2.setLinearVelocity(controller.getHandle().getVel().add(b2.getLinearVelocity()));
            controller.setLastTouch("PLAYER");

            if(controller.isSoundEnabled())
                controller.getHit().play(controller.getVolume());

        }
        else if(b1UserData instanceof PuckModel && b2UserData instanceof BotModel)
        {
            controller.setLastTouch("BOT");
            GameController.getInstance().getBot().getBehaviour().changeState("RESET");
            if(controller.isSoundEnabled())
                controller.getHit().play(controller.getVolume()); }
        else if (b2UserData instanceof PuckModel && b1UserData instanceof BotModel) {
            controller.setLastTouch("BOT");
            GameController.getInstance().getBot().getBehaviour().changeState("RESET");
            if(controller.isSoundEnabled())
                controller.getHit().play(controller.getVolume());
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
