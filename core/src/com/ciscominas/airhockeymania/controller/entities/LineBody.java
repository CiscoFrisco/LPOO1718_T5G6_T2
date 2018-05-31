package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import com.ciscominas.airhockeymania.model.entities.EntityModel;

/**
 * Sub-class of EntityBody, represents a line body.
 */
public class LineBody extends EntityBody {
    /**
     * LineBody constructor.Calls super class constructor with the respective parameters.
     * @param world World
     * @param model Bot's model
     * @param type Indicates if a body is dynamic, static or kinematic.
     * @param mask Indicates the entities that can collide with a LineBody.
     */
    public LineBody(World world, EntityModel model,BodyDef.BodyType type, short mask) {
        super(world, model, type);

        float density = 0f, friction = 0f, restitution = 0f;

        createFixture(body, createShape(model.getWidth(), POLYGON, model.getHeight()), density, friction, restitution, LINE_BODY,
                mask);
    }
}
