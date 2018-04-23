package com.ciscominas.airhockeymania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ciscominas.airhockeymania.screens.GameScreen;
import com.ciscominas.airhockeymania.screens.PreferencesScreen;
import com.ciscominas.airhockeymania.screens.MainMenu;
import com.ciscominas.airhockeymania.screens.Splash;
import com.ciscominas.airhockeymania.utils.GameAssetManager;

import java.util.ArrayList;

public class AirHockeyMania extends Game {
	private SpriteBatch batch;
	public GameAssetManager assetManager;

	private MainMenu mainMenu;
	private GameScreen gameScreen;
	private PreferencesScreen preferencesScreen;

	@Override
	public void create () {
		gameScreen = new GameScreen();
		assetManager = new GameAssetManager();
		mainMenu = new MainMenu(this);
		batch = new SpriteBatch();

		setScreen(mainMenu);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(96/255f, 176/255f, 244/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
		super.render();
	}

	public void changeScreen(int screen){
		switch(screen){
			case 0:
				if(mainMenu == null) mainMenu = new MainMenu(this);
				this.setScreen(mainMenu);
				break;
			case 1:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen();
				this.setScreen(preferencesScreen);
				break;
			case 2:
				if(gameScreen == null) gameScreen = new GameScreen();
				this.setScreen(gameScreen);
				break;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public GameAssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
