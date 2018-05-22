package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;

import javax.swing.text.ViewFactory;

public class GameView extends ScreenAdapter {

    private static final boolean DEBUG_PHYSICS = true;

    public final static float PIXEL_TO_METER = 0.04f;

    private static final int VIEWPORT_WIDTH = 1;

    private static final int VIEWPORT_HEIGHT = 15;

    private final AirHockeyMania game;

    private final OrthographicCamera camera;

    private Box2DDebugRenderer debugRenderer;

    private Matrix4 debugCamera;


    public GameView(AirHockeyMania game) {
        this.game = game;

        loadAssets();

        camera = createCamera();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

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

    }

    public void render(float delta) {

    }

    private void drawEntities()
    {
        PuckModel puck = GameModel.getInstance().getPuck();
    }


}
