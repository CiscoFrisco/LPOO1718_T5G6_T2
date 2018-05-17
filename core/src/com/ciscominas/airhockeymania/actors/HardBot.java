package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.Constants;

public class HardBot extends Bot {
    public HardBot(Body body) {
        super(body);
    }

    @Override
    public void move(GameStage game) {

        Body puck = game.getPuck().getBody();

        //body.setTransform(puck.getPosition().x, body.getPosition().y, 0);
        if(puck.getPosition().y > Constants.MID_Y && puck.getLinearVelocity().len() < 10 && puck.getPosition().y < body.getPosition().y)
        {
            Vector2 direction = puck.getPosition();
            direction.sub(body.getPosition());
            direction.nor();

            float speed = 10;
            body.setLinearVelocity(direction.scl(speed));
        }
    }
}
