package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ciscominas.airhockeymania.AirHockeyMania;

public class MainView extends MenuView {

    private TextButton newGame;
    private TextButton preferences;
    private TextButton exit;
    private TextButton results;


    public MainView(AirHockeyMania game) {
        super(game);
        }

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
                game.changeScreen(AirHockeyMania.GAME_SCREEN);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.PREFERENCES_MENU);
            }
        });

        results.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.RESULTS_SCREEN);
            }
        });
    }


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

    private void checkMusic()
    {
        if(game.getPreferences().isMusicEnabled())
        {
            game.getMenuMusic().setVolume(game.getPreferences().getMusicVolume());
            game.getMenuMusic().play();
        }
        else
            game.getMenuMusic().stop();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        checkMusic();
    }

}
