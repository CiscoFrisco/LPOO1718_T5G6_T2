package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

public class BotBody extends EntityBody {
    public BotBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 200000f, friction = 1f, restitution = 0.5f;
        float radius = 0.5f;

        createFixture(body, createShape(radius, CIRCLE, 0), density, friction, restitution, HANDLE_BODY,
                (short) (LINE_BODY |PUCK_BODY));
    }
}
