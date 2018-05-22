package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.box2d.PuckUserData;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.utils.Constants;

public class HardBot extends Bot {
    public HardBot(Body body) {
        super(body);
    }

    @Override
    public void move(GameStage game) {

        if(controlOn) {
            Body puck = game.getPuck().getBody();
            Vector2 final_pos = puck.getPosition();
            float radius = Math.abs(puck.getPosition().sub(body.getPosition()).len());

            if ( radius < Constants.HARD_BOT_SENS && puck.getPosition().y > Constants.MID_Y )
            {
               /* if(puck.getLinearVelocity().y < 0)
                    defended = true;
                if(!calcTrajectory) {
                    System.out.println("finalX:" + final_pos.x + "||" + "finalY:" + final_pos.y);
                    calculateTrajectory(puck, final_pos);
                    System.out.println("finalX:" + final_pos.x + "||" + "finalY:" + final_pos.y);
                    calcTrajectory = true;
                }
                if(!defended)
                    defend(puck, final_pos);
                else
                    resetPosition();*/

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
    public void defend(Body puck, Vector2 final_pos) {

        if(body.getPosition().x > final_pos.x)
            body.setLinearVelocity(-5,0);
        else if(body.getPosition().x < final_pos.x)
            body.setLinearVelocity(5,0);
        else
            body.setLinearVelocity(0,0);

    }

    public void resetPosition()
    {
        Vector2 vel = new Vector2(Constants.BOT_X - body.getPosition().x, Constants.BOT_Y - body.getPosition().y);
        body.setLinearVelocity(vel);

        if(body.getPosition().x == Constants.BOT_X && body.getPosition().y == Constants.BOT_Y - 1)
            body.setLinearVelocity(0,0);

    }

}
