package com.ciscominas.airhockeymania.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ciscominas.airhockeymania.actors.Edge;
import com.ciscominas.airhockeymania.actors.Handle;
import com.ciscominas.airhockeymania.actors.Puck;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.WorldUtils;

public class GameStage extends Stage implements ContactListener{
    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private Puck puck;
    private Handle handle;
    private World world;
    private Edge lEdge;
    private Edge rEdge;
    private Body gLine1;
    private Body gLine2;
    private Body gLine3;
    private Body gLine4;

    private Vector3 touchPoint;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage() {

        setUpWorld();
        setupCamera();
        setUpTouch();
        renderer = new Box2DDebugRenderer();
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
        addActor(lEdge);
        addActor(rEdge);
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
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation

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
