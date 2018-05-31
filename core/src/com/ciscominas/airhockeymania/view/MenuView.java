package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.utils.Functions;

/**
 * Represents a Menu.
 */
public abstract class MenuView extends ScreenAdapter {

    /**
     * The main game
     */
    protected final AirHockeyMania game;

    /**
     * The stage that will handle input
     */
    protected Stage stage;

    /**
     * The skin for the table
     */
    protected Skin skin;

    /**
     * Music to be played in the menus
     */
    protected Music menuMusic;

    /**
     * The background image
     */
    protected Image background;

    /**
     * The menu's background file path
     */
    protected static final String MENU = "menu.png";

    /**
     * The menu's skin file path
     */
    protected static final String MENU_SKIN = "skin/glassy-ui.json";

    protected static final String MENU_MUSIC = "menu.mp3";

    /**
     * Creates a MenuView object.
     * @param game the main game class
     */
    protected MenuView(AirHockeyMania game) {

        this.game = game;
        stage = new Stage(new ScreenViewport());

        AssetManager manager = game.getAssetManager();

        loadMusic(manager);

        loadSkin(manager);

        setBackground(manager);

        setUpElements();
    }

    /**
     * Loads the menu's table skin.
     * @param manager the game's asset manager
     */
    private void loadSkin(AssetManager manager) {

        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/glassy-ui.atlas");
        manager.load(MENU_SKIN, Skin.class, params);
        manager.finishLoading();

        skin = manager.get(MENU_SKIN);
    }

    /**
     * Sets the menu's background, by fetching it from the asset manager and scaling it to the screen.
     * @param manager the game's asset manager
     */
    private void setBackground(AssetManager manager) {

        manager.load(MENU, Texture.class);
        manager.finishLoading();

        background = new Image(manager.get(MENU, Texture.class));
        background.setScale(Gdx.graphics.getWidth() / background.getWidth(), Gdx.graphics.getHeight() / background.getHeight());
    }

    /**
     * Creates a table to be added to the stage.
     * @return a table
     */
    protected Table createTable()
    {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        return table;
    }


    /**
     * Sets up the elements that compose the table.
     */
    protected abstract void setUpElements();

    /**
     * Adds the elements to the table.
     * @param table the table
     */
    protected abstract void setUpTable(Table table);

    /**
     * Clears the stage and sets up the table.
     */
    @Override
    public void show()
    {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);

        Table table = createTable();

        setUpTable(table);
        Functions.checkMusic(game.getPreferences(), menuMusic);
    }

    @Override
    public void hide() {
        super.hide();
        if(game.getCurrentScreen() == AirHockeyMania.Screen.GAME)
            menuMusic.stop();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Loads the menu's music
     * @param manager the game's asset manager
     */
    public void loadMusic(AssetManager manager)
    {
        manager.load(MENU_MUSIC, Music.class);
        manager.finishLoading();

        menuMusic = manager.get(MENU_MUSIC);
        menuMusic.setLooping(true);
    }
}
