package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.utils.Constants;

public class Handle extends GameActor {

    public Handle(Body body)
    {
        super(body);
    }

    public void reset(float x, float y)
    {
        body.setTransform(x, y, 0);
        body.setLinearVelocity(0,0);
    }
}
