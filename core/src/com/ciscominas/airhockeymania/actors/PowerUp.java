package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ciscominas.airhockeymania.box2d.PowerUpUserData;
import com.ciscominas.airhockeymania.box2d.UserData;
import com.ciscominas.airhockeymania.stages.GameStage;

import java.util.Date;

public abstract class PowerUp  extends Actor{

    protected Body body;
    protected PowerUpUserData userData;
    protected boolean active;
    protected Date init;
    protected Date now;

    public PowerUp()
    {
        userData = new PowerUpUserData();
        active = false;
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

    public abstract boolean checkScore(GameStage game);

    public abstract boolean check(GameStage game);

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void checkDead(GameStage game) {

        if (body != null) {
            PowerUpUserData data = getUserData();
            if (data.isFlaggedForRemoval()) {
                init = new Date();

            }
        }
    }
}
