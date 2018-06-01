package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.entities.artificial_intelligence.Bot;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

import java.util.ArrayList;

/**
 * Sub-class of EntityBody, represents a bot body.
 */
public class BotBody extends EntityBody {

    /**
     * Bot's behaviour
     */
    private Bot bot;

    /**
     * BotBody constructor. Calls super class constructor with the respective parameters.
     * Initializes bot's behaviour and other default variables.
     * @param world World
     * @param model Bot's model
     * @param type Indicates if a body is dynamic, static or kinematic.
     */
    public BotBody(World world, EntityModel model, BodyDef.BodyType type) {

        super(world, model, type);

        bot = new Bot();

        float density = 200000f, friction = 1f, restitution = 0.5f;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, HANDLE_BODY,
                (short) (LINE_BODY |PUCK_BODY));
    }

    /**
     * Move this bot according to its type.
     * @param puckBodies the active pucks in the game
     */
    public void move(ArrayList<PuckBody> puckBodies)
    {
        if(GameModel.getInstance().getBot().getControlOn())
            bot.move(puckBodies);
    }

    /**
     * Resets bot's position, velocity and behaviour.
     * @param botX Bot initial X coordinate.
     * @param botY Bot initial Y coordinate.
     */
    public void reset(float botX, float botY) {
        body.setTransform(botX, botY, 0);
        body.setLinearVelocity(0,0);
        bot.reset();
        //bot.changeState("RESET");
    }

    /**
     * Returns bot's behaviour
     * @return Bot Bot behaviour
     */
    public Bot getBehaviour(){
        return bot;
    }

    /**
     * Sets the difficulty of the bot
     */
    public void setDifficulty() {
        bot.setValues(GameModel.getInstance().getBot().getDifficulty());
    }
}
