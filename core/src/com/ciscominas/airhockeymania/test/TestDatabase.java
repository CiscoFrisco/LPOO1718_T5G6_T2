package com.ciscominas.airhockeymania.test;

import com.ciscominas.airhockeymania.database.DesktopDatabase;
import com.ciscominas.airhockeymania.database.GameResult;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class TestDatabase {

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
        database.pop();
    }
}