package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.powerups.DuplicatePucks;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.*;
import com.ciscominas.airhockeymania.view.entities.EntityView;
import com.ciscominas.airhockeymania.view.entities.ViewFactory;

import java.util.ArrayList;

/**
 * The game screen. Responsible for showing the game elements to the user, receiving input,
 * and sending it to the game controller.
 */
public class GameView extends ScreenAdapter {

    private static final boolean DEBUG_PHYSICS = false;

    public final static float PIXEL_TO_METER = 0.04f;

    public static final float VIEWPORT_WIDTH = GameController.ARENA_WIDTH;

    public static float VIEWPORT_HEIGHT;

    private final AirHockeyMania game;

    public final OrthographicCamera camera;

    private Box2DDebugRenderer debugRenderer;

    private Matrix4 debugCamera;

    private Music bkg_music;

    private Texture pause;

    private Texture background;

    private final float PAUSE_WIDTH = Gdx.graphics.getWidth()/8;
    protected final float PAUSE_X = VIEWPORT_WIDTH/PIXEL_TO_METER - PAUSE_WIDTH;
    protected float PAUSE_Y;

    private BitmapFont score;

    /**
     * Builds a GameView object. Loads the assets, sets up the camera and sends the screen coordinates
     * to the GameController.
     * @param game the main game class
     */
    public GameView(AirHockeyMania game) {
        this.game = game;

        loadAssets();
        GameController.getInstance().setSounds(game.getAssetManager());
        GameController.getInstance().setUpDimensions();

        bkg_music = game.getAssetManager().get("bkg_music1.mp3");
        bkg_music.setLooping(true);

        pause = game.getAssetManager().get("pause.png");
        score = new BitmapFont();
        score.setColor(243/255f,12/255f,12/255f, 1);
        camera = createCamera();
        background = game.getAssetManager().get("rink.png");
    }

    /**
     * Creates a camera and sets its position to the center.
     * @return the game camera
     */
    private OrthographicCamera createCamera() {

        VIEWPORT_HEIGHT = GameController.ARENA_HEIGHT;
        PAUSE_Y = VIEWPORT_HEIGHT/PIXEL_TO_METER - PAUSE_WIDTH;
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

    /**
     * Loads the game assets, such as textures and background music.
     */
    private void loadAssets() {
        AssetManager manager = game.getAssetManager();
        manager.load("puck.png", Texture.class);
        manager.load("handle.png", Texture.class);
        manager.load("powerup.png", Texture.class);
        manager.load("blue.png", Texture.class);
        manager.load( "hit.mp3", Sound.class);
        manager.load( "bkg_music1.mp3", Music.class);
        manager.load("pause.png", Texture.class);
        manager.load("rink.png",Texture.class);
        manager.finishLoading();
    }

    /**
     * Shows the screen to the user, playing the background music if enabled, and setting the bot's difficulty.
     */
    @Override
    public void show() {
        super.show();
        GameController.getInstance().setBegin();
        Gdx.input.setInputProcessor(new InputHandler(this));

        GameController.getInstance().setBotDiff(game.getPreferences().getDifficulty());

        if(game.getPreferences().isMusicEnabled())
        {
            bkg_music.setVolume(game.getPreferences().getMusicVolume());
            bkg_music.play();
        }
        else
            bkg_music.stop();

        GameController.getInstance().setSound(game.getPreferences().getSoundVolume(), game.getPreferences().isSoundEffectsEnabled());
    }

    /**
     * Draw the entities to the screen, along with the score.
     * @param delta
     */
    public void render(float delta) {
        GameController.getInstance().update(delta);

        checkGameOver();

        Gdx.gl.glClearColor( 253/255f, 207/255f, 113/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();

        game.getBatch().draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        drawEntities();

        game.getBatch().draw(pause, PAUSE_X,PAUSE_Y, PAUSE_WIDTH,PAUSE_WIDTH);
        score.getData().setScale(2,2);
        score.draw(game.getBatch(), Integer.toString(GameModel.getInstance().getHandle().getScore()),(VIEWPORT_WIDTH - VIEWPORT_WIDTH/8)/PIXEL_TO_METER , (5*VIEWPORT_HEIGHT/12)/PIXEL_TO_METER);
        score.draw(game.getBatch(), Integer.toString(GameModel.getInstance().getBot().getScore()),(VIEWPORT_WIDTH - VIEWPORT_WIDTH/8)/PIXEL_TO_METER, (7*VIEWPORT_HEIGHT/12)/PIXEL_TO_METER);

 
        if(GameController.getInstance().getPowerUp()!=null)
            if(GameController.getInstance().getPowerUp().getBody() != null)
                drawPowerUp();
            else if(GameController.getInstance().getPowerUp().getType() instanceof DuplicatePucks)
                drawDuplicate();

        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(GameController.getInstance().getWorld(), debugCamera);
        }
    }

    /**
     * Draw the duplicate puck to the screen if its corresponding power up is active.
     */
    private void drawDuplicate() {
        PuckModel duplicate = GameModel.getInstance().getDuplicate();

        EntityView view = ViewFactory.makeView(game, duplicate);
        view.update(duplicate);
        view.draw(game.getBatch());
    }

    /**
     * Draw the power up to the screen.
     */
    private void drawPowerUp() {

        PowerUpModel powerUp = GameModel.getInstance().getPowerUp();

        EntityView view = ViewFactory.makeView(game, powerUp);
        view.resize(powerUp);
        view.update(powerUp);
        view.draw(game.getBatch());
    }

    /**
     * Draw entities to the screen: puck, edges and handles.
     */
    private void drawEntities()
    {
        ArrayList<LineModel> lines = GameModel.getInstance().getEdges();
        EntityView view;

        for(LineModel line : lines)
        {
            view = ViewFactory.makeView(game, line);
            view.resize(line);
            view.update(line);
            view.draw(game.getBatch());
        }

        PuckModel puck = GameModel.getInstance().getPuck();
        BotModel bot = GameModel.getInstance().getBot();
        HandleModel handle = GameModel.getInstance().getHandle();

        view = ViewFactory.makeView(game, puck);
        view.resize(puck);
        view.update(puck);
        view.draw(game.getBatch());

        view = ViewFactory.makeView(game, bot);
        view.resize(bot);
        view.update(bot);
        view.draw(game.getBatch());

        view = ViewFactory.makeView(game, handle);
        view.resize(handle);
        view.update(handle);
        view.draw(game.getBatch());
    }

    /**
     * Checks if the game is over. In that case, resets the gamecontroller, stops the music and
     * sends a new result to the database.
     */
    public void checkGameOver()
    {
        if(GameController.getInstance().isGameOver())
        {
            game.changeScreen(AirHockeyMania.Screen.MAIN);
            bkg_music.stop();
            game.getDatabase().insert(GameModel.getInstance().getResult());
            GameController.getInstance().reset();
        }
    }

    /**
     * Returns this camera.
     * @return the camera.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Pauses the game, stopping the music and changing to the pause screen.
     */
    public void pauseGame() {
        bkg_music.stop();
        game.changeScreen(AirHockeyMania.Screen.PAUSE);
    }
}
