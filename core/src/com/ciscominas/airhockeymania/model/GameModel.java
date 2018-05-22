package com.ciscominas.airhockeymania.model;

import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;

import java.util.ArrayList;

public class GameModel {

    private static GameModel instance;
    private ArrayList<LineModel> edges;
    private HandleModel handle;
    private PuckModel puck;
    private BotModel bot;


    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }


    private GameModel() {
        puck = new PuckModel(GameController.ARENA_WIDTH/2, GameController.ARENA_HEIGHT/2);
        handle = new HandleModel(GameController.ARENA_WIDTH/2, GameController.ARENA_HEIGHT/6);
        bot = new BotModel(GameController.ARENA_WIDTH/2, 5*GameController.ARENA_HEIGHT/6 );

        setUpEdges();
    }

    private void setUpEdges()
    {
        edges = new ArrayList<LineModel>();
        edges.add(new LineModel(0,0, ""));
        edges.add(new LineModel(0,0, ""));
        edges.add(new LineModel(0,0, ""));
        edges.add(new LineModel(0,0, ""));
        edges.add(new LineModel(0,0, "Lat"));
        edges.add(new LineModel(0,0, "Lat"));
        edges.add(new LineModel(0,0, ""));
        edges.add(new LineModel(0,0, ""));
        edges.add(new LineModel(0,0, ""));
    }

    public PuckModel getPuck() {
        return puck;
    }

    public HandleModel getHandle() {
        return handle;
    }

    public ArrayList<LineModel> getEdges() {
        return edges;
    }

    public BotModel getBot() {
        return bot;
    }
}
