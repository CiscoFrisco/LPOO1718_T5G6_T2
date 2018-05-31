package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.database.GameResult;

import java.util.ArrayList;

/**
 * The Results menu
 */
public class ResultsView extends MenuView {

    private Label titleLabel;
    private Label score1Label;
    private Label score2Label;
    private Label dateLabel;
    private TextButton backButton;

    /**
     * Creates a ResultsView object.
     * @param game the main game class
     */
    public ResultsView(AirHockeyMania game)
    {
        super(game);
    }

    /**
     * Sets up the labels (table headers)
     */
    private void setUpLabels()
    {
        titleLabel = new Label("Results", skin);
        score1Label = new Label("Player", skin);
        score2Label = new Label("Computer", skin);
        dateLabel = new Label("Date", skin);
    }

    /**
     * Sets up the table's elements: labels and back button.
     */
    protected void setUpElements()
    {
        setUpLabels();

        backButton = new TextButton("Back", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.Screen.MAIN);
            }
        });
    }

    /**
     * Sets up the table, adding the labels, and back button.
     * @param table the table
     */
    @Override
    protected void setUpTable(Table table)
    {
        table.add(titleLabel).colspan(3);
        table.row();
        table.add(score1Label).pad(0,0,0,5f);
        table.add(score2Label).pad(0,0,0,5f);
        table.add(dateLabel);
        table.row();

        ArrayList<GameResult> results = game.getDatabase().selectAll();

        for(GameResult result : results)
            addTableRow(table, result);

        table.add(backButton).colspan(3);
    }

    /**
     * Adds a row to the table, adding data about a game result.
     * @param table the table
     * @param result the game result
     */
    private void addTableRow(Table table, GameResult result)
    {
        Label score1 = new Label(Integer.toString(result.getScore1()), skin);
        Label score2 = new Label(Integer.toString(result.getScore2()), skin);
        Label date = new Label(result.getDate().toString(), skin);

        table.add(score1).pad(0,0,0,5f);;
        table.add(score2).pad(0,0,0,5f);;
        table.add(date);
        table.row();
    }
}
