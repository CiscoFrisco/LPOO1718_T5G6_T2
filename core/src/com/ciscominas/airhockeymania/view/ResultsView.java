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


public class ResultsView extends MenuView {

    private Label titleLabel;
    private Label score1Label;
    private Label score2Label;
    private Label dateLabel;
    private TextButton backButton;

    public ResultsView(AirHockeyMania game)
    {
        super(game);
    }

    private void setUpLabels()
    {
        titleLabel = new Label("Results", skin);
        score1Label = new Label("Player", skin);
        score2Label = new Label("Computer", skin);
        dateLabel = new Label("Date", skin);
    }

    protected void setUpElements()
    {
        setUpLabels();

        backButton = new TextButton("Back", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.MAIN_MENU);
            }
        });
    }


    @Override
    protected void setUpTable(Table table)
    {
        table.add(titleLabel).colspan(3);
        table.row();
        table.add(score1Label);
        table.add(score2Label);
        table.add(dateLabel);
        table.row();

        ArrayList<GameResult> results = game.getDatabase().selectAll();

        for(GameResult result : results)
            addTableRow(table, result);

        table.add(backButton).colspan(3);
    }

    private void addTableRow(Table table, GameResult result)
    {
        Label score1 = new Label(Integer.toString(result.getScore1()), skin);
        Label score2 = new Label(Integer.toString(result.getScore2()), skin);
        Label date = new Label(result.getDate().toString(), skin);

        table.add(score1);
        table.add(score2);
        table.add(date);
        table.row();
    }
}
