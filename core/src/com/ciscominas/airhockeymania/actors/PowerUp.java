package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.box2d.PowerUpUserData;
import com.ciscominas.airhockeymania.box2d.UserData;
import com.ciscominas.airhockeymania.stages.GameStage;

public abstract class PowerUp extends GameActor {

    private PowerUpUserData userData;
    protected boolean active;

    public PowerUp(Body body)
    {
        super(body);
        userData = new PowerUpUserData();
    }

    public PowerUpUserData getUserData() {
        return userData;
    }

    public void deleteBody()
    {
        body = null;
    }

    public abstract void effect(GameStage game);

    public abstract void reset(GameStage game);

    public boolean isActive() {
        return active;
    }

    public abstract void checkScore(GameStage game);
}
