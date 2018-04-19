package com.ciscominas.airhockeymania.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ciscominas.airhockeymania.actors.Edge;
import com.ciscominas.airhockeymania.actors.Puck;
import com.ciscominas.airhockeymania.utils.Constants;
import com.ciscominas.airhockeymania.utils.WorldUtils;

public class GameStage extends Stage {
    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = 20;
    private static final int VIEWPORT_HEIGHT = 13;

    private Puck puck;
    private World world;
    private Edge lEdge;
    private Edge rEdge;
    private Body gLine1;
    private Body gLine2;
    private Body gLine3;
    private Body gLine4;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameStage() {

        setUpWorld();
        setupCamera();
        renderer = new Box2DDebugRenderer();
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        setUpPuck();
        setUpEdges();
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
}
