package com.ciscominas.airhockeymania.controller.entities.artificial_intelligence;

import com.badlogic.gdx.math.Vector2;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.Constants;

import java.util.Random;

public class Bot {

    float alert_radius;
    float reaction_vel;
    String state;
    boolean hasPrediction;
    Vector2 prediction;
    char difficulty;


    public Bot(char diff){
        switch(diff)
        {
            case 'E': //easy
                alert_radius = GameController.ARENA_WIDTH/3;
                reaction_vel = GameController.ARENA_WIDTH/8;
                break;
            case 'N': //normal
                alert_radius = GameController.ARENA_WIDTH/2;
                reaction_vel = GameController.ARENA_WIDTH/4;
                break;
            case 'H': //hard
                alert_radius = GameController.ARENA_HEIGHT/3;
                reaction_vel = GameController.ARENA_WIDTH/2;
                break;
            default:
                break;
        }

        difficulty = diff;
        prediction = new Vector2();
        state = "RESET";
        hasPrediction = false;
    }

    public void move(PuckBody puck){

       System.out.println(puck.getBody().getLinearVelocity().len());
        if(puck.getBody().getPosition().sub(GameController.getInstance().getBot().getBody().getPosition()).len() < alert_radius)
        {
           if(puck.getBody().getLinearVelocity().len() < GameController.ARENA_HEIGHT /4.0 )
                state = "ATTACK";

            else if (!hasPrediction && puck.getBody().getLinearVelocity().y > 0) {
                state = "DEFEND";
                getTrajectory(puck);
                hasPrediction= true;
            }
        }

        System.out.println(state);
        if (state == "DEFEND" && hasPrediction)
            defend(prediction);
        else if (state == "ATTACK")
            attack();
        else
            reset();

    }

    public void defend(Vector2 prediction)
    {
        System.out.println("deffend");

        Vector2 puck_pos = GameController.getInstance().getPuckBody().getBody().getPosition();

        if(puck_pos.y <= prediction.y ) {
            if (GameController.getInstance().getBot().getX() > prediction.x + GameController.ARENA_WIDTH/160)
                GameController.getInstance().getBot().setLinearVelocity(-reaction_vel, 0);
            else if (GameController.getInstance().getBot().getX() < prediction.x -  GameController.ARENA_WIDTH/160)
                GameController.getInstance().getBot().setLinearVelocity(reaction_vel, 0);
            else
                GameController.getInstance().getBot().setLinearVelocity(0, 0);
        }
        else
            changeState("RESET");
    }

    public void attack(){
        state = "RESET";
        System.out.println("attack");
        Vector2 bot_pos = GameController.getInstance().getBot().getBody().getPosition();
        Vector2 puck_pos = GameController.getInstance().getPuckBody().getBody().getPosition();
        Vector2 vel = new Vector2((puck_pos.x - bot_pos.x) * reaction_vel, (puck_pos.y - bot_pos.y) * reaction_vel);
        GameController.getInstance().getBot().getBody().setLinearVelocity(vel);
    }

    public void changeState(String new_state){
        state = new_state;
    }

    public void reset(){
        //move bot back to his original position
        Vector2 current_botPos = GameController.getInstance().getBot().getBody().getPosition();

        if(Math.abs(current_botPos.x - Constants.BOT_X) >  GameController.ARENA_WIDTH/160 || Math.abs(current_botPos.y - Constants.BOT_Y) >  GameController.ARENA_WIDTH/160)
        {
            System.out.println("entrei");
            GameController.getInstance().getBot().getBody().setLinearVelocity((Constants.BOT_X - current_botPos.x)*reaction_vel,(Constants.BOT_Y - current_botPos.y)*reaction_vel);
        }
        else{
            System.out.println("tambem");
            GameController.getInstance().getBot().getBody().setLinearVelocity(0,0);
            hasPrediction = false;
        }
    }

    public void getTrajectory(PuckBody puck){

        Vector2 puck_pos = puck.getBody().getPosition();
        Vector2 puck_vel = puck.getBody().getLinearVelocity();

        while(!calculateWallBounce(puck_pos, puck_vel))
        {
            puck_pos = prediction;
            puck_vel = new Vector2(-puck_vel.x, puck_vel.y);
        }
    }

    public boolean calculateWallBounce(Vector2 puck_pos, Vector2 puck_vel)
    {
        float x_pos = 0;
        float y_pos = 0;
        float coef;

        //get bounce x_coordinate (depending on the x puck_vel)
        if(puck_vel.x < 0) {
            x_pos = GameModel.getInstance().getEdges().get(4).getWidth();
        }
        else if(puck_vel.x > 0) {
            x_pos = GameController.ARENA_WIDTH - GameModel.getInstance().getEdges().get(4).getWidth();
        }

        if(puck_vel.x != 0) {
            if (Math.abs(puck_vel.x) >= GameController.ARENA_WIDTH / 16)
                coef = (x_pos - puck_pos.x) / puck_vel.x;
            else
                coef = (x_pos - puck_pos.x) * puck_vel.x;

            y_pos = puck_pos.y + puck_vel.y*coef;
        }

        if((y_pos >= GameController.getInstance().getBot().getY() && y_pos != 0) || puck_vel.x == 0)
        {
            y_pos = GameController.getInstance().getBot().getY();

            if(Math.abs(puck_vel.y) >=  GameController.ARENA_WIDTH/16)
                coef = (y_pos - puck_pos.y)/puck_vel.y;
            else
                coef = (y_pos - puck_pos.y)*puck_vel.y;

            x_pos = puck_pos.x + puck_vel.x*coef;

            prediction = new Vector2(x_pos,y_pos);
            return true;
        }
        else
        {
            prediction = new Vector2(x_pos, y_pos);
            return false;
        }
    }
}
