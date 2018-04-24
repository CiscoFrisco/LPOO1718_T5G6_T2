package com.ciscominas.airhockeymania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.stages.GameStage;

public class GameScreen extends ScreenAdapter {


    private GameStage stage;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public GameScreen()
    {
        stage = new GameStage();
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
        stage.draw();
        stage.act(delta);
    }


}


    /*
    private final AirHockeyMania game;
    private final Texture puck;
    private final Texture handle;

    private final OrthographicCamera camera;
    private Viewport gamePort;
    private static float HEIGHT = 2;
    private static float ppu = Gdx.graphics.getHeight() / HEIGHT;
    private static float WIDTH = Gdx.graphics.getWidth() / ppu;


    private final World world;
    private final Body puckBody;
    private final Body handleBody;


    public GameScreen(AirHockeyMania g) {
        game = g;

        game.getAssetManager().load("puck.png", Texture.class);
        game.getAssetManager().load("handle.png", Texture.class);
        game.getAssetManager().finishLoading();

        puck = (Texture) game.getAssetManager().get("puck.png");
        handle = (Texture) game.getAssetManager().get("handle.png");

        camera = new OrthographicCamera(WIDTH, HEIGHT);

        world = new World(new Vector2(0, 0), true);

        puckBody = createPuckBody();
        handleBody = createHandleBody();
    }

    private Body createHandleBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        float ratio = ((float)Gdx.graphics.getWidth()) / (float) Gdx.graphics.getHeight();
        Body body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(0.20f); //22cm

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.friction =  .5f;

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        circle.dispose();

        return body;
    }

    private Body createEdgeBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        float ratio = ((float)Gdx.graphics.getWidth()) / (float) Gdx.graphics.getHeight();
        Body body = world.createBody(bodyDef);
        body.setTransform(0, 0, 0); // Middle of the viewport, no rotation

        // Create circle shape
        EdgeShape edge = new EdgeShape();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edge;

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        edge.dispose();

        return body;
    }

    private Body createPuckBody() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        float ratio = ((float)Gdx.graphics.getWidth()) / (float) Gdx.graphics.getHeight();
        Body body = world.createBody(bodyDef);

        // Create circle shape
        CircleShape circle = new CircleShape();
        circle.setRadius(0.20f); //22cm

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = .5f;
        fixtureDef.friction =  .5f;

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        circle.dispose();

        return body;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.getBatch().setProjectionMatrix(camera.combined);
        camera.update();

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        game.getBatch().draw(puck, 0,  0);

        game.getBatch().end();
        //world.step(delta,6,2);

    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width, height);
    }
}*/