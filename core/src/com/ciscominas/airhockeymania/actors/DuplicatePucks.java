package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.WorldUtils;

import java.util.Date;

import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.LINE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.POWERUP_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.PUCK_BODY;

public class DuplicatePucks extends PowerUp {

    private Puck puck;

    public DuplicatePucks(Body body) {
        super(body);
    }

    @Override
    public void effect(GameStage game) {
            puck = new Puck(WorldUtils.createPuck(game.getWorld(), PUCK_BODY, (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY)));
            puck.getBody().setTransform(game.getPuck().getBody().getPosition(),0);
            puck.getBody().setLinearVelocity(-game.getPuck().getBody().getLinearVelocity().x,game.getPuck().getBody().getLinearVelocity().y);
            game.addActor(puck);
            active = true;
    }

    @Override
    public void reset(GameStage game) {
        game.getWorld().destroyBody(puck.getBody());
        puck.getBody().setUserData(null);
        puck.deleteBody();
        active = false;
        game.resetInit();
    }

    public boolean checkScore(GameStage game) {
        if(puck != null && puck.getBody() != null)
        {
            if(puck.getBody().getPosition().y < 0)
            {
                game.scorePlayer++;
                game.resetActors();
                reset(game);
                return true;
            } else if (puck.getBody().getPosition().y > 16) {
                game.scoreOpponent++;
                game.resetActors();
                reset(game);
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean check(GameStage game) {

        if(!active && body != null)
        {
            checkContact(game);
            return false;
        }

        if(checkScore(game))
            return true;

        return false;
    }
}
