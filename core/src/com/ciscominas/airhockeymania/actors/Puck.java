package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ciscominas.airhockeymania.utils.Constants;

public class Puck extends GameActor {

    public Puck(Body body)
    {
        super(body);
    }

    public void reset()
    {
        body.setTransform(Constants.PUCK_X, Constants.PUCK_Y, 0);
        body.setLinearVelocity(0,0);
    }
}
