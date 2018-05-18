package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.WorldUtils;

import static com.ciscominas.airhockeymania.utils.Constants.DOWNL_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.DOWNR_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.DOWN_GL_Y;
import static com.ciscominas.airhockeymania.utils.Constants.GL_HEIGHT;
import static com.ciscominas.airhockeymania.utils.Constants.GL_WIDTH;
import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.LINE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.PUCK_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.UPL_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.UPR_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.UP_GL_Y;

public class SuperGoal extends PowerUp {

    String lastTouch;

    public SuperGoal(Body body) {
        super(body);
    }

    @Override
    public void effect(GameStage game) {
        lastTouch = game.getLastTouch();


        //aumentar baliza do bot
        if(lastTouch == "PLAYER")
        {
          //destruir linhas
            game.getUlEdge().destroyBody(game);
            game.getUrEdge().destroyBody(game);
            game.setUpperLines(WorldUtils.createLine(game.getWorld(), UPL_GL_X, UP_GL_Y, GL_WIDTH-2, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)),
                    WorldUtils.createLine(game.getWorld(), UPR_GL_X+2, UP_GL_Y, GL_WIDTH-2, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
        }
        else
        {
            //destuir linhas
            game.getDlEdge().destroyBody(game);
            game.getDrEdge().destroyBody(game);
            game.setLowerLines(WorldUtils.createLine(game.getWorld(), DOWNL_GL_X, DOWN_GL_Y, GL_WIDTH-2, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)),
                    WorldUtils.createLine(game.getWorld(), DOWNR_GL_X+2, DOWN_GL_Y, GL_WIDTH-2, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
        }

        active = true;
    }

    @Override
    public void reset(GameStage game) {
        if(lastTouch == "PLAYER")
        {
            game.getUlEdge().destroyBody(game);
            game.getUrEdge().destroyBody(game);
            game.setUpperLines(WorldUtils.createLine(game.getWorld(), UPL_GL_X, UP_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)),
                    WorldUtils.createLine(game.getWorld(), UPR_GL_X, UP_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
        }
        else
        {
            game.getDlEdge().destroyBody(game);
            game.getDrEdge().destroyBody(game);
            game.setUpperLines(WorldUtils.createLine(game.getWorld(), DOWNL_GL_X, DOWN_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)),
                    WorldUtils.createLine(game.getWorld(), DOWNR_GL_X, DOWN_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
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
