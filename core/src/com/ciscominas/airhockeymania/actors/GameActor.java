package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ciscominas.airhockeymania.stages.GameStage;

public abstract class GameActor extends Actor {

    protected Body body;

    public GameActor(Body body)
    {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void destroyBody(GameStage game)
    {
        game.getWorld().destroyBody(body);
        body.setUserData(null);
        body = null;
    }

}
