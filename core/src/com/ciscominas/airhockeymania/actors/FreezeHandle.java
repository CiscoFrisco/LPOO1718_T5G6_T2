package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;

public class FreezeHandle extends PowerUp {
    private String lastTouch;

    public FreezeHandle(Body body) {
        super(body);
    }

    @Override
    public void effect(GameStage game) {

        lastTouch = game.getLastTouch();

        if(lastTouch == "PLAYER") {
            game.getBot().setControlOn(false);
            game.getBot().getBody().setLinearVelocity(0,0);
        }
        else
        {
            game.setControlOn(false);
            game.getHandle().getBody().setLinearVelocity(0, 0);
        }


        active = true;
    }

    @Override
    public void reset(GameStage game) {

        if(lastTouch == "PLAYER")
            game.getBot().setControlOn(true);
        else
            game.setControlOn(true);


        active = false;
    }

    @Override
    public boolean check(GameStage game) {
        if(!active && body != null)
            checkContact(game);

        return false;
    }
}
