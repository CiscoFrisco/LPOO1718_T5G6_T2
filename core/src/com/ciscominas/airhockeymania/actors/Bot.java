package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;
import com.ciscominas.airhockeymania.utils.Constants;

public abstract class Bot extends GameActor{

    protected boolean controlOn;
    protected boolean defended;
    protected boolean calcTrajectory;

    public Bot(Body body) {
        super(body);
        controlOn = true;
        defended = false;
        calcTrajectory = false;
    }

    public void reset(float x, float y)
    {
        body.setTransform(x, y, 0);
        body.setLinearVelocity(0,0);
    }

    public void setDefended(boolean isDefended)
    {
        defended = isDefended;
    }

    public void setTrajectoryFlag(boolean isCalculated)
    {
        calcTrajectory = isCalculated;
    }

    public abstract void move(GameStage game);

    public boolean isControlOn()
    {
        return controlOn;
    }

    public void setControlOn(boolean control)
    {
        controlOn = control;
    }

    public abstract void attack(Body puck);
    public abstract void defend(Body puck, Vector2 final_pos);

    public void calculateTrajectory(Body puck,Vector2 final_pos)
    {
        Body ghost = puck;
        while(final_pos.y < body.getPosition().y - 1){

            final_pos.add(ghost.getLinearVelocity());
            if(final_pos.x <= Constants.EDGE_WIDTH || final_pos.x > Constants.R_EDGE_X - Constants.EDGE_WIDTH)
            {
                ghost.setLinearVelocity(-ghost.getLinearVelocity().x,ghost.getLinearVelocity().y);
            }
            else if(final_pos.y <= Constants.EDGE_WIDTH)
            {
                ghost.setLinearVelocity(ghost.getLinearVelocity().x, - ghost.getLinearVelocity().y);
            }
        }
    }
}
