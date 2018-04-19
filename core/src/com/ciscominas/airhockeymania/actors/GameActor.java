package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameActor extends Actor {

    protected Body body;

    public GameActor(Body body)
    {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }
}
