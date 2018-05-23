package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

public class PuckBody extends EntityBody {
    public PuckBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 100f, friction = 0f, restitution = 0.5f;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, PUCK_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));
    }

    public void setUserData(Object userData) {
        this.body.setUserData(userData);
    }

    public Body getBody() {
        return body;
    }

    public void reset() {
        body.setTransform(Constants.PUCK_X, Constants.PUCK_Y, 0);
        body.setLinearVelocity(0,0);
    }
}
