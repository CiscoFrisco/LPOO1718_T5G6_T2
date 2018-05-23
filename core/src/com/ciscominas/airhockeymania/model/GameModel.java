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

        float height = GameController.ARENA_HEIGHT*GameView.PIXEL_TO_METER;
        float width = GameController.ARENA_WIDTH*GameView.PIXEL_TO_METER;

        puck = new PuckModel(width/2, height/2, 0.5f, 0);
        handle = new HandleModel(width/2, height/6, 0.5f, 0);
        bot = new BotModel(width/2, 5*height/6, 0.5f, 0 );
        Vector2 botPos = BodyUtils.randPosition(0,0,1,1);
        powerUp = new PowerUpModel(botPos.x, botPos.y, 0.5f, 0);

        setUpEdges();
    }

    public void setEdge(LineModel edge, int which)
    {
        edges.set(which, edge);
    }

    private void setUpEdges()
    {
        float height = GameController.ARENA_HEIGHT*GameView.PIXEL_TO_METER;
        float width = GameController.ARENA_WIDTH*GameView.PIXEL_TO_METER;

        edges = new ArrayList<LineModel>();
        edges.add(new LineModel(width/2,height/2,  width - 5,height - 5,"")); //down left edge
        /*edges.add(new LineModel(3*width/4,0, width/4,height/6,"")); //down right edge
        edges.add(new LineModel(0,height,width/4 ,height/6,"")); //upper left edge
        edges.add(new LineModel(3*width/4,height,width/4 ,height/6,"")); //upper right edge
        edges.add(new LineModel(width,0, width/6,height, "lat")); //right edge
        edges.add(new LineModel(0,0, width/6,height, "lat")); //left edge
        edges.add(new LineModel(width/2,height/2, width, height/8,"")); //mid line
        edges.add(new LineModel(width/3,0,width/4 ,height/8,"")); //down goal line
        edges.add(new LineModel(width/3,height, width/4,height/8,"")); //upper goal line*/
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
