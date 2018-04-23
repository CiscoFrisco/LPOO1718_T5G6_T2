package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldUtils {

    public static World createWorld()
    {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createEdge(World world, float x, float y)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.EDGE_WIDTH/2, Constants.EDGE_HEIGHT/2);
        body.createFixture(shape, Constants.EDGE_DENSITY);

        shape.dispose();

        return body;
    }

    public static Body createGoalLine(World world, float x, float y, float width, float height)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2,height/2 );
        body.createFixture(shape, Constants.EDGE_DENSITY);
        shape.dispose();

        return body;
    }

    public static Body createPuck(World world)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.PUCK_X, Constants.PUCK_Y));
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.PUCK_RADIUS);
        Body body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        fixture.density = Constants.PUCK_DENSITY;
        fixture.shape = shape;
        fixture.restitution = 0.5f;
        body.createFixture(fixture);

        body.resetMassData();
        shape.dispose();
        return body;
    }

    public static Body createHandle(World world)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.HANDLE_X, Constants.HANDLE_Y));
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.HANDLE_RADIUS);
        Body body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        fixture.density = Constants.PUCK_DENSITY;
        fixture.shape = shape;
        fixture.restitution = Constants.RESTITUTION;
        body.createFixture(fixture);
        body.resetMassData();
        shape.dispose();
        return body;
    }
}
