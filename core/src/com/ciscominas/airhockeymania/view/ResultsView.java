package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.database.GameResult;

import java.util.ArrayList;

public class ResultsView extends ScreenAdapter {

    private final Label titleLabel;
    private final Label score1Label;
    private final Label score2Label;
    private final Label dateLabel;

    private Skin skin;
    private AirHockeyMania myGame;
    private Stage stage;

    public ResultsView(AirHockeyMania game)
    {
        myGame = game;

        stage = new Stage(new ScreenViewport());

        skin = myGame.getAssetManager().get("skin/glassy-ui.json"); // new

        titleLabel = new Label("Results", skin);
        score1Label = new Label("Score1", skin);
        score2Label = new Label("Score2", skin);
        dateLabel = new Label("Date", skin);
    }

    @Override
    public void show()
    {
        ArrayList<GameResult> results = myGame.getDatabase().selectAll();

        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        table.add(titleLabel);
        table.row();
        table.add(score1Label);
        table.add(score2Label);
        table.add(dateLabel);
        table.row();


        final TextButton backButton = new TextButton("Back", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myGame.changeScreen(0);
            }
        });

        for(GameResult result : results)
        {
            Label score1 = new Label(Integer.toString(result.getScore1()), skin);
            Label score2 = new Label(Integer.toString(result.getScore2()), skin);
            Label date = new Label(result.getDate().toString(), skin);

            table.add(score1);
            table.add(score2);
            table.add(date);
            table.row();
        }

        table.add(backButton);
    }

    @Override
    public void render(float delta) {
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
