package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

public class PowerUpBody extends EntityBody {
    public PowerUpBody(World world, EntityModel model) {
        super(world, model);

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int width = 75, height = 75;

        createFixture(body, createShape(Constants.PUCK_RADIUS, CIRCLE, 0), density, friction, restitution, POWERUP_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));
    }
}
