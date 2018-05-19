package com.ciscominas.airhockeymania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.stages.GameStage;

public class GameScreen extends ScreenAdapter {


    private GameStage stage;
    private AirHockeyMania myGame;

    @Override
    public void show() {
        stage.setDifficulty(myGame.getPreferences().getDifficulty());
        Gdx.input.setInputProcessor(stage);
    }

    public GameScreen(AirHockeyMania game)
    {
        stage = new GameStage(game.getPreferences().getDifficulty());
        myGame = game;
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
        stage.draw();
        stage.act(delta);

        if(stage.isGameOver())
        {
            myGame.changeScreen(0);
            stage.reset();
        }

        myGame.batch.begin();
        myGame.font.draw(myGame.batch, stage.getScore(), 100, 150);
        myGame.batch.end();
    }
}