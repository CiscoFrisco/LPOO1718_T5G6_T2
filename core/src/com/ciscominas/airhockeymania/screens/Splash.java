package com.ciscominas.airhockeymania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ciscominas.airhockeymania.AirHockeyMania;

public class Splash extends ScreenAdapter {

    private AirHockeyMania myGame;
    private SpriteBatch spriteBatch;
    private Texture splash;

    public Splash(AirHockeyMania g) {
        myGame = g;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        splash = new Texture(Gdx.files.internal("air_hockey_splash.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(splash, 240, 852);
        spriteBatch.end();

        if (Gdx.input.justTouched())
            myGame.setScreen(new GameScreen(myGame));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
