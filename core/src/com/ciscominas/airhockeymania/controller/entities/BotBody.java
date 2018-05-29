package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.artificial_intelligence.Bot;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

/**
 * Sub-class of EntityBody, represents a bot body.
 */
public class BotBody extends EntityBody {

    /**
     * Bot's behaviour
     */
    private Bot bot;
    /**
     * Boolean that indicates whether or not the bot is able to move.
     */
    boolean controlOn;

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
     * Updates the controlOn attribute according to the control parameter.
     * @param control Boolean referring to whether or not the bot is able to move.
     */
    public void setControlOn(boolean control)
    {
        this.controlOn = control;
    }

    /**
     * Updates bot difficulty according to botDiff.
     * @param botDiff Bot's new difficulty.
     */
    public void setDifficulty(String botDiff) {
        bot.setDifficulty(botDiff);
    }
}
