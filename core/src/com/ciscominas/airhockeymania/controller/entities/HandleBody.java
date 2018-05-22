package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

public class HandleBody extends EntityBody {
    public HandleBody(World world, EntityModel model) {
        super(world, model);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 75, height = 75;

        createFixture(body, createShape(Constants.HANDLE_RADIUS, CIRCLE, 0), density, friction, restitution, PUCK_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));
    }
}
