package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

/**
 * Sub-class of EntityBody, represents a puck body.
 */
public class PuckBody extends EntityBody {

    /**
     * PcukBody constructor. Calls super class constructor with the respective parameters.
     * @param world World
     * @param model Bot's model
     * @param type Indicates if a body is dynamic, static or kinematic.
     */
    public PuckBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 100f, friction = 0f, restitution = 0.5f;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, PUCK_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));
    }

    /**
     * Sets puck's body userData as parameter userData
     * @param userData Puck's body new userData.
     */
    public void setUserData(Object userData) {
        this.body.setUserData(userData);
    }

    /**
     * Return puck's body.
     * @return Puck's body.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Resets puck by teleporting it back to it's initial position and setting it's body linear velocity to 0.
     */
    public void reset() {
        body.setTransform(Constants.PUCK_X, Constants.PUCK_Y, 0);
        body.setLinearVelocity(0,0);
    }
}
