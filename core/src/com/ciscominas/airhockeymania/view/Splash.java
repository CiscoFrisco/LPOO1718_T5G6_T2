package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.ciscominas.airhockeymania.AirHockeyMania;

public class Splash extends ScreenAdapter {

    Texture logo;
    AirHockeyMania game;
    BitmapFont font;
    private final int VIEWPORT_WIDTH = Gdx.graphics.getWidth();
    private final int VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

    public Splash(AirHockeyMania game)
    {
        this.game = game;
        game.getAssetManager().load("logo.png", Texture.class);
        game.getAssetManager().finishLoading();

        logo = game.getAssetManager().get("logo.png");
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(logo, 0, Gdx.graphics.getHeight()/3, VIEWPORT_WIDTH, VIEWPORT_HEIGHT/3);
        font.draw(game.getBatch(), "By zephyrminas and CiscoFrisco", VIEWPORT_WIDTH/4, VIEWPORT_HEIGHT/6);
        game.getBatch().end();

        if(Gdx.input.justTouched())
            game.changeScreen(AirHockeyMania.Screen.MAIN);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }


}
