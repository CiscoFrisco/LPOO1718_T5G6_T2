package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ciscominas.airhockeymania.box2d.PowerUpUserData;
import com.ciscominas.airhockeymania.box2d.UserData;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.BodyUtils;

public abstract class PowerUp  extends GameActor{

    protected PowerUpUserData userData;
    protected boolean active;

    public PowerUp(Body body)
    {
        super(body);
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

    public boolean isActive() {
        return active;
    }

    public abstract void effect(GameStage game);

    public abstract void reset(GameStage game);

    public abstract boolean check(GameStage game);

    public void checkContact(GameStage game)
    {
        if (BodyUtils.checkIntersection(body, game.getPuck().getBody(),0.5))
        {
            effect(game);
            destroyBody(game);
        }

    }
}
