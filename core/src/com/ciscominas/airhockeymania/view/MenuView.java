package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ciscominas.airhockeymania.AirHockeyMania;

import static com.ciscominas.airhockeymania.utils.Constants.MENU_SKIN;

public abstract class MenuView extends ScreenAdapter {

    protected final AirHockeyMania game;
    protected Stage stage;
    protected Skin skin;

    protected MenuView(AirHockeyMania game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = game.getAssetManager().get(MENU_SKIN);

        setUpElements();
    }

    protected Table createTable()
    {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        return table;
    }

    protected abstract void setUpElements();

    protected abstract void setUpTable(Table table);

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
