package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;
import com.ciscominas.airhockeymania.view.entities.EntityView;
import com.ciscominas.airhockeymania.view.entities.PuckView;
import com.ciscominas.airhockeymania.view.entities.ViewFactory;

import java.util.ArrayList;

public class GameView extends ScreenAdapter {

    private static final boolean DEBUG_PHYSICS = true;

    public final static float PIXEL_TO_METER = 0.04f;

    public static final float VIEWPORT_WIDTH = 10f;

    public static float VIEWPORT_HEIGHT;

    private final AirHockeyMania game;

    public final OrthographicCamera camera;

    private Box2DDebugRenderer debugRenderer;

    private Matrix4 debugCamera;


    public GameView(AirHockeyMania game) {
        this.game = game;

        loadAssets();

        camera = createCamera();
    }

    private OrthographicCamera createCamera() {

        VIEWPORT_HEIGHT = (VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_HEIGHT/PIXEL_TO_METER);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }


    private void loadAssets() {
        game.getAssetManager().load("puck.png", Texture.class);
        game.getAssetManager().load("line.png", Texture.class);

        game.getAssetManager().finishLoading();
    }

    @Override
    public void show() {
        super.show();
        GameController.getInstance().setBegin();
        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    public void render(float delta) {
        GameController.getInstance().update(delta);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }

    private void drawEntities()
    {
        PuckModel puck = GameModel.getInstance().getPuck();
        BotModel bot = GameModel.getInstance().getBot();
        HandleModel handle = GameModel.getInstance().getHandle();

        EntityView view = ViewFactory.makeView(game, puck);
        view.update(puck);
        view.draw(game.getBatch());

        view = ViewFactory.makeView(game, bot);
        view.update(bot);
        view.draw(game.getBatch());

        view = ViewFactory.makeView(game, handle);
        view.update(handle);
        view.draw(game.getBatch());

        ArrayList<LineModel> lines = GameModel.getInstance().getEdges();

        for(LineModel line : lines)
        {
            view = ViewFactory.makeView(game, line);
            view.update(line);
            view.draw(game.getBatch());
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
