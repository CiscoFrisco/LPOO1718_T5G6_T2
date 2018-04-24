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
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.WorldUtils;
import com.ciscominas.airhockeymania.utils.BodyUtils;

import org.omg.CORBA.CODESET_INCOMPATIBLE;


public class GameStage extends Stage implements ContactListener{
    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 15;

    private Puck puck;
    private Handle handle;
    private World world;
    private Edge lEdge;
    private Edge rEdge;
    private Edge ulEdge;
    private Edge urEdge;
    private Edge dlEdge;
    private Edge drEdge;

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
        setUpHandle();
    }

    private void setUpHandle() {
        handle = new Handle(WorldUtils.createHandle(world));
    }

    private void setUpEdges() {
        lEdge = new Edge(WorldUtils.createEdge(world, Constants.L_EDGE_X, Constants.EDGE_Y));
        rEdge = new Edge(WorldUtils.createEdge(world, Constants.R_EDGE_X, Constants.EDGE_Y));
        ulEdge = new Edge(WorldUtils.createGoalLine(world, Constants.UPL_GL_X, Constants.UP_GL_Y, Constants.GL_WIDTH, Constants.GL_HEIGHT));
        urEdge = new Edge(WorldUtils.createGoalLine(world, Constants.UPR_GL_X, Constants.UP_GL_Y, Constants.GL_WIDTH, Constants.GL_HEIGHT));
        dlEdge = new Edge(WorldUtils.createGoalLine(world, Constants.DOWNL_GL_X, Constants.DOWN_GL_Y, Constants.GL_WIDTH, Constants.GL_HEIGHT));
        drEdge = new Edge(WorldUtils.createGoalLine(world, Constants.DOWNR_GL_X, Constants.DOWN_GL_Y, Constants.GL_WIDTH, Constants.GL_HEIGHT));
        addActor(lEdge);
        addActor(rEdge);
        addActor(ulEdge);
        addActor(urEdge);
        addActor(dlEdge);
        addActor(drEdge);
    }

    private void setUpPuck() {
        puck = new Puck(WorldUtils.createPuck(world));
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

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

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

       /* Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (BodyUtils.bodyIsPuck(a) || BodyUtils.bodyIsPuck(b))
        {
            if(BodyUtils.bodyIsEdge(a))
            {
               Vector2 curr_vel =  new Vector2(b.getLinearVelocity());

                curr_vel *= a.getRebound();

                b.setLinearVelocity(curr_vel);
            }
            else if(BodyUtils.bodyIsEdge(b))
            {
                Vector2 curr_vel =  new Vector2(a.getLinearVelocity());

                curr_vel *= b.getRebound();

                a.setLinearVelocity(curr_vel);
            }
        }*/
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
