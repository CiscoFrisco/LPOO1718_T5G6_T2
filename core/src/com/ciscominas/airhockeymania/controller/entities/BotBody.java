package com.ciscominas.airhockeymania.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.artificial_intelligence.Bot;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

public class BotBody extends EntityBody {

    private Bot bot;
    boolean controlOn;

    public BotBody(World world, EntityModel model, BodyDef.BodyType type) {

        super(world, model, type);

        bot = new Bot('H');

        float density = 200000f, friction = 1f, restitution = 0.5f;

        createFixture(body, createShape(model.getWidth(), CIRCLE, 0), density, friction, restitution, HANDLE_BODY,
                (short) (LINE_BODY |PUCK_BODY));
    }

    public void reset(float botX, float botY) {
        body.setTransform(botX, botY, 0);
        body.setLinearVelocity(0,0);
        bot.reset();
    }

    public Bot getBehaviour(){
        return bot;
    }

    public void setControlOn(boolean control)
    {
        this.controlOn = control;
    }
}
