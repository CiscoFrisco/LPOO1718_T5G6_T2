package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

public abstract class EntityBody {

    final static short PUCK_BODY = 0x0001;
    final static short HANDLE_BODY = 0x0002;
    final static short LINE_BODY = 0x0004;
    final static short POWERUP_BODY = 0x0008;

    final static int POLYGON = 0;
    final static int CIRCLE = 1;

    final Body body;

    public EntityBody(World world, EntityModel model) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());

        body = world.createBody(bodyDef);
        body.setUserData(model);
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
                circle.setRadius(Constants.PUCK_RADIUS);
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
}
