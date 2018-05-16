package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.WorldUtils;

import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.LINE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.PUCK_BODY;

public class DuplicatePucks extends PowerUp {

    private Puck puck;

    public DuplicatePucks(Body body) {
        super(body);
    }

    @Override
    public void effect(GameStage game) {
            puck = new Puck(WorldUtils.createPuck(game.getWorld(), PUCK_BODY, (short) (LINE_BODY | HANDLE_BODY)));
            game.addActor(puck);
            active = true;
    }

    @Override
    public void reset(GameStage game) {
        game.getWorld().destroyBody(puck.getBody());
        puck.getBody().setUserData(null);
        puck.deleteBody();
        active = false;
    }

    @Override
    public void checkScore(GameStage game) {
        if(puck != null && puck.getBody() != null)
        {
            if(puck.getBody().getPosition().y < 0)
            {
                game.scorePlayer++;
                game.resetActors();
            } else if (puck.getBody().getPosition().y > 20) {
                game.scoreOpponent++;
                game.resetActors();
            }
        }

    }
}
