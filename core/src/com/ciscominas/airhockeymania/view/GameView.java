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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.AppPreferences;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.powerups.DuplicatePucks;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.*;
import com.ciscominas.airhockeymania.utils.Functions;
import com.ciscominas.airhockeymania.view.entities.EntityView;
import com.ciscominas.airhockeymania.view.entities.ViewFactory;

import java.util.ArrayList;

import static com.ciscominas.airhockeymania.utils.Constants.GRAPHICS_HEIGHT;
import static com.ciscominas.airhockeymania.utils.Constants.GRAPHICS_WIDTH;

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

    private final float PAUSE_WIDTH = GRAPHICS_WIDTH/8;

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
        camera = createCamera();

        loadAssets();

        GameController.getInstance().setSounds(game.getAssetManager());
        GameController.getInstance().setUpDimensions();

        setAssets();
    }

    /**
     * Sets backgorund music, pause texture, score font and background.
     */
    private void setAssets()
    {
        bkg_music = game.getAssetManager().get("bkg_music1.mp3");
        bkg_music.setLooping(true);

        pause = game.getAssetManager().get("pause.png");

        score = new BitmapFont();
        score.setColor(243/255f,12/255f,12/255f, 1);

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

        GameController controller = GameController.getInstance();
        AppPreferences preferences = game.getPreferences();

        controller.setBegin();

        Gdx.input.setInputProcessor(new InputHandler(this));

        controller.setBotDiff(preferences.getDifficulty());

        Functions.checkMusic(preferences, bkg_music);

        controller.setSound(preferences.getSoundVolume(), preferences.isSoundEffectsEnabled());
    }

    /**
     * Draw the entities to the screen, along with the score.
     * @param delta
     */
    public void render(float delta) {

        GameController controller = GameController.getInstance();
        SpriteBatch batch = game.getBatch();
        GameModel model = GameModel.getInstance();

        controller.update(delta);

        checkGameOver();

        Gdx.gl.glClearColor( 253/255f, 207/255f, 113/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        batch.begin();

        batch.draw(background,0,0, GRAPHICS_WIDTH, GRAPHICS_HEIGHT);

        batch.draw(pause, PAUSE_X,PAUSE_Y, PAUSE_WIDTH,PAUSE_WIDTH);

        drawEntities(controller, model, batch);

        drawScore(model, batch);

        batch.end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    /**
     * Draws the current score to the right side of the screen.
     * @param model the game's model instance
     * @param batch the main game's sprite batch
     */
    private void drawScore(GameModel model, SpriteBatch batch)
    {
        score.getData().setScale(2,2);
        score.draw(batch, Integer.toString(model.getHandle().getScore()),(VIEWPORT_WIDTH - VIEWPORT_WIDTH/8)/PIXEL_TO_METER , (5*VIEWPORT_HEIGHT/12)/PIXEL_TO_METER);
        score.draw(batch, Integer.toString(model.getBot().getScore()),(VIEWPORT_WIDTH - VIEWPORT_WIDTH/8)/PIXEL_TO_METER, (7*VIEWPORT_HEIGHT/12)/PIXEL_TO_METER);
    }

    /**
     * Draw entities to the screen: puck, edges and handles.
     * @param controller the game's controller instance
     * @param model the game's model instance
     * @param batch the main game's sprite batch
     */
    private void drawEntities(GameController controller, GameModel model, SpriteBatch batch)
    {
        ArrayList<LineModel> lines = model.getEdges();

        for(LineModel line : lines)
            drawEntity(line, batch);

        drawEntity(model.getPuck(), batch);
        drawEntity(model.getBot(), batch);
        drawEntity(model.getHandle(), batch);

        if(controller.getPowerUp()!=null)
            if(controller.getPowerUp().getBody() != null)
                drawEntity(model.getPowerUp(), batch);
            else if(controller.getPowerUp().getType() instanceof DuplicatePucks)
                drawEntity(model.getDuplicate(), batch);
    }

    /**
     * Draws an entity to the screen.
     * @param model the entity's model
     * @param batch the main game's sprite batch
     */
    private void drawEntity(EntityModel model, SpriteBatch batch)
    {
        EntityView view = ViewFactory.makeView(game, model);
        view.resize(model);
        view.update(model);
        view.draw(batch);
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
