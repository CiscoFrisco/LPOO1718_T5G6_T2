package com.ciscominas.airhockeymania.controller.entities.artificial_intelligence;

import com.badlogic.gdx.math.Vector2;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controls the behaviour of the Bot
 */
public class Bot {

    public enum State {DEFEND, ATTACK, RESET};

    /**
     * Bot alert range. It will only react when a puck enters the area within this range.
     */
    float alert_radius;
    /**
     * Bot movement velocity.
     */
    float reaction_vel;
    /**
     * Represents the bot current state, affecting the current behaviour (attacking, defending or waiting)
     */
    State state;
    /**
     * Indicates whether or not the bot as calculated a prediction to where the puck will move.
     */
    boolean hasPrediction;
    /**
     * Bot prediction to where the puck will move.
     */
    Vector2 prediction;

    /**
     *  Bot constructor.
     *  Attributes are initialized with some default values(not depending on the difficulty).
     */
    public Bot(){

        prediction = new Vector2();
        state = State.RESET;
        hasPrediction = false;
        setValues(GameModel.getInstance().getBot().getDifficulty());
    }

    /**
     * According to the current difficulty sets default values to the alert_radius and reaction_vel attributes.
     */
    public void setValues(String difficulty)
    {
        System.out.println(difficulty);
        if(difficulty == "Easy") {
            alert_radius = GameController.ARENA_WIDTH/3;
            reaction_vel = GameController.ARENA_WIDTH/8;
        }
        else if(difficulty == "Medium") {
            alert_radius = GameController.ARENA_WIDTH/2;
            reaction_vel = GameController.ARENA_WIDTH/4;
        }
        else if(difficulty == "Hard") {
            alert_radius = GameController.ARENA_HEIGHT / 3;
            reaction_vel = GameController.ARENA_WIDTH / 2;
        }
    }

    /**
     * This function will update the current behaviour (attacking or deffensive behaviour) base on some conditions.
     * This update will only occur when the puck enters the area within the alert_radius.
     * It will also call the respective functions according to the current state, acting like a state machine.
     * @param puckBodies Array of Pucks
     */
    public void move(ArrayList<PuckBody> puckBodies){

        PuckBody puck = getClosestPuck(puckBodies);

        if(puck.getBody().getPosition().sub(GameController.getInstance().getBot().getBody().getPosition()).len() < alert_radius)
        {
            if(puck.getBody().getLinearVelocity().len() < GameController.ARENA_HEIGHT /4.0 )
                state = State.ATTACK;

            else if (!hasPrediction && puck.getBody().getLinearVelocity().y > 0) {
                state = State.DEFEND;
                getTrajectory(puck);
                hasPrediction= true;
            }
        }

        if (state == State.DEFEND && hasPrediction)
            defend(prediction, puck);
        else if (state == State.ATTACK)
            attack(puck);
        else
            reset();

    }

    private PuckBody getClosestPuck(ArrayList<PuckBody> puckBodies) {

        PuckBody puck = puckBodies.get(0);
        Vector2 puck_pos = puck.getBody().getPosition();
        Vector2 bot_pos = GameController.getInstance().getBot().getBody().getPosition();
        float distance = getDistance(bot_pos,puck_pos);
        float min_distance = distance;

        for(PuckBody puckBody: puckBodies)
        {
            distance = getDistance(puckBody.getBody().getPosition(),bot_pos);

            if(distance <= min_distance)
            {
                puck = puckBody;
                min_distance = distance;
            }
        }

        return puck;
    }

    private float getDistance(Vector2 vector1, Vector2 vector2){

        return new Vector2(vector2.x - vector1.x, vector2.y - vector1.y).len();
    }

    /**
     * While puck y coordinate is less than bot y coordinate, move bot towards the prediction position.
     * If the bot fails to get to the prediction position on time (puck y coordinate > bot y coordinate), change bot state back do the initial state(RESET).
     * @param prediction 2D Vector referring to the final position of the puck (final position corresponds the position where the puck y coordinate is equal to the bot y coordinate).
     */
    public void defend(Vector2 prediction, PuckBody puck)
    {
        Vector2 puck_pos = puck.getBody().getPosition();

        if(puck_pos.y <= prediction.y ) {
            if (GameController.getInstance().getBot().getX() > prediction.x + GameController.ARENA_WIDTH/160)
                GameController.getInstance().getBot().setLinearVelocity(-reaction_vel, 0);
            else if (GameController.getInstance().getBot().getX() < prediction.x -  GameController.ARENA_WIDTH/160)
                GameController.getInstance().getBot().setLinearVelocity(reaction_vel, 0);
            else
                GameController.getInstance().getBot().setLinearVelocity(0, 0);
        }
        else
            changeState(State.RESET);
    }

    /**
     * Applies a linear velocity towards the puck.
     * Changes current state to initial state(RESET) to prevent the bot from start following the puck's movement.
     */
    public void attack(PuckBody puck){
        state = State.RESET;
        Vector2 bot_pos = GameController.getInstance().getBot().getBody().getPosition();
        Vector2 puck_pos = puck.getBody().getPosition();
        Vector2 vel = new Vector2((puck_pos.x - bot_pos.x) * reaction_vel, (puck_pos.y - bot_pos.y) * reaction_vel);
        GameController.getInstance().getBot().getBody().setLinearVelocity(vel);
    }

    /**
     * Changes the current state of the bot
     * @param new_state New state to which the bot will be updated
     */
    public void changeState(State new_state){
        state = new_state;
    }

    /**
     * Applies a linear velocity towards the bot's initial position and stops it once it gets there.
     * It also sets hasPrediction attribute back to it's default value (false).
     */
    public void reset(){
        //move bot back to his original position
        Vector2 current_botPos = GameController.getInstance().getBot().getBody().getPosition();

        if(Math.abs(current_botPos.x - Constants.BOT_X) >  GameController.ARENA_WIDTH/160 || Math.abs(current_botPos.y - Constants.BOT_Y) >  GameController.ARENA_WIDTH/160)
        {
            GameController.getInstance().getBot().getBody().setLinearVelocity((Constants.BOT_X - current_botPos.x)*reaction_vel,(Constants.BOT_Y - current_botPos.y)*reaction_vel);
        }
        else{
            GameController.getInstance().getBot().getBody().setLinearVelocity(0,0);
            hasPrediction = false;
        }
    }

    /**
     * Updates prediction member variable to the final position of the puck.
     * This function has a while loop that calls a function that calculates a wallBounce from the puck.
     * It will only leave that loop when the final position is retrieved (final position = position where puck y position = bot y position)
     * @param puck Puck
     */
    public void getTrajectory(PuckBody puck){

        Vector2 puck_pos = puck.getBody().getPosition();
        Vector2 puck_vel = puck.getBody().getLinearVelocity();

        while(!calculateWallBounce(puck_pos, puck_vel))
        {
            puck_pos = prediction;
            puck_vel = new Vector2(-puck_vel.x, puck_vel.y);
        }
    }

    /**
     * This function uses vectorial algebra to calculate where the puck bounces in the field.
     * If it reaches/goes over the bots y coordinate, it will calculate the final position.
     * @param puck_pos Puck's current position
     * @param puck_vel Pucc's current velocity
     * @return Return whether or not the final position has been calculated.
     */
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
    
    public State getState()
    {
        return state;
    }

    public boolean getPrediction() {
        return hasPrediction;
    }
}
