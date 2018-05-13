package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.box2d.PowerUpUserData;

import static com.ciscominas.airhockeymania.utils.Constants.PUCK_DENSITY;

public class WorldUtils {

    public static World createWorld()
    {
        return new World(Constants.WORLD_GRAVITY, false);
    }

    public static Body createLine(World world, float x, float y, float width, float height, short category, short mask)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2,height/2 );
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.density = Constants.EDGE_DENSITY;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        body.createFixture(fixture);
        shape.dispose();

        return body;
    }

    public static Body createPuck(World world, short category, short mask)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.bullet=true;
        bodyDef.position.set(new Vector2(Constants.PUCK_X, Constants.PUCK_Y));
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.PUCK_RADIUS);
        Body body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        fixture.density = PUCK_DENSITY;
        fixture.shape = shape;
        fixture.restitution = 0.5f;
        fixture.friction = 0f;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        body.createFixture(fixture);

        body.resetMassData();
        shape.dispose();
        return body;
    }

    public static Body createHandle(Vector2 position, World world, short category, short mask)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.HANDLE_RADIUS);
        Body body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        fixture.density = 200000f;
        fixture.shape = shape;
        fixture.restitution = 0.5f;
        fixture.friction = 1f;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        body.createFixture(fixture);
        body.resetMassData();
        shape.dispose();
        return body;
    }

    public static Body createPowerUp(Vector2 position, World world, short category, short mask)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.HANDLE_RADIUS);
        Body body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        fixture.density = 200000f;
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        body.createFixture(fixture);
        body.resetMassData();
        shape.dispose();

        body.setUserData(new PowerUpUserData());

        return body;
    }
}
