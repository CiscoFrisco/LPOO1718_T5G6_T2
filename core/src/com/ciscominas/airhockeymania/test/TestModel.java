package com.ciscominas.airhockeymania.test;

import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestModel {

    @Test
    public void testScoreHandle()
    {
        GameModel gameModel = GameModel.getInstance();

        assertEquals(gameModel.getHandle().getScore(),0);
        gameModel.getHandle().incScore();
        assertEquals(gameModel.getHandle().getScore(),1);
        gameModel.getHandle().resetScore();
        assertEquals(gameModel.getHandle().getScore(),0);
    }

    @Test
    public void testScoreBot()
    {
        GameModel gameModel = GameModel.getInstance();

        assertEquals(gameModel.getBot().getScore(),0);
        gameModel.getBot().incScore();
        assertEquals(gameModel.getBot().getScore(),1);
        gameModel.getBot().resetScore();
        assertEquals(gameModel.getBot().getScore(),0);
    }
}