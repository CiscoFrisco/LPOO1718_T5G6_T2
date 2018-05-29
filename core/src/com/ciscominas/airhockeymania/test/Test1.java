package com.ciscominas.airhockeymania.test;

import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.powerups.DuplicatePucks;
import com.ciscominas.airhockeymania.controller.entities.powerups.FreezeHandle;
import com.ciscominas.airhockeymania.controller.entities.powerups.PowerUpType;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperGoal;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperHandle;
import com.ciscominas.airhockeymania.database.DesktopDatabase;
import com.ciscominas.airhockeymania.database.GameResult;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.Functions;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

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

    @Test(timeout = 1000)
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
            else
                fail("Not a valid PowerUp type!");

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
    public void test()
    {

    }




}