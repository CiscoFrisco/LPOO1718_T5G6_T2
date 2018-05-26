package com.ciscominas.airhockeymania.controller.entities.artificial_intelligence;

import com.badlogic.gdx.math.Vector2;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;

import java.util.Random;

public class Bot {

    float alert_radius;
    float reaction_vel;
    boolean hasPrediction;
    boolean hasAttacked;
    boolean hasDeffended;
    Vector2 prediction;
    char difficulty;
    Vector2 offset;


    public Bot(char diff){
        switch(diff)
        {
            case 'E': //easy
                alert_radius = GameController.ARENA_WIDTH/3;
                reaction_vel = GameController.ARENA_WIDTH/8;
                //offset = calculateOffset(GameController.ARENA_WIDTH/8);
                break;
            case 'N': //normal
                alert_radius = GameController.ARENA_WIDTH/2;
                reaction_vel = GameController.ARENA_WIDTH/4;
                //offset = calculateOffset(GameController.ARENA_WIDTH/12);
                break;
            case 'H': //hard
                alert_radius = GameController.ARENA_HEIGHT/3;
                reaction_vel = GameController.ARENA_WIDTH/2;
                //offset = calculateOffset(GameController.ARENA_WIDTH/16);
                break;
            default:
                break;
        }

        difficulty = diff;
        prediction = new Vector2();
        hasPrediction = false;
        hasAttacked = false;
        hasDeffended = false;
    }

    private Vector2 calculateOffset(float maxOffset) {

        Random random = new Random();
        int off = random.nextInt((int) maxOffset*10);
        off /= 10.0;
        return new Vector2(off,off);
    }

    public void move(PuckBody puck){

        if(puck.getBody().getPosition().sub(GameController.getInstance().getBot().getBody().getPosition()).len() < alert_radius)
        {
            if (!hasPrediction) {
                getTrajectory(puck);
                hasPrediction= true;
            }

            if(!hasDeffended)
                defend(prediction);
            else
                reset();
        }
    }

    public void defend(Vector2 prediction)
    {
        if(Math.abs(GameController.getInstance().getBot().getX() - prediction.x) > 0) // offset.x)
            if(GameController.getInstance().getBot().getX() > prediction.x)
            GameController.getInstance().getBot().setLinearVelocity(-reaction_vel,0);
            else if(GameController.getInstance().getBot().getX() < prediction.x)
                GameController.getInstance().getBot().setLinearVelocity(reaction_vel,0);
        else
            GameController.getInstance().getBot().setLinearVelocity(0,0);
    }

    public void attack(){


    }

    public void reset(){

        //move bot back to his original position
        hasDeffended = false;
        hasAttacked = false;
        hasPrediction = false;
    }

    public void getTrajectory(PuckBody puck){

        Vector2 puck_pos = puck.getBody().getPosition();
        Vector2 puck_vel = puck.getBody().getLinearVelocity();

        while(!calculateWallBounce(puck_pos, puck_vel))
        {
            puck_pos = prediction;
            puck_vel = new Vector2(-puck.getBody().getLinearVelocity().x, puck.getBody().getLinearVelocity().y);
        }
        System.out.println("out:" + prediction.x + "||" + prediction.y);

    }

    public boolean calculateWallBounce(Vector2 puck_pos, Vector2 puck_vel)
    {
        float x_pos = 0;
        float y_pos;
        float coef;

        //get bounce x_coordinate (depending on the x puck_vel)
        if(puck_vel.x < 0) {
            x_pos = GameModel.getInstance().getEdges().get(4).getWidth();
        }
        else if(puck_vel.x > 0) {
            x_pos = GameController.ARENA_WIDTH - GameModel.getInstance().getEdges().get(4).getWidth();
        }

        if(Math.abs(puck_vel.x) >= 1)
            coef = (x_pos - puck_pos.x)/puck_vel.x;
        else
            coef = (x_pos - puck_pos.x)*puck_vel.x;

        y_pos = puck_pos.y + puck_vel.y*coef;
        System.out.println(y_pos);
        if(y_pos >= GameController.getInstance().getBot().getY()) {

            System.out.println("entrei");
            y_pos = GameController.getInstance().getBot().getY();

            if(Math.abs(puck_vel.y) >= 1)
                coef = (y_pos - puck_pos.y)/puck_vel.y;
            else
                coef = (y_pos - puck_pos.y)*puck_vel.y;

            x_pos = puck_pos.x + puck_vel.x*coef;

            prediction = new Vector2(x_pos,y_pos);
            return true;

        } else {

            prediction = new Vector2(x_pos, y_pos);
            return false;

        }
    }
}
