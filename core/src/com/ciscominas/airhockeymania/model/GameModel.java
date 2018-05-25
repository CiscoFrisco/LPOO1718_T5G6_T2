package com.ciscominas.airhockeymania.model;

import com.badlogic.gdx.Gdx;
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
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.view.GameView;

import java.util.ArrayList;

public class GameModel {

    private static GameModel instance;
    private ArrayList<LineModel> edges;
    private HandleModel handle;
    private PuckModel puck;
    private BotModel bot;
    private PowerUpModel powerUp;

    private float WIDTH = GameController.ARENA_WIDTH;
    private float HEIGHT = GameController.ARENA_HEIGHT;
    private PuckModel duplicate;

    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }


    private GameModel() {

        puck = new PuckModel(Constants.PUCK_X, Constants.PUCK_Y, Constants.PUCK_RADIUS, 0);
        handle = new HandleModel(Constants.HANDLE_X, Constants.HANDLE_Y, Constants.HANDLE_RADIUS, 0);
        bot = new BotModel(Constants.BOT_X, Constants.BOT_Y, Constants.HANDLE_RADIUS, 0 );
        newPowerUp();

        setUpEdges();
    }

    public void setHandle(float width)
    {
        handle.setWidth(width);
    }

    public void duplicatePuck()
    {
        duplicate = new PuckModel(WIDTH/2, HEIGHT/2, WIDTH/16f, 0);
    }

    public void newPowerUp()
    {
        Vector2 botPos = BodyUtils.randPosition((int) WIDTH/4,(int)HEIGHT/4, (int) (3*WIDTH/4),(int) (3*HEIGHT/4));
        powerUp = new PowerUpModel(botPos.x, botPos.y, WIDTH/20, 0);
    }

    public void setEdge(float x, float width, int which)
    {
        LineModel edge = edges.get(which);
        edge.setWidth(width);
        edge.setX(x);
        edges.set(which, edge);
    }

    private void setUpEdges()
    {

        edges = new ArrayList<LineModel>();
        edges.add(new LineModel(WIDTH/8,HEIGHT/48,  WIDTH/4,HEIGHT/24,"")); //down left edge
        edges.add(new LineModel(WIDTH - WIDTH/8,HEIGHT/48, WIDTH/4,HEIGHT/24,"")); //down right edge
        edges.add(new LineModel(WIDTH/8,47*HEIGHT/48,WIDTH/4 ,HEIGHT/24,"")); //upper left edge
        edges.add(new LineModel(WIDTH - WIDTH/8,47*HEIGHT/48,WIDTH/4,HEIGHT/24, "")); //upper right edge
        edges.add(new LineModel(WIDTH - WIDTH/32,HEIGHT/2, WIDTH/16,HEIGHT, "lat")); //right edge
        edges.add(new LineModel(WIDTH/32,HEIGHT/2, WIDTH/16,HEIGHT, "lat")); //left edge
        edges.add(new LineModel(WIDTH/2,HEIGHT/2, WIDTH, HEIGHT/96,"")); //mid line
        edges.add(new LineModel(WIDTH/2,0,WIDTH/2 + 1 ,HEIGHT/96,"")); //down goal line
        edges.add(new LineModel(WIDTH/2,HEIGHT, WIDTH/2 + 1,HEIGHT/96,"")); //upper goal line
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

    public PuckModel getDuplicate() {
        return duplicate;
    }
}
