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

public abstract class EntityBody {

    public final static short PUCK_BODY = 0x0001;
    public final static short HANDLE_BODY = 0x0002;
    public final static short LINE_BODY = 0x0004;
    public final static short POWERUP_BODY = 0x0008;

    final static int POLYGON = 0;
    final static int CIRCLE = 1;

    protected Body body;

    public EntityBody(World world, EntityModel model, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(model.getX(), model.getY());

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

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

    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    public void setLinearVelocity(float vx, float vy)
    {
        body.setLinearVelocity(vx,vy);
    }

    public Vector2 getLinearVelocity()
    {
        return body.getLinearVelocity();
    }

    public Object getUserData() {
        return body.getUserData();
    }

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

    public void deleteBody() {
        body = null;
    }
}
