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
import com.ciscominas.airhockeymania.actors.Edge;
import com.ciscominas.airhockeymania.actors.Handle;
import com.ciscominas.airhockeymania.actors.Puck;
import com.ciscominas.airhockeymania.utils.WorldUtils;
import com.ciscominas.airhockeymania.utils.BodyUtils;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

import com.ciscominas.airhockeymania.utils.Constants;

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
import static com.ciscominas.airhockeymania.utils.Constants.PUCK_BODY;
import static com.ciscominas.airhockeymania.utils.Constants.R_EDGE_X;
import static com.ciscominas.airhockeymania.utils.Constants.UPL_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.UPR_GL_X;
import static com.ciscominas.airhockeymania.utils.Constants.UP_GL_Y;

public class GameStage extends Stage implements ContactListener{
    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 15;

    private Puck puck;
    private Handle handle;
    private Handle bot;
    private World world;
    private Edge lEdge;
    private Edge rEdge;
    private Edge ulEdge;
    private Edge urEdge;
    private Edge dlEdge;
    private Edge drEdge;
    private Edge midLine;

    private Vector3 touchPoint;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private int scorePlayer;
    private int scoreOpponent;

    public GameStage() {

        setUpWorld();
        setupCamera();
        setUpTouch();
        renderer = new Box2DDebugRenderer();

        scorePlayer = 0;
        scoreOpponent = 0;
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        setUpPuck();
        setUpEdges();
        setUpHandles();
    }

    private void setUpHandles() {
        handle = new Handle(WorldUtils.createHandle(new Vector2(HANDLE_X, HANDLE_Y), world, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
        addActor(handle);

        bot = new Handle(WorldUtils.createHandle(new Vector2(10, 14), world, HANDLE_BODY, (short) (PUCK_BODY | LINE_BODY)));
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
        addActor(lEdge);
        addActor(rEdge);
        addActor(ulEdge);
        addActor(urEdge);
        addActor(dlEdge);
        addActor(drEdge);
        addActor(midLine);
    }

    private void setUpPuck() {
        puck = new Puck(WorldUtils.createPuck(world, PUCK_BODY, (short) (LINE_BODY | HANDLE_BODY)));
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

    @Override
    public boolean touchDown(int screenX, int screenY, int point, int button)
    {
        camera.unproject(touchPoint.set(screenX, screenY, 0));
        // calculte the normalized direction from the body to the touch position
        Vector2 direction = new Vector2(touchPoint.x, touchPoint.y);
        direction.sub(handle.getBody().getPosition());
        direction.nor();

        float speed = 10;
        handle.getBody().setLinearVelocity(direction.scl(speed));

        return true;
    }

   /* @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        camera.unproject(touchPoint.set(screenX, screenY, 0));
        handle.getBody().setTransform(touchPoint.x, touchPoint.y, 0);
        return true;
    }*/

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        bot.getBody().setTransform(puck.getBody().getPosition().x, bot.getBody().getPosition().y, 0);

        if(puck.getBody().getPosition().y < 0)
        {
            scorePlayer++;
            resetActors();
        } else if (puck.getBody().getPosition().y > 20) {
            scoreOpponent++;
            resetActors();
        }

        //TODO: Implement interpolation

    }

    private void resetActors() {
        handle.reset();
        puck.reset();
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

    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x,y,0));
    }

    @Override
    public void beginContact(Contact contact) {

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
}
