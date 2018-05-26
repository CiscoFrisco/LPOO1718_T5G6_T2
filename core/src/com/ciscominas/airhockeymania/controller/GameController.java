package com.ciscominas.airhockeymania.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.EntityBody;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.controller.entities.LineBody;
import com.ciscominas.airhockeymania.controller.entities.PowerUpBody;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.database.GameResult;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PowerUpModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.WorldUtils;
import com.ciscominas.airhockeymania.view.GameView;

import java.util.ArrayList;
import java.util.Date;

import static com.ciscominas.airhockeymania.utils.Constants.BOT_X;
import static com.ciscominas.airhockeymania.utils.Constants.BOT_Y;
import static com.ciscominas.airhockeymania.utils.WorldUtils.randPowerUp;

public class GameController implements ContactListener {

    private static GameController instance;

    private final World world;

    public static final float ARENA_WIDTH = Gdx.graphics.getWidth()*GameView.PIXEL_TO_METER;//GameView.VIEWPORT_WIDTH;

    public static float ARENA_HEIGHT =  Gdx.graphics.getHeight()*GameView.PIXEL_TO_METER;//GameView.VIEWPORT_HEIGHT;

    private final PuckBody puckBody;
    private HandleBody handleBody;
    private float accumulator;
    private String lastTouch;
    private BotBody botBody;

    private Date begin;
    private Date end;
    private PowerUpBody powerUpBody;
    private ArrayList<LineBody> edges;
    private int scorePlayer;
    private int scoreOpponent;
    private boolean gameOver;
    private boolean controlOn;
    private Sound hit;
    private boolean soundEnabled;
    private float volume;

    private GameController() {

        world = new World(new Vector2(0, 0), true);

        puckBody = new PuckBody(world, GameModel.getInstance().getPuck(), BodyDef.BodyType.DynamicBody);

        handleBody = new HandleBody(world, GameModel.getInstance().getHandle(), BodyDef.BodyType.DynamicBody);

        botBody = new BotBody(world, GameModel.getInstance().getBot(), BodyDef.BodyType.DynamicBody);

        createEdges();

        world.setContactListener(this);
        gameOver = false;
    }

    public void setHandleBody(HandleBody handle)
    {
        this.handleBody = handle;
    }

    private void createEdges() {
        ArrayList<LineModel> models = GameModel.getInstance().getEdges();
        edges = new ArrayList<LineBody>();
        short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;
        short mask2 = EntityBody.HANDLE_BODY;

        //Criar linhas de golo
        float goalWidth = 0.15f, goalHeight = 0.1f;
        edges.add(new LineBody(world, models.get(0), BodyDef.BodyType.StaticBody, mask));
        edges.add(new LineBody(world, models.get(1), BodyDef.BodyType.StaticBody, mask));
        edges.add(new LineBody(world, models.get(2), BodyDef.BodyType.StaticBody, mask));
        edges.add(new LineBody(world, models.get(3), BodyDef.BodyType.StaticBody, mask));

        //Criar laterais
        float latWidth = 0.1f, latHeight = 0.4f;
        edges.add(new LineBody(world, models.get(4), BodyDef.BodyType.StaticBody, mask));
        edges.add(new LineBody(world, models.get(5), BodyDef.BodyType.StaticBody, mask));

        //Criar linha do meio
        float midWidth = 0.4f, midHeight = 0.1f;
        edges.add(new LineBody(world, models.get(6), BodyDef.BodyType.StaticBody, mask2));

        //Criar linhas de baliza
        float limitWidth = 0.4f, limitHeight = 0.1f;
        edges.add(new LineBody(world, models.get(7), BodyDef.BodyType.StaticBody, mask2));
        edges.add(new LineBody(world, models.get(8), BodyDef.BodyType.StaticBody, mask2));
    }

    public void setLine(LineBody line, int which)
    {
        edges.set(which, line);
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public void update(float delta)
    {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        end = new Date();

        long timeElapsed = (end.getTime() - begin.getTime())/1000;

        if(timeElapsed >= Constants.POWERUP_FREQUENCY && powerUpBody == null)
            setUpPowerUp();

        if(powerUpBody!=null)
        {
            if(powerUpBody.check()){
                powerUpBody = null;
                begin = new Date();
            }
        }

        botBody.getBehaviour().move(puckBody);

        checkScore();

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    public boolean checkScore()
    {
        boolean changed = false;

        if(puckBody.getBody().getPosition().y < -2)
        {
            scoreOpponent++;
            resetBodies();
            changed = true;
            if(powerUpBody!=null && powerUpBody.isActive())
            {
                powerUpBody.reset();
                powerUpBody = null;
                begin = new Date();
            }
        } else if (puckBody.getBody().getPosition().y > GameController.ARENA_HEIGHT + 2) {
            scorePlayer++;
            resetBodies();
            changed = true;
            if(powerUpBody!=null && powerUpBody.isActive())
            {
                powerUpBody.reset();
                powerUpBody = null;
                begin = new Date();
            }
        }

        if((scoreOpponent >= Constants.WIN_SCORE || scorePlayer >= Constants.WIN_SCORE) && Math.abs(scorePlayer - scoreOpponent) >= 2) {
            gameOver = true;
            changed = true;
        }

        return changed;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    private void setUpPowerUp() {
        GameModel.getInstance().newPowerUp();
        powerUpBody = new PowerUpBody(world, GameModel.getInstance().getPowerUp(), BodyDef.BodyType.StaticBody);
    }


    @Override
    public void beginContact(Contact contact) {
        Body b1 = contact.getFixtureA().getBody();
        Body b2 = contact.getFixtureB().getBody();

        if(b1.getUserData() instanceof PuckModel && b2.getUserData() instanceof HandleModel)
        {
            b1.setLinearVelocity(handleBody.getVel().add(b1.getLinearVelocity()));
            lastTouch = "PLAYER";
            if(soundEnabled)
                hit.play(volume);

            ((PuckModel) b1.getUserData()).resetWallBounce();
        }
        else if (b2.getUserData() instanceof PuckModel && b1.getUserData() instanceof HandleModel)
        {
            b2.setLinearVelocity(handleBody.getVel().add(b2.getLinearVelocity()));
            lastTouch = "PLAYER";
            if(soundEnabled)
                hit.play(volume);

            ((PuckModel) b2.getUserData()).resetWallBounce();

        }
        else if(b1.getUserData() instanceof PuckModel && b2.getUserData() instanceof BotModel)
        {
            lastTouch = "BOT";
            if(soundEnabled)
                hit.play(volume);
            ((PuckModel) b1.getUserData()).resetWallBounce();
        }
        else if (b2.getUserData() instanceof PuckModel && b1.getUserData() instanceof BotModel) {
            lastTouch = "BOT";
            if(soundEnabled)
                hit.play(volume);
            ((PuckModel) b2.getUserData()).resetWallBounce();

        }
        else if(b1.getUserData() instanceof PuckModel && b2.getUserData() instanceof LineModel && ((LineModel) b2.getUserData()).getPos().equals("Lat"))
        {
            ((PuckModel) b1.getUserData()).incWallBounce();
        }
        else if(b2.getUserData() instanceof PuckModel && b1.getUserData() instanceof LineModel && ((LineModel) b1.getUserData()).getPos().equals("Lat"))
        {
            ((PuckModel) b2.getUserData()).incWallBounce();
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public HandleBody getHandle() {
        return handleBody;
    }

    public String getLastTouch() {
        return lastTouch;
    }

    public World getWorld() {
        return world;
    }

    public ArrayList<LineBody> getEdges() { return edges;};

    public void setBegin()
    {
        begin = new Date();
    }

    public PuckBody getPuckBody() {
        return puckBody;
    }

    public void incScorePlayer() {
        this.scorePlayer++;
    }

    public void resetBodies() {
        handleBody.reset(Constants.HANDLE_X,Constants.HANDLE_Y);
        puckBody.reset();
        botBody.reset(Constants.BOT_X, Constants.BOT_Y);
    }

    public void incScoreOpponent() {
        this.scoreOpponent++;
    }

    public void setControlOn(boolean controlOn) {
        this.controlOn = controlOn;
    }

    public BotBody getBot()
    {
        return botBody;
    }

    public void setBotBody(BotBody botBody) {
        this.botBody = botBody;
    }

    public void reset() {
        gameOver = false;
        resetBodies();
        scoreOpponent = 0;
        scorePlayer = 0;

        if(powerUpBody != null && powerUpBody.getBody() != null)
        {
            WorldUtils.destroyBody(powerUpBody.getBody());
            powerUpBody.getBody().setUserData(null);
            powerUpBody = null;
        }
    }

    public GameResult getResult() {
        return new GameResult(scorePlayer, scoreOpponent, System.currentTimeMillis());
    }

    public PowerUpBody getPowerUp() {
        return powerUpBody;
    }

    public void setSounds(AssetManager sounds) {
        hit = sounds.get("hit.mp3");
    }

    public int getPlayerScore() {
        return scorePlayer;
    }

    public int getScoreOpponent() {
        return scoreOpponent;
    }

    public void setSound(float volume, boolean soundEffectsEnabled) {
        soundEnabled = soundEffectsEnabled;
        this.volume = volume;
    }
}
