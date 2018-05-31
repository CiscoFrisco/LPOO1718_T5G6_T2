package com.ciscominas.airhockeymania.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.ContactHandler;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.controller.entities.PowerUpBody;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.controller.entities.powerups.DuplicatePucks;
import com.ciscominas.airhockeymania.controller.entities.powerups.FreezeHandle;
import com.ciscominas.airhockeymania.controller.entities.powerups.PowerUpType;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperGoal;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperHandle;
import com.ciscominas.airhockeymania.database.DesktopDatabase;
import com.ciscominas.airhockeymania.database.GameResult;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.PowerUpModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.Functions;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class Test1 {

    @Test(timeout = 1000)
    public void testBodiesPositions() {

        GameController gameController = GameController.getInstance();

        assertEquals(gameController.getHandle().getX(), Constants.HANDLE_X, 0.1f);
        assertEquals(gameController.getHandle().getY(), Constants.HANDLE_Y, 0.1f);

        assertEquals(gameController.getBot().getX(), Constants.BOT_X, 0.1f);
        assertEquals(gameController.getBot().getY(), Constants.BOT_Y, 0.1f);

        assertEquals(gameController.getPuckBodies().get(0).getX(), Constants.PUCK_X, 0.1f);
        assertEquals(gameController.getPuckBodies().get(0).getY(), Constants.PUCK_Y, 0.1f);
    }

    @Test
    public void testDatabase()
    {
        DesktopDatabase database = new DesktopDatabase();

        int scorePlayer = 5, scoreBot = 0;

        GameResult result = new GameResult(scorePlayer,scoreBot, System.currentTimeMillis());
        assertEquals(database.selectAll().size(), 0);

        database.insert(result);
        ArrayList<GameResult> results = database.selectAll();
        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getScore1(), scorePlayer);
        database.clear();
    }

    @Test(timeout = 1000)
    public void generatePowerUp()
    {
        boolean freezeHandle = false, superGoal = false, superHandle = false, duplicatePucks = false;

        PowerUpType  powerUp;

        while(!(freezeHandle && superGoal && superHandle && duplicatePucks))
        {
            powerUp = Functions.randPowerUp();

            if(powerUp instanceof FreezeHandle)
                freezeHandle = true;
            else if(powerUp instanceof SuperGoal)
                superGoal = true;
            else if(powerUp instanceof SuperHandle)
                superHandle = true;
            else if(powerUp instanceof DuplicatePucks)
                duplicatePucks = true;
        }
    }

    @Test
    public void testScores()
    {
        GameModel gameModel = GameModel.getInstance();

        assertEquals(gameModel.getHandle().getScore(),0);
        gameModel.getHandle().incScore();
        assertEquals(gameModel.getHandle().getScore(),1);
        gameModel.getHandle().resetScore();
        assertEquals(gameModel.getHandle().getScore(),0);


        assertEquals(gameModel.getBot().getScore(),0);
        gameModel.getBot().incScore();
        assertEquals(gameModel.getBot().getScore(),1);
        gameModel.getBot().resetScore();
        assertEquals(gameModel.getBot().getScore(),0);
    }

    @Test
    public void testPowerUps()
    {
        World world = new World(new Vector2(0,0), true);
        PowerUpType type;
        PowerUpModel model = new PowerUpModel(0, 0, 1, 0);
        PowerUpBody body;
        GameModel.getInstance().setLastTouch(EntityModel.ModelType.HANDLE);

        body = new PowerUpBody(world, model, BodyDef.BodyType.StaticBody);
        type = new SuperGoal();
        testSuperGoal(type,body);


       type = new FreezeHandle();
        testFreeze(type,body);

        type = new SuperHandle();
        testSuperHandle(type,body);

        type = new DuplicatePucks();
        testDuplicatePucks(type,body);

    }

    private void testSuperGoal(PowerUpType type, PowerUpBody body) {

        assertEquals(GameModel.getInstance().getEdges().get(2).getWidth(), 4, 0.1f);

        type.effect();

        assertEquals(GameModel.getInstance().getEdges().get(2).getWidth(), 2, 0.1f);

        type.reset();

        assertEquals(GameModel.getInstance().getEdges().get(2).getWidth(), 4, 0.1f);
    }

    private void testFreeze(PowerUpType type, PowerUpBody body) {

        assertTrue(GameController.getInstance().getBot().getControlOn());
        type.effect();
        assertFalse(GameController.getInstance().getBot().getControlOn());

        type.reset();
        assertTrue(GameController.getInstance().getBot().getControlOn());
    }

    private void testSuperHandle(PowerUpType type, PowerUpBody body) {
        assertEquals(GameModel.getInstance().getHandle().getWidth(), Constants.HANDLE_RADIUS,0.1f);
        type.effect();
        assertEquals(GameModel.getInstance().getHandle().getWidth(), 2*Constants.HANDLE_RADIUS,0.1f);
        type.reset();
        assertEquals(GameModel.getInstance().getHandle().getWidth(), Constants.HANDLE_RADIUS,0.1f);

    }

    private void testDuplicatePucks(PowerUpType type, PowerUpBody body) {
        assertEquals(GameController.getInstance().getPuckBodies().size(), 1);
        type.effect();
        assertEquals(GameController.getInstance().getPuckBodies().size(), 2);
        type.reset();
        assertEquals(GameController.getInstance().getPuckBodies().size(), 1);

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
        assertEquals(GameModel.getInstance().getLastTouch(), "PLAYER");
    }

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
   }

   @Test
   public void checkPowerUp()
   {
       GameController controller = GameController.getInstance();
       controller.setUpPowerUp();

        controller.getPuckBodies().get(0).getBody().setTransform(controller.getPowerUp().getX(),controller.getPowerUp().getY(),0);

        controller.update(1f);

        assertTrue(controller.getPowerUp().isActive());

        controller.getPuckBodies().get(0).reset();
   }


}