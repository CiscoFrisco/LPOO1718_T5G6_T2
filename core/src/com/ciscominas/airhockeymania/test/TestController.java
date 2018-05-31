package com.ciscominas.airhockeymania.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.utils.Constants;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestController {

    @Test(timeout = 1000)
    public void testBodiesPositions() {

        GameController gameController = GameController.getInstance();
        HandleBody handle = gameController.getHandle();
        BotBody bot = gameController.getBot();
        PuckBody puck = gameController.getPuckBodies().get(0);

        assertEquals(handle.getX(), Constants.HANDLE_X, 0.1f);
        assertEquals(handle.getY(), Constants.HANDLE_Y, 0.1f);

        assertEquals(bot.getX(), Constants.BOT_X, 0.1f);
        assertEquals(bot.getY(), Constants.BOT_Y, 0.1f);

        assertEquals(puck.getX(), Constants.PUCK_X, 0.1f);
        assertEquals(puck.getY(), Constants.PUCK_Y, 0.1f);
    }

    @Test
    public void testHandleMove()
    {
        World world = new World(new Vector2(0,0), true);
        HandleModel model = new HandleModel(Constants.HANDLE_X, Constants.HANDLE_Y, Constants.HANDLE_RADIUS, 0);
        HandleBody handle = new HandleBody(world, model, BodyDef.BodyType.DynamicBody);

        handle.setControlOn(true);
        assertTrue(GameController.getInstance().getHandle().getControlOn());

        handle.move(9, 3);

        assertEquals(handle.getBody().getPosition().x, 9, 0.1f);
        assertEquals(handle.getBody().getPosition().y,3,0.1f);
        assertEquals(handle.getVel().x, 20, 0.1f);
        assertEquals(handle.getVel().y, 20, 0.1f);
    }

    @Test(timeout = 10000)
    public void testContact()
    {
        GameController controller = GameController.getInstance();

        Vector2 direction = controller.getPuckBodies().get(0).getBody().getPosition();
        direction.sub(controller.getHandle().getBody().getPosition());
        direction.nor();

        float speed = 10;
        controller.getHandle().getBody().setLinearVelocity(direction.scl(speed));

        float delta = 0;
        while(controller.getPuckBodies().get(0).getBody().getLinearVelocity().len() == 0f)
        {
            controller.update(delta);
            delta+=1f;
        }

        assertNotEquals(controller.getHandle().getBody().getPosition().y, Constants.HANDLE_Y);
        assertEquals(GameModel.getInstance().getLastTouch(), EntityModel.ModelType.HANDLE);
    }

    /*
    @Test
    public void checkGoal()
    {
        GameController controller = GameController.getInstance();

        Vector2 direction = controller.getBot().getBody().getPosition();
        direction.sub(controller.getPuckBodies().get(0).getBody().getPosition());
        direction.nor();
        controller.getBot().getBody().setTransform(14, Constants.BOT_Y, 0);
        float speed = 10;
        controller.getPuckBodies().get(0).getBody().setLinearVelocity(direction.scl(speed));

        float delta = 0;
        while(GameModel.getInstance().getHandle().getScore() != 1)
        {
            controller.update(delta);
            delta+=1f;
        }

        assertEquals(GameModel.getInstance().getHandle().getScore(), 1);
        GameModel.getInstance().getHandle().resetScore();
    }*/

       /* @Test(timeout = 10000)
    public void testBot()
    {
        GameController controller = GameController.getInstance();

        Vector2 direction = controller.getBot().getBody().getPosition();
        direction.sub(controller.getPuckBodies().get(0).getBody().getPosition());
        direction.nor();

        float speed = 10;
        controller.getPuckBodies().get(0).getBody().setLinearVelocity(direction.scl(speed));

        float delta = 0;
        while(controller.getBot().getBehaviour().getState() == "RESET")
        {
            controller.update(delta);
            delta+=1f;
        }

        assertTrue(controller.getBot().getBehaviour().getPrediction());
    }*/
}