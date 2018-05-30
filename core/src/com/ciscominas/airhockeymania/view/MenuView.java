package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    protected Texture background;

    /**
     * Creates a MenuView object.
     * @param game the main game class
     */
    protected MenuView(AirHockeyMania game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = game.getAssetManager().get(MENU_SKIN);
        background = game.getAssetManager().get("menu.png");
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
        table.setDebug(true);
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

        Table table = createTable();

        setUpTable(table);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().end();

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
