package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

public class LineBody extends EntityBody {

    public LineBody(World world, EntityModel model,BodyDef.BodyType type, float width, float height, short mask) {
        super(world, model, type);

        float density = 0f, friction = 0f, restitution = 0f;

        createFixture(body, createShape(width, POLYGON, height), density, friction, restitution, LINE_BODY,
                mask);
    }
}
