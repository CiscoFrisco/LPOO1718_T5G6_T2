package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ciscominas.airhockeymania.AirHockeyMania;

import static com.ciscominas.airhockeymania.utils.Constants.MENU_SKIN;

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
     * The background image
     */
    protected Image background;

    protected static final float PIXEL_TO_METER = 0.04f;

    protected static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth()*PIXEL_TO_METER;

    protected static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight()*PIXEL_TO_METER;

    protected static final float DEFAULT_BUTTON_SIZE = VIEWPORT_WIDTH / 2;

    protected static final float BUTTON_WIDTH = VIEWPORT_WIDTH / 2;

    protected float BUTTON_EDGE = VIEWPORT_WIDTH/ 24;

    protected float FONT_SIZE = VIEWPORT_WIDTH / 16;


    /**
     * Creates a MenuView object.
     * @param game the main game class
     */
    protected MenuView(AirHockeyMania game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = game.getAssetManager().get(MENU_SKIN);
        background = new Image(game.getAssetManager().get("menu.png", Texture.class));
        background.setScale(Gdx.graphics.getWidth() / background.getWidth(), Gdx.graphics.getHeight() / background.getHeight());

        setUpElements();
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
}
