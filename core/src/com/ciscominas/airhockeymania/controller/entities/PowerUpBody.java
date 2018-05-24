package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.controller.entities.powerups.PowerUpType;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.utils.WorldUtils;

public class PowerUpBody extends EntityBody {

    private PowerUpType type;
    boolean active;

    public PowerUpBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 1f, friction = 0.4f, restitution = 0.5f;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, POWERUP_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));

        setType(WorldUtils.randPowerUp());
    }


    public void setType(PowerUpType type)
    {
        this.type = type;
    }

    public void effect()
    {
        type.effect();
        active = true;
    }

    public void reset()
    {
        type.reset();
        active = false;
    }

    public boolean check()
    {
        if(!active && body != null)
        {
            checkContact();
            return false;
        }

        return type.check();
    }

    public void checkContact() {
        if (BodyUtils.checkIntersection(body, GameController.getInstance().getPuckBody().getBody(),0.5))
        {
            effect();
            WorldUtils.destroyBody(body);
            body = null;
        }

    }


    public boolean isActive() {
        return active;
    }

    public PowerUpType getType() {
        return type;
    }
}
