package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

public class LineBody extends EntityBody {

    private float height;
    private float width;

    public LineBody(World world, EntityModel model, float width, float height) {
        super(world, model);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        this.width = width;
        this.height = height;

        createFixture(body, createShape(width, POLYGON, height), density, friction, restitution, PUCK_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));
    }
}
