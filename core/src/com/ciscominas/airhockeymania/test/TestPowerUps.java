package com.ciscominas.airhockeymania.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.PowerUpBody;
import com.ciscominas.airhockeymania.controller.entities.powerups.DuplicatePucks;
import com.ciscominas.airhockeymania.controller.entities.powerups.FreezeHandle;
import com.ciscominas.airhockeymania.controller.entities.powerups.PowerUpType;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperGoal;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperHandle;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.PowerUpModel;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.Functions;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPowerUps {

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

        assertEquals(GameModel.getInstance().getEdges().get(2).getWidth(), 4f, 0.1f);

        type.effect();

        assertEquals(GameModel.getInstance().getEdges().get(2).getWidth(), 2f, 0.1f);

        type.reset();

        assertEquals(GameModel.getInstance().getEdges().get(2).getWidth(), 4f, 0.1f);
    }

    private void testFreeze(PowerUpType type, PowerUpBody body) {

        assertTrue(GameModel.getInstance().getBot().getControlOn());
        type.effect();
        assertFalse(GameModel.getInstance().getBot().getControlOn());

        type.reset();
        assertTrue(GameModel.getInstance().getBot().getControlOn());
    }

    private void testSuperHandle(PowerUpType type, PowerUpBody body) {

        assertEquals(GameModel.getInstance().getHandle().getWidth(), Constants.HANDLE_RADIUS,0.1f);
        type.effect();
        assertEquals(GameModel.getInstance().getHandle().getWidth(), 2*Constants.HANDLE_RADIUS,0.1f);
        type.reset();
        assertEquals(GameModel.getInstance().getHandle().getWidth(), Constants.HANDLE_RADIUS,0.1f);
    }

    private void testDuplicatePucks(PowerUpType type, PowerUpBody body) {

        assertEquals(GameController.getInstance().getPuckBodies().size(), 1, 0.1f);
        type.effect();
        assertEquals(GameController.getInstance().getPuckBodies().size(), 2, 0.1f);
        type.reset();
        assertEquals(GameController.getInstance().getPuckBodies().size(), 1, 0.1f);

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
    public void checkPowerUp()
    {
        GameController controller = GameController.getInstance();
        controller.setUpPowerUp();

        controller.getPuckBodies().get(0).getBody().setTransform(controller.getPowerUp().getX(),controller.getPowerUp().getY(),0);

        controller.update(0.01f);

        assertTrue(controller.getPowerUp().isActive());

        controller.getPuckBodies().get(0).reset();
        controller.getPowerUp().reset();
    }


}