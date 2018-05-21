package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.box2d.PuckUserData;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.WorldUtils;

public class HardBot extends Bot {
    public HardBot(Body body) {
        super(body);
    }

    @Override
    public void move(GameStage game) {

        if(controlOn) {
            Body puck = game.getPuck().getBody();

            body.setTransform(puck.getPosition().x, body.getPosition().y, 0);

            float radius = Math.abs(puck.getPosition().sub(body.getPosition()).len());

            if ( radius < Constants.HARD_BOT_SENS && puck.getPosition().y > Constants.MID_Y )
            {
                if(((PuckUserData)puck.getUserData()).getWallBounce() < 1)
                    attack(puck);
                else
                    if(radius < 1)
                    defend(puck);
            }
        }
    }

    @Override
    public void attack(Body puck) {
        Vector2 direction = puck.getPosition();
        direction.sub(body.getPosition());
        direction.nor();

        float speed = 10;
        body.setLinearVelocity(direction.scl(speed));
    }

    @Override
    public void defend(Body puck) {
        body.setTransform(puck.getPosition().x + BodyUtils.randNumber(-Constants.HARD_BOT_MAX_OFF,Constants.HARD_BOT_MAX_OFF), body.getPosition().y, 0);
    }


}
