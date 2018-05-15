package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.box2d.PowerUpUserData;
import com.ciscominas.airhockeymania.box2d.UserData;

public class PowerUp extends GameActor {

    private PowerUpUserData userData;

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
}
