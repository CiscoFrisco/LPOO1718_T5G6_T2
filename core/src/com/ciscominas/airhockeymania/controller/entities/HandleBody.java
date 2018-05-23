package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.utils.Constants;

public class HandleBody extends EntityBody {

    private Vector2 vel;
    private Vector2 lastPos;
    private Vector2 currentPos;

    public HandleBody(World world, EntityModel model, BodyDef.BodyType type) {
        super(world, model, type);

        float density = 200000f, friction = 1f, restitution = 0.5f;
        float radius = 0.5f;

        this.currentPos = new Vector2(model.getX(), model.getY());
        this.vel = new  Vector2(0,0);

        createFixture(body, createShape(radius, CIRCLE, 0), density, friction, restitution, HANDLE_BODY,
                (short) (LINE_BODY |PUCK_BODY));
    }

    public void move(float x, float y)
    {
        this.lastPos = this.currentPos;
        setTransform(x, y,0);
        this.currentPos = new Vector2(x,y);
        this.vel = new Vector2((this.currentPos.x - this.lastPos.x) *10, (this.currentPos.y - this.lastPos.y)*10);
    }

    public Vector2 getVel() {
        return vel;
    }
}
