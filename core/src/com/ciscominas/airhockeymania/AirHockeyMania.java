package com.ciscominas.airhockeymania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ciscominas.airhockeymania.screens.GameScreen;
import com.ciscominas.airhockeymania.screens.PreferencesScreen;
import com.ciscominas.airhockeymania.screens.MainMenu;
import com.ciscominas.airhockeymania.screens.Splash;
import com.ciscominas.airhockeymania.utils.GameAssetManager;

import java.util.ArrayList;

public class AirHockeyMania extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public GameAssetManager assetManager;

	private MainMenu mainMenu;
	private GameScreen gameScreen;
	private PreferencesScreen preferencesScreen;

	private AppPreferences preferences;

	@Override
	public void create () {
		preferences = new AppPreferences();
		gameScreen = new GameScreen(this);
		assetManager = new GameAssetManager();
		mainMenu = new MainMenu(this);
		batch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(mainMenu);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
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
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case 2:
				if(gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.manager.dispose();
	}

	public GameAssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AppPreferences getPreferences() {
		return preferences;
	}
}
