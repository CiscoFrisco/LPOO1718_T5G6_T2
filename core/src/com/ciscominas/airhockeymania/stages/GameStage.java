package com.ciscominas.airhockeymania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ciscominas.airhockeymania.actors.DuplicatePucks;
import com.ciscominas.airhockeymania.actors.Edge;
import com.ciscominas.airhockeymania.actors.Handle;
import com.ciscominas.airhockeymania.actors.HardBot;
import com.ciscominas.airhockeymania.actors.PowerUp;
import com.ciscominas.airhockeymania.actors.Puck;
import com.ciscominas.airhockeymania.box2d.PowerUpUserData;
import com.ciscominas.airhockeymania.utils.WorldUtils;
import com.ciscominas.airhockeymania.utils.BodyUtils;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

import com.ciscominas.airhockeymania.utils.Constants;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static com.ciscominas.airhockeymania.utils.Constants.BOT_X;
import static com.ciscominas.airhockeymania.utils.Constants.BOT_Y;
import static com.ciscominas.airhockeymania.utils.Constants.DOWNL_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.DOWNR_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.DOWN_GL_Y;
import static com.ciscominas.airhockeymania.utils.Constants.EDGE_HEIGHT;
import static com.ciscominas.airhockeymania.utils.Constants.EDGE_WIDTH;
import static com.ciscominas.airhockeymania.utils.Constants.EDGE_Y;
import static com.ciscominas.airhockeymania.utils.Constants.GL_HEIGHT;
import static com.ciscominas.airhockeymania.utils.Constants.GL_WIDTH;
import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_X;
import static com.ciscominas.airhockeymania.utils.Constants.HANDLE_Y;
import static com.ciscominas.airhockeymania.utils.Constants.LINE_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.L_EDGE_X;
import static com.ciscominas.airhockeymania.utils.Constants.MID_HEIGHT;
import static com.ciscominas.airhockeymania.utils.Constants.MID_WIDTH;
import static com.ciscominas.airhockeymania.utils.Constants.MID_X;
import static com.ciscominas.airhockeymania.utils.Constants.MID_Y;
import static com.ciscominas.airhockeymania.utils.Constants.POWERUP_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.PUCK_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.PUCK_X;
import static com.ciscominas.airhockeymania.utils.Constants.R_EDGE_X;
import static com.ciscominas.airhockeymania.utils.Constants.UPL_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.UPR_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.UP_GL_Y;
import static java.lang.Math.abs;

public class GameStage extends Stage implements ContactListener{
    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 15;
    private Date init;
    private Date now;

    private Puck puck;
    private Handle handle;
    private HardBot bot;
    private World world;
    private Edge lEdge;
    private Edge rEdge;
    private Edge ulEdge;
    private Edge urEdge;
    private Edge dlEdge;
    private Edge drEdge;
    private Edge midLine;
    private Edge goalLine;
    private Edge anotherGoalLine;
    private PowerUp currPowerUp;

    private Vector3 touchPoint;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    public int scorePlayer;
    public int scoreOpponent;

    private boolean gameOver;

    public GameStage() {

        setUpWorld();
        setupCamera();
        setUpTouch();
        renderer = new Box2DDebugRenderer();

        scorePlayer = 0;
        scoreOpponent = 0;

        init = new Date();
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setUpPuck();
        setUpEdges();
        setUpHandles();
    }

    private void setUpHandles() {
        handle = new Handle(WorldUtils.createHandle(new Vector2(HANDLE_X, HANDLE_Y), world, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
        addActor(handle);
        bot = new HardBot(WorldUtils.createHandle(new Vector2(BOT_X, BOT_Y), world, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
        addActor(bot);
    }

    private void setUpEdges() {
        lEdge = new Edge(WorldUtils.createLine(world, L_EDGE_X, EDGE_Y, EDGE_WIDTH, EDGE_HEIGHT, LINE_BODY,(short)(PUCK_BODY | HANDLE_BODY)));
        rEdge = new Edge(WorldUtils.createLine(world, R_EDGE_X, EDGE_Y, EDGE_WIDTH, EDGE_HEIGHT, LINE_BODY,(short)(PUCK_BODY | HANDLE_BODY)));
        ulEdge = new Edge(WorldUtils.createLine(world, UPL_GL_X, UP_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
        urEdge = new Edge(WorldUtils.createLine(world, UPR_GL_X, UP_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
        dlEdge = new Edge(WorldUtils.createLine(world, DOWNL_GL_X, DOWN_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
        drEdge = new Edge(WorldUtils.createLine(world, DOWNR_GL_X, DOWN_GL_Y, GL_WIDTH, GL_HEIGHT, LINE_BODY, (short)(PUCK_BODY | HANDLE_BODY)));
        midLine = new Edge(WorldUtils.createLine(world, MID_X, MID_Y, MID_WIDTH, MID_HEIGHT, LINE_BODY, (short)(HANDLE_BODY)));
        goalLine = new Edge(WorldUtils.createLine(world, MID_X, UP_GL_Y, MID_WIDTH, MID_HEIGHT, LINE_BODY, (short)(HANDLE_BODY)));
        anotherGoalLine = new Edge(WorldUtils.createLine(world, MID_X, DOWN_GL_Y, MID_WIDTH, MID_HEIGHT, LINE_BODY, (short)(HANDLE_BODY)));

        addActor(lEdge);
        addActor(rEdge);
        addActor(ulEdge);
        addActor(urEdge);
        addActor(dlEdge);
        addActor(drEdge);
        addActor(midLine);
        addActor(goalLine);
        addActor(anotherGoalLine);
    }

    private void setUpPuck() {
        puck = new Puck(WorldUtils.createPuck(world, PUCK_BODY, (short) (LINE_BODY | HANDLE_BODY | PUCK_BODY)));
        addActor(puck);
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpTouch()
    {
        touchPoint = new Vector3();
        Gdx.input.setInputProcessor(this);
    }

    private void setUpPowerUp()
    {
        currPowerUp = WorldUtils.randPowerUp(world);
        addActor(currPowerUp);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int point, int button)
    {
        camera.unproject(touchPoint.set(screenX, screenY, 0));
        // calculate the normalized direction from the body to the touch position
        Vector2 direction = new Vector2(touchPoint.x, touchPoint.y);
        direction.sub(handle.getBody().getPosition());
        direction.nor();

        float speed = 10;
        handle.getBody().setLinearVelocity(direction.scl(speed));

        return true;
    }
/*
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        camera.unproject(touchPoint.set(screenX, screenY, 0));
        handle.getBody().setTransform(touchPoint.x, touchPoint.y, 0);

        return true;
    }
*/
    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        now = new Date();
        long timeElapsed = (now.getTime() - init.getTime())/1000;

        if(timeElapsed >= 5 && currPowerUp == null)
            setUpPowerUp();

        if(currPowerUp != null)
            if(currPowerUp.check(this)) {
                currPowerUp = null;
                init = new Date();
            }

        bot.move(this);

        checkScore();

        //TODO: Implement interpolation

    }

    private void checkScore()
    {
        if(puck.getBody().getPosition().y < 0)
        {
            scorePlayer++;
            resetActors();
            if(currPowerUp!=null && currPowerUp.isActive())
            {
                currPowerUp.reset(this);
                init = new Date();
            }
        } else if (puck.getBody().getPosition().y > 16) {
            scoreOpponent++;
            resetActors();
            if(currPowerUp!=null && currPowerUp.isActive())
            {
                currPowerUp.reset(this);
                init = new Date();
            }
        }

        if(currPowerUp != null && currPowerUp.checkScore(this))
                init = new Date();

        if((scoreOpponent >= 5 || scorePlayer >= 5) && abs(scorePlayer - scoreOpponent) >= 2)
            gameOver = true;
    }

    public void resetActors() {
        handle.reset(Constants.HANDLE_X,Constants.HANDLE_Y);
        puck.reset();
        bot.reset(BOT_X, BOT_Y);
    }

    public String getScore()
    {
        return "Score: " + scorePlayer + " - " + scoreOpponent;
    }

    @Override
    public void draw() {
        super.draw();

        renderer.render(world, camera.combined);
    }

    @Override
    public void beginContact(Contact contact) {
        Body b1 = contact.getFixtureA().getBody();
        Body b2 = contact.getFixtureB().getBody();

        if(currPowerUp != null && (b1.equals(puck.getBody()) && b2.equals(currPowerUp.getBody())))
        {
            b1.setLinearVelocity(b1.getLinearVelocity());

            currPowerUp.getUserData().setFlaggedForRemoval(true);
        }
        else if(currPowerUp != null &&  (b1.equals(currPowerUp.getBody()) && b2.equals(puck.getBody())))
        {
                b2.setLinearVelocity(b2.getLinearVelocity());

                currPowerUp.getUserData().setFlaggedForRemoval(true);
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

    public boolean isGameOver() {
        return gameOver;
    }

    public void reset() {
        scoreOpponent = 0;
        scorePlayer = 0;
        bot.reset(BOT_X, BOT_Y);
        handle.reset(HANDLE_X, HANDLE_Y);
        puck.reset();
        gameOver = false;
        init = new Date();

        if(currPowerUp != null && currPowerUp.getBody()!=null)
        {
            world.destroyBody(currPowerUp.getBody());
            currPowerUp.getBody().setUserData(null);
            currPowerUp = null;
        }
    }

    public World getWorld() {
        return world;
    }

    public Puck getPuck() {
        return puck;
    }
}
