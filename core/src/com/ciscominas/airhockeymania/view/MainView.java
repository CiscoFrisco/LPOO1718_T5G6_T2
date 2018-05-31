package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.ciscominas.airhockeymania.AirHockeyMania;

/**
 * Main menu.
 */
public class MainView extends MenuView {

    /**
     * New game button, leading to Game screen
     */
    private TextButton newGame;

    /**
     * Button leading to Preferences screen
     */
    private TextButton preferences;

    /**
     * Button that exits the game
     */
    private TextButton exit;

    /**
     * Button leading to the results screen
     */
    private TextButton results;

    /**
     * Creates a MainView object.
     * @param game the main game class
     */
    public MainView(AirHockeyMania game) {
        super(game);
    }

    /**
     * Sets up the table elements: newGame, preferences, results and exit buttons.
     */
    protected void setUpElements()
    {
        newGame = new TextButton("New Game", skin);
        preferences = new TextButton("Preferences", skin);
        results = new TextButton("Results", skin);
        exit = new TextButton("Exit", skin);

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.Screen.GAME);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.Screen.PREFERENCES);
            }
        });

        results.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.Screen.RESULTS);
            }
        });
    }

    /**
     * Sets up the table, adding the buttons to it in a formatted manner.
     * @param table the table
     */
    @Override
    protected void setUpTable(Table table)
    {
        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferences).fillX().uniformX();
        table.row().pad(0, 0, 10, 0);
        table.add(results).fillX().uniformX();
        table.row().pad(0, 0, 0, 0);
        table.add(exit).fillX().uniformX();
    }
}
