package com.ciscominas.airhockeymania.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu extends ScreenAdapter {

    private final Game myGame;
    private SpriteBatch spriteBatch;

    public MainMenu(Game g)
    {
        myGame = g;
    }

    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }
}
