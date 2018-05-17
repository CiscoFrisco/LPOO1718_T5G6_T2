package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ciscominas.airhockeymania.box2d.PowerUpUserData;
import com.ciscominas.airhockeymania.box2d.UserData;
import com.ciscominas.airhockeymania.stages.GameStage;

import java.util.Date;

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
        Vector2 powerUp = body.getPosition();
        Vector2 puckPos = game.getPuck().getBody().getPosition();

        if (Math.abs(powerUp.x - puckPos.x) <= 0.5 && Math.abs(powerUp.y - puckPos.y) <= 0.5 && !active)
        {
            effect(game);
            destroyBody(game);
        }
    }

    public void destroyBody(GameStage game)
    {
        game.getWorld().destroyBody(body);
        body.setUserData(null);
        body = null;
    }
}
