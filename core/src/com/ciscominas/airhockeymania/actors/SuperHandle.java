package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.WorldUtils;


import static com.ciscominas.airhockeymania.utils.Constants.BOT_X;
import static com.ciscominas.airhockeymania.utils.Constants.BOT_Y;
import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_X;
import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_Y;
import static com.ciscominas.airhockeymania.utils.Constants.LINE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.PUCK_BODY;

public class SuperHandle extends PowerUp {

    String lastTouch;

    public SuperHandle(Body body) {
        super(body);
    }

    @Override
    public void effect(GameStage game) {

        lastTouch = game.getLastTouch();

        if(lastTouch == "PLAYER")
        {
            game.getHandle().destroyBody(game);
            game.setHandle(WorldUtils.createHandle(new Vector2(HANDLE_X, HANDLE_Y), game.getWorld(), Constants.HANDLE_RADIUS*2, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
        }
        else
        {
            game.getBot().destroyBody(game);
            game.setBot(WorldUtils.createHandle(new Vector2(BOT_X, BOT_Y), game.getWorld(), Constants.HANDLE_RADIUS*2, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
        }


        active = true;
    }

    @Override
    public void reset(GameStage game) {

        if(lastTouch == "PLAYER")
        {
            game.getHandle().destroyBody(game);
            game.setHandle(WorldUtils.createHandle(new Vector2(HANDLE_X, HANDLE_Y), game.getWorld(), Constants.HANDLE_RADIUS, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
        }
        else
        {
            game.getBot().destroyBody(game);
            game.setBot(WorldUtils.createHandle(new Vector2(BOT_X, BOT_Y), game.getWorld(), Constants.HANDLE_RADIUS, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
        }

        active = false;

    }

    @Override
    public boolean check(GameStage game) {
        if(!active && body != null)
            checkContact(game);

        return false;
    }
}
