package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

/**
 * Represents a body.
 */
public abstract class EntityBody {
    /**
     * Puck identifier for collision handling.
     */
    public final static short PUCK_BODY = 0x0001;
    /**
     * Handle identifier for collision handling.
     */
    public final static short HANDLE_BODY = 0x0002;
    /**
     * Line identifier for collision handling.
     */
    public final static short LINE_BODY = 0x0004;
    /**
     * PowerUp identifier for collision handling.
     */
    public final static short POWERUP_BODY = 0x0008;

    /**
     * Polygon identifier defining entity shape.
     */
    final static int POLYGON = 0;
    /**
     * Circle identifier defining entity shape.
     */
    final static int CIRCLE = 1;

    /**
     * Entity's body.
     */
    protected Body body;

    /**
     * EntityBody's constructor.
     * Creates a new body definition with the correct type and position.
     * Creates a body and attaches it to the world.
     * @param world World
     * @param model Entity's model
     * @param type Indicates if a body is dynamic, static or kinematic.
     */
    public EntityBody(World world, EntityModel model, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(model.getX(), model.getY());

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    /**
     * Returns entity body.
     * @return Body Entity's body
     */
    public Body getBody()
    {
        return body;
    }

    /**
     * Wraps the getX method from the Box2D body class.
     *
     * @return the x-coordinate of this body.
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Wraps the getY method from the Box2D body class.
     *
     * @return the y-coordinate of this body.
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     *  Set the position of the body's origin and rotation.
     *  This breaks any contacts and wakes the other bodies.
     *  Manipulating a body's transform may cause non-physical behavior.
     * @param x World's position on the x-axis
     * @param y World's position on the y-axis
     * @param angle World's rotation in radians
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /**
     *Set the linear velocity of the center of mass.
     * @param vx Velocity value on the x-axis.
     * @param vy Velocity values on the y-axis.
     */
    public void setLinearVelocity(float vx, float vy)
    {
        body.setLinearVelocity(vx,vy);
    }

    /**
     * Get the linear velocity of the center of mass.
     * Note that the same Vector2 instance is returned each time this method is called.
     * @return Vector representing the linear velocity.
     */
    public Vector2 getLinearVelocity()
    {
        return body.getLinearVelocity();
    }

    /**
     * Get the user data.
     * @return User data.
     */
    public Object getUserData() {
        return body.getUserData();
    }


     /**
      * Creates a shape according to the argument type (CIRCLE or POLYGON) with a respective width and height.
      * @param width
      * @param type
      * @param height
      * @return Shape
     */

    Shape createShape(float width, int type, float height)
    {
        switch(type)
        {
            case POLYGON:
                PolygonShape polygon = new PolygonShape();
                polygon.setAsBox(width/2,height/2 );
                return polygon;
            case CIRCLE:
                CircleShape circle = new CircleShape();
                circle.setRadius(width);
                return circle;
        }

        return null;
    }

    /**
     *
     * Creates a fixture from a shape and attaches it to this body. This is a convenience function.
     * If the density is non-zero, this function automatically updates the mass of the body.
     * @param body Body
     * @param shape Shape
     * @param density FixtureDef's density
     * @param friction FixtureDef's friction
     * @param restitution FixtureDef's restitution
     * @param category FixtureDef's category
     * @param mask FixtureDef's mask
     */

    final void createFixture(Body body, Shape shape, float density, float friction, float restitution, short category, short mask) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    /**
     * Sets body as null.
     */
    public void deleteBody() {
        body = null;
    }
}
