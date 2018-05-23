package com.ciscominas.airhockeymania.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.LineBody;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PowerUpModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.view.GameView;

import java.util.ArrayList;

public class GameModel {

    private static GameModel instance;
    private ArrayList<LineModel> edges;
    private HandleModel handle;
    private PuckModel puck;
    private BotModel bot;
    private PowerUpModel powerUp;


    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }


    private GameModel() {

        float height = GameController.ARENA_HEIGHT;
        float width = GameController.ARENA_WIDTH;

        puck = new PuckModel(width/2, height/2, 0.5f, 0);
        handle = new HandleModel(width/2, height/12, 0.5f, 0);
        bot = new BotModel(width/2, 10*height/12, 0.5f, 0 );
        Vector2 botPos = BodyUtils.randPosition(2,2,(int)(width) - 2,(int) (height) - 2);
        powerUp = new PowerUpModel(botPos.x, botPos.y, 0.5f, 0);

        setUpEdges();
    }

    public void setEdge(LineModel edge, int which)
    {
        edges.set(which, edge);
    }

    private void setUpEdges()
    {
        float height = GameController.ARENA_HEIGHT;
        float width = GameController.ARENA_WIDTH;

        edges = new ArrayList<LineModel>();
        edges.add(new LineModel(1.5f,0.5f,  width/4,height/24,"")); //down left edge
        edges.add(new LineModel(14.5f,0.5f, width/4,height/24,"")); //down right edge
        edges.add(new LineModel(1.5f,23.5f,width/4 ,height/24,"")); //upper left edge
        edges.add(new LineModel(14.5f,23.5f,width/4,height/24, "")); //upper right edge
        edges.add(new LineModel(15.5f,height/2, width/16,height, "lat")); //right edge
        edges.add(new LineModel(0.5f,height/2, width/16,height, "lat")); //left edge
        edges.add(new LineModel(width/2,height/2, width, height/96,"")); //mid line
        edges.add(new LineModel(width/2,0,width/2 + 1 ,height/96,"")); //down goal line
        edges.add(new LineModel(width/2,height, width/2 + 1,height/96,"")); //upper goal line
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

    public PowerUpModel getPowerUp() {
        return powerUp;
    }
}
