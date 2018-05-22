package com.ciscominas.airhockeymania.model;

import com.ciscominas.airhockeymania.actors.Handle;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;

import java.util.ArrayList;

public class GameModel {

    private static GameModel instance;
    private ArrayList<LineModel> edges;
    private HandleModel handle;
    private PuckModel puck;



    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }


    private GameModel() {
        puck = new PuckModel(GameController.ARENA_WIDTH/2, GameController.ARENA_HEIGHT/2);
        handle = new HandleModel(GameController.ARENA_WIDTH/2, GameController.ARENA_HEIGHT/6);

        setUpEdges();
    }

    private void setUpEdges()
    {
        edges = new ArrayList<LineModel>();
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
        edges.add(new LineModel(0,0));
    }

    public PuckModel getPuck() {
        return puck;
    }
}
