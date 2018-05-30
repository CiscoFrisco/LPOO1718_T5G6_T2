package com.ciscominas.airhockeymania.model;

import com.badlogic.gdx.math.Vector2;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.database.GameResult;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PowerUpModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;
import com.ciscominas.airhockeymania.utils.Functions;
import com.ciscominas.airhockeymania.utils.Constants;

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

    /**
     * Keeps track of the last player touching the puck.
     */
    private String lastTouch;

    /**
     * Returns the instance of the GameModel, creating it if it doesn't already exist.
     * @return this instance.
     */
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

    /**
     * Sets the handles width
     * @param width the new width
     */
    public void setHandle(float width)
    {
        handle.multWidth(width);
    }

    /**
     * Creates a new puck model, designed for the DuplicatePuck powerup.
     */
    public void duplicatePuck() { duplicate =  new PuckModel(Constants.PUCK_X, Constants.PUCK_Y, Constants.PUCK_RADIUS, 0);}

    /**
     * Creates a new power up, on a random position of the screen.
     */
    public void newPowerUp()
    {
        Vector2 botPos = Functions.randPosition((int) WIDTH/4,(int)HEIGHT/4, (int) (2*WIDTH/4),(int) (2*HEIGHT/4));
        powerUp = new PowerUpModel(botPos.x, botPos.y, WIDTH/15, 0);
    }

    /**
     * Sets an edge's with by a given ratio.
     * @param width the ratio
     * @param which identifier of the edge
     */
    public void setEdge(float width, int which)
    {
        LineModel edge = edges.get(which);
        edge.multWidth(width);
        edges.set(which, edge);
    }

    /**
     * Creates the edges.
     */
    private void setUpEdges()
    {
        edges = new ArrayList<LineModel>();
        edges.add(new LineModel(WIDTH/8,HEIGHT/48,  WIDTH/4,HEIGHT/24));
        edges.add(new LineModel(WIDTH - WIDTH/8,HEIGHT/48, WIDTH/4,HEIGHT/24));
        edges.add(new LineModel(WIDTH/8,47*HEIGHT/48,WIDTH/4 ,HEIGHT/24));
        edges.add(new LineModel(WIDTH - WIDTH/8,47*HEIGHT/48,WIDTH/4,HEIGHT/24));
        edges.add(new LineModel(WIDTH - WIDTH/32,HEIGHT/2, WIDTH/16,HEIGHT));
        edges.add(new LineModel(WIDTH/32,HEIGHT/2, WIDTH/16,HEIGHT));
        //edges.add(new LineModel(WIDTH/2,HEIGHT/2, WIDTH, HEIGHT/96));
        edges.add(new LineModel(WIDTH/2,0,WIDTH/2 + 1 ,HEIGHT/96));
        edges.add(new LineModel(WIDTH/2,HEIGHT, WIDTH/2 + 1,HEIGHT/96));
    }

    /**
     * Returns the puck's model.
     * @return the puck's model.
     */
    public PuckModel getPuck() {
        return puck;
    }

    /**
     * Returns the handle's model.
     * @return the handle's model.
     */
    public HandleModel getHandle() {
        return handle;
    }

    /**
     * Returns the edges models.
     * @return the edges models.
     */
    public ArrayList<LineModel> getEdges() {
        return edges;
    }

    /**
     * Returns the bot's model.
     * @return the bot's model.
     */
    public BotModel getBot() {
        return bot;
    }

    /**
     * Returns the powerup's model.
     * @return the powerup's model.
     */
    public PowerUpModel getPowerUp() {
        return powerUp;
    }

    /**
     * Returns the duplicate's model.
     * @return the duplicate's model.
     */
    public PuckModel getDuplicate() {
        return duplicate;
    }

    /**
     *  Returns the current Game Result.
     * @return The current Game Result.
     */
    public GameResult getResult() {
        return new GameResult(handle.getScore(), bot.getScore(), System.currentTimeMillis());
    }

    /**
     *  Returns the person who last touched the puck.
     * @return Person who last touched the puck.
     */
    public String getLastTouch() {
        return lastTouch;
    }

    /**
     * Sets lastTouch as parameter lasTouch. Called every time there's a collision between a puck and a handle or bot.
     * @param lastTouch Last touch.
     */
    public void setLastTouch(String lastTouch) {
        this.lastTouch = lastTouch;
    }
}
