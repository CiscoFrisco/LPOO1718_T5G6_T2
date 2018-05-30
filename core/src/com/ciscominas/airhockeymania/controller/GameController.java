package com.ciscominas.airhockeymania.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.EntityBody;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.controller.entities.LineBody;
import com.ciscominas.airhockeymania.controller.entities.PowerUpBody;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.Functions;
import com.ciscominas.airhockeymania.view.GameView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Implements the in-game logic bringing together all other classes from controller package and making the most of their capabilies.
 */
public class GameController {

    /**
     * The singleton instance of this controller
     */
    private static GameController instance;

    /**
     * The physics world controlled by this controller.
     */
    private final World world;

    /**
     * The arena width in meters.
     */
    public static float ARENA_WIDTH = 16;

    /**
     * The arena height in meters.
     */
    public static float ARENA_HEIGHT = 24;

    /**
     * Number of seconds between the deactivation of a powerup, and the creation of another
     */
    private static final int POWERUP_FREQUENCY = 5;

    /**
     * Minimum number of goals to win a game
     */
    private static final int WIN_SCORE = 5;

    /**
     * The puck body.
     */
    private ArrayList<PuckBody> puckBodies;

    /**
     * The handle body.
     */
    private HandleBody handleBody;

    /**
     * The bot body.
     */
    private BotBody botBody;

    /**
     * The powerUp body.
     */
    private PowerUpBody powerUpBody;

    /**
     * ArrayList of Line bodies.
     */
    private ArrayList<LineBody> edges;

    /**
     * Accumulator used to calculate the simulation step.
     */
    private float accumulator;

    /**
     * Used, in association to "end" attribute, to count up the time to generate a powerUp.
     */
    private Date begin;

    /**
     * Used, in association to "begin" attribute, to count up the time to generate a powerUp.
     */
    private Date end;

    /**
     * Indicates whether or not the game is over.
     */
    private boolean gameOver;

    /**
     * Hit sound for puck and handle collision.
     */
    private Sound hit;

    /**
     * Indicates if the sounds are enabled or not.
     */
    private boolean soundEnabled;

    /**
     * Sound volume
     */
    private float volume;

    /**
     * GameController constructor. Creates a new world and the bodies to be present in it.
     * Also sets the world as the contact listener and initializes the attribute game over to false.
     */
    private GameController() {

        world = new World(new Vector2(0, 0), true);
        setUpBodies();

        begin = new Date();
        world.setContactListener(new ContactHandler());
        gameOver = false;
    }

    /**
     * Creates all elements to be present in the world (puck, handle, bot and edges) except the powerUp.
     */
    private void setUpBodies()
    {
        puckBodies = new ArrayList<PuckBody>();
        puckBodies.add(new PuckBody(world, GameModel.getInstance().getPuck(), BodyDef.BodyType.DynamicBody));

        handleBody = new HandleBody(world, GameModel.getInstance().getHandle(), BodyDef.BodyType.DynamicBody);

        botBody = new BotBody(world, GameModel.getInstance().getBot(), BodyDef.BodyType.DynamicBody);

        createEdges();
    }

    /**
     * Sets up ARENA_HEIGHT and ARENA_WIDTH. Necessary to perform Unit Tests, since graphics are not available.
     * So, default values are set.
     */
    public static void setUpDimensions()
    {
        ARENA_WIDTH = Gdx.graphics.getWidth()*GameView.PIXEL_TO_METER;

        ARENA_HEIGHT =  Gdx.graphics.getHeight()*GameView.PIXEL_TO_METER;
    }

    /**
     *  Sets handleBody as handle.
     * @param handle New handleBody.
     */
    public void setHandleBody(HandleBody handle)
    {
        this.handleBody = handle;
    }

    /**
     * Creates all the edges of the world and adds them to an array.
     */
    private void createEdges() {
        ArrayList<LineModel> models = GameModel.getInstance().getEdges();
        edges = new ArrayList<LineBody>();
        short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;
        short mask2 = EntityBody.HANDLE_BODY;

        for(int i = 0; i < 6; i++)
            edges.add(new LineBody(world, models.get(i), BodyDef.BodyType.StaticBody, mask));

        for(int i = 6; i < 8; i++)
            edges.add(new LineBody(world, models.get(i), BodyDef.BodyType.StaticBody, mask2));
    }

    /**
     *  Updates edge[which] from edges to line.
     * @param line New LineBody.
     * @param which Line index to be updated.
     */
    public void setLine(LineBody line, int which)
    {
        edges.set(which, line);
    }

    /**
     * Returns a singleton instance of a game controller
     *
     * @return the singleton instance
     */
    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    /**
     * Calculate the simulation step, update the accumulator and make step.
     * @param delta The size of this physics step in seconds.
     */
    private void step(float delta)
    {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }
    }

    /**
     * Calculates the next physics step of duration delta (in seconds).
     * @param delta The size of this physics step in seconds.
     */
    public void update(float delta)
    {
        step(delta);

        end = new Date();

        checkPowerUp();

        botBody.move(puckBodies);

        checkScore();

        updatePositions();
    }

    /**
     * If there is no powerUp and time since last powerUp is greater or equal than the powerUp frequency, then generate a powerUp.
     * Else if there is a powerUp but it was deactivated reset the time since last PowerUp and set powerUpBody to null.
     */
    private void checkPowerUp()
    {
        long timeElapsed = (end.getTime() - begin.getTime())/1000;

        if(timeElapsed >= POWERUP_FREQUENCY && powerUpBody == null)
            setUpPowerUp();
        else if(powerUpBody != null)
            powerUpBody.check();
    }

    /**
     * Updates world element positions on the GameModel package.
     */
    private void updatePositions()
    {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    /**
     *  Checks whether or not a player scored a goal and updates scoreOpponent or scorePlayer according to who scored.
     *  Also checks if the game is over (five or plus goals where scored and difference between opponent and player score is greater than or equal to 2):
     * @return Returns whether or not the score has changed.
     */
    public boolean checkScore()
    {
        boolean changed = false;

        for(PuckBody puckBody: puckBodies) {
            if (puckBody.getBody().getPosition().y < -2) {
                GameModel.getInstance().getBot().incScore();
                changed = true;
                handleGoal();
                break;

            } else if (puckBody.getBody().getPosition().y > GameController.ARENA_HEIGHT + 2) {
                GameModel.getInstance().getHandle().incScore();
                changed = true;
                handleGoal();
                break;
            }
        }
            int scorePlayer = GameModel.getInstance().getHandle().getScore();
            int scoreOpponent = GameModel.getInstance().getBot().getScore();

            if ((scoreOpponent >= WIN_SCORE || scorePlayer >= WIN_SCORE) && Math.abs(scorePlayer - scoreOpponent) >= 2) {
                gameOver = true;
                changed = true;
        }
        return changed;
    }

    /**
     *Resets world bodies including powerUp if existent.
     */
    private void handleGoal() {

        resetBodies();
        if(powerUpBody!=null && powerUpBody.isActive())
        {
            powerUpBody.reset();
            powerUpBody = null;
            begin = new Date();
        }
    }

    /**
     * Returns boolean gameOver that indicates whether or not the game as finished.
     * @return Boolean gameOver that indicates whether or not the game as finished.
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Generates a new random PowerUp.
     */
    public void setUpPowerUp() {
        GameModel.getInstance().newPowerUp();
        powerUpBody = new PowerUpBody(world, GameModel.getInstance().getPowerUp(), BodyDef.BodyType.StaticBody);
    }

    /**
     *  Returns handle body.
     * @return Handle body.
     */
    public HandleBody getHandle() {
        return handleBody;
    }

    /**
     *  Returns world.
     * @return World.
     */
    public World getWorld() {
        return world;
    }

    /**
     *  Returns an ArrayList of edges.
     * @return Edges.
     */
    public ArrayList<LineBody> getEdges() { return edges;}

    /**
     * Start the counter on powerUp frequency.
     */
    public void setBegin()
    {
        begin = new Date();
    }

    /**
     *Returns puck body.
     * @return Puck body.
     */
    public ArrayList<PuckBody> getPuckBodies() {
        return puckBodies;
    }

    /**
     * Reset handle, puck and bot bodies.
     */
    public void resetBodies() {
        handleBody.reset(Constants.HANDLE_X,Constants.HANDLE_Y);
        for(PuckBody puckBody : puckBodies)
            puckBody.reset();
        botBody.reset(Constants.BOT_X, Constants.BOT_Y);
    }

    /**
     *  Return bot body.
     * @return Bot body.
     */
    public BotBody getBot()
    {
        return botBody;
    }

    /**
     *  Sets botBody as parameter botBody.
     * @param botBody Bot body
     */
    public void setBotBody(BotBody botBody) {
        this.botBody = botBody;
    }

    /**
     * Resets the GameController by setting the gameOver attribute back to its default value (false).
     * Enables movement to the bot and to the player.
     * Resets allBodies in this world by calling resetBodies() function.
     * Resets the scores.
     * Resets the powerUp if existent.
     */
    public void reset() {
        gameOver = false;
        handleBody.setControlOn(true);
        botBody.setControlOn(true);
        resetBodies();
        GameModel.getInstance().getBot().resetScore();
        GameModel.getInstance().getHandle().resetScore();

        if(powerUpBody != null && powerUpBody.getBody() != null)
        {
            Functions.destroyBody(powerUpBody.getBody());
            powerUpBody = null;
        }
    }

    /**
     * Returns a powerUp body.
     * @return PowerUp body.
     */
    public PowerUpBody getPowerUp() {
        return powerUpBody;
    }

    /**
     *  Sets hit sound as the respective sound in the assetManager.
     * @param sounds AssetManager containing the sound wanted.
     */
    public void setSounds(AssetManager sounds) {
        hit = sounds.get(Constants.HIT_SOUND);
    }

    /**
     *  Sets sound related variables as the ones parsed to the function as parameters.
     * @param volume Volume
     * @param soundEffectsEnabled Boolean that indicates whether or not the sound is enabled.
     */
    public void setSound(float volume, boolean soundEffectsEnabled) {
        soundEnabled = soundEffectsEnabled;
        this.volume = volume;
    }

    /**
     * Returns boolean that indicates whether or not the sound is enabled.
     * @return Boolean that indicates whether or not the sound is enabled.
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    /**
     * Returns hit sound.
     * @return Hit sound.
     */
    public Sound getHit() {
        return hit;
    }

    /**
     *  Returns sound volume.
     * @return Sound volume.
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Sets bot difficulty as botDiff.
     * @param botDiff Bot difficulty.
     */
    public void setBotDiff(String botDiff) {
        this.botBody.setDifficulty(botDiff);
    }
}
