package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;

public abstract class Bot extends GameActor{

    protected boolean controlOn;

    public Bot(Body body) {
        super(body);
        controlOn = true;
    }

    public void reset(float x, float y)
    {
        body.setTransform(x, y, 0);
        body.setLinearVelocity(0,0);
    }

    public abstract void move(GameStage game);

    public boolean isControlOn()
    {
        return controlOn;
    }

    public void setControlOn(boolean control)
    {
        controlOn = control;
    }
}
