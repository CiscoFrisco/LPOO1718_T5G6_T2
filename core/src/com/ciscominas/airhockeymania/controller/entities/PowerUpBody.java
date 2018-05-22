package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

public class PowerUpBody extends EntityBody {
    public PowerUpBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        float radius = 0.5f;

        createFixture(body, createShape(radius, CIRCLE, 0), density, friction, restitution, POWERUP_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));
    }
}
