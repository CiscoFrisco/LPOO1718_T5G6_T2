package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.controller.entities.powerups.PowerUpType;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.utils.WorldUtils;

/**
 * Sub-class of EntityBody, represents a powerUp body.
 */
public class PowerUpBody extends EntityBody {
    /**
     * The powerUp type (DuplicatePuck, FreezeHandle, SuperGoal or SuperHandle).
     */
    private PowerUpType type;
    /**
     * Boolean that indicates whether or not the powerUp is currently active.
     */
    boolean active;

    /**
     * PowerUpBody constructor. Calls super class constructor with the respective parameters.
     * Generates a random powerUp type.
     * @param world World
     * @param model Bot's model
     * @param type Indicates if a body is dynamic, static or kinematic.
     */
    public PowerUpBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 1f, friction = 0.4f, restitution = 0.5f;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, POWERUP_BODY,
                (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY));

        setType(WorldUtils.randPowerUp());
    }

    /**
     *  Set powerUpType as parameter type.
     * @param type New powerUp type.
     */
    public void setType(PowerUpType type)
    {
        this.type = type;
    }

    /**
     * Activates current PowerUp.
     */
    public void effect()
    {
        type.effect();
        active = true;
    }

    /**
     *  Resets the current powerUp by nulling its effects.
     */
    public void reset()
    {
        type.reset();
        active = false;
    }

    /**
     * Returns whether or not the powerUp was deactivated.
     * @return
     */
    public boolean check()
    {
        if(!active && body != null)
        {
            checkContact();
            return false;
        }

        return type.check();
    }

    /**
     * Checks if the powerUp body collides with the puck.
     * If it does then the currentPowerUp is activated and its body is destroyed.
     */
    public void checkContact() {
        if (BodyUtils.checkIntersection(body, GameController.getInstance().getPuckBody().getBody(),0.5))
        {
            effect();
            WorldUtils.destroyBody(body);
            body = null;
        }

    }

    /**
     * Returns boolean that indicates whether or not the powerUp is active.
     * @return Boolean that indicates whether or not the powerUp is active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns powerUp type.
     * @return PowerUpType.
     */
    public PowerUpType getType() {
        return type;
    }
}
