package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

public class BotBody extends EntityBody {

    boolean controlOn;

    public BotBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 200000f, friction = 1f, restitution = 0.5f;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, HANDLE_BODY,
                (short) (LINE_BODY |PUCK_BODY));
    }

    public void reset(float botX, float botY) {
        body.setTransform(botX, botY, 0);
        body.setLinearVelocity(0,0);
    }

    public void setControlOn(boolean control)
    {
        this.controlOn = control;
    }
}
