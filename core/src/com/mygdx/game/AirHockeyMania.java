package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AirHockeyMania extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;

	@Override
	public void create () {
	    assetManager = new AssetManager();
		batch = new SpriteBatch();

		setScreen(new Splash(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(96/255f, 176/255f, 244/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
