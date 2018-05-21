package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.utils.Constants;

public class Handle extends GameActor {

    private Vector2 vel;
    private Vector2 lastPos;
    private Vector2 currentPos;


    public Handle(Body body)
    {
        super(body);
        currentPos = this.getBody().getPosition();
    }

    public void reset(float x, float y)
    {
        body.setTransform(x, y, 0);
        body.setLinearVelocity(0,0);
    }

    public void move(float x, float y) {
        this.lastPos = this.currentPos;
        this.getBody().setTransform(new Vector2(x,y),0);
        this.currentPos = new Vector2(x,y);
        this.vel = new Vector2((this.currentPos.x - this.lastPos.x) *10, (this.currentPos.y - this.lastPos.y)*10);
    }

    public Vector2 getVel(){
        return this.vel;
    }

    public void setVel(Vector2 vel){
        this.vel = vel;
    }

    public boolean isTouched(float x, float y)
    {
        if((x < (this.getBody().getPosition().x + Constants.HANDLE_RADIUS) && x > (this.getBody().getPosition().x - Constants.HANDLE_RADIUS))
            &&
           (y < (this.getBody().getPosition().y + Constants.HANDLE_RADIUS) && y > (this.getBody().getPosition().y - Constants.HANDLE_RADIUS)))
            return true;

        return false;
    }
}

