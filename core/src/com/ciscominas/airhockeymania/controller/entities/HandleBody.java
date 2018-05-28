package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.view.GameView;

/**
 * Sub-class of EntityBody, represents a handle body.
 */
public class HandleBody extends EntityBody {

    /**
     * Handle velocity.
     */
    private Vector2 vel;
    /**
     * Handle's last position.
     */
    private Vector2 lastPos;
    /**
     * Handle's current position.
     */
    private Vector2 currentPos;
    /**
     * Boolean that indicates whether or not the handle is able to move.
     */
    private boolean controlOn;

    /**
     * HandleBody constructor.Calls super class constructor with the respective parameters.
     * @param world World
     * @param model Bot's model
     * @param type Indicates if a body is dynamic, static or kinematic.
     */
    public HandleBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 200000f, friction = 1f, restitution = 0.5f;

        this.currentPos = new Vector2(model.getX(), model.getY());
        this.vel = new  Vector2(0,0);
        controlOn = true;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, HANDLE_BODY,
                (short) (LINE_BODY |PUCK_BODY));
    }

    /**
     *  Moves handle body according to the x and y coordinates.
     * @param x Handle new x coordinate.
     * @param y Handle new y coordinate.
     */
    public void move(float x, float y)
    {
        if(controlOn)
        {
            this.lastPos = this.currentPos;
            setTransform(x, y,0);
            this.currentPos = new Vector2(x,y);
            this.vel = new Vector2((this.currentPos.x - this.lastPos.x) *20, (this.currentPos.y - this.lastPos.y)*20);
        }
    }

    /**
     * Gets the handle velocity.
     * @return Handle's velocity.
     */
    public Vector2 getVel() {
        return vel;
    }

    /**
     * Resets handle by teleporting it back to it's initial position and setting it's body linear velocity to 0.
     * @param handleX Handle initial x-coordinate.
     * @param handleY Handle initial y-coordinate.
     */
    public void reset(float handleX, float handleY) {
        body.setTransform(handleX, handleY, 0);
        body.setLinearVelocity(0,0);
    }

    /**
     * Updates hanlde's ability to move.
     * @param controlOn Indicates whether or not the handle is able to move.
     */
    public void setControlOn(boolean controlOn) {
        this.controlOn = controlOn;
    }
}
