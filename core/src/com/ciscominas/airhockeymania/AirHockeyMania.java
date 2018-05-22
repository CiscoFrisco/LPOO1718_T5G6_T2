package com.ciscominas.airhockeymania;

import com.badlogic.gdx.Application;
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
import com.ciscominas.airhockeymania.view.GameView;
import com.ciscominas.airhockeymania.view.MainView;
import com.ciscominas.airhockeymania.view.PreferencesView;

import java.util.ArrayList;

public class AirHockeyMania extends Game {
	private static final int MAIN_MENU = 0;
	private static final int PREFERENCES_MENU = 1;
	private static final int GAME_SCREEN = 2;


	private SpriteBatch batch;
	private BitmapFont font;
	private AssetManager assetManager;

	private MainView mainMenu;
	private GameView gameScreen;
	private PreferencesView preferencesScreen;

	private AppPreferences preferences;

	@Override
	public void create () {
		preferences = new AppPreferences();
		assetManager = new AssetManager();
		mainMenu = new MainView(this);
		batch = new SpriteBatch();
		font = new BitmapFont();

		changeScreen(MAIN_MENU);
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
			case MAIN_MENU:
				if(mainMenu == null) mainMenu = new MainView(this);
				this.setScreen(mainMenu);
				break;
			case PREFERENCES_MENU:
				if(preferencesScreen == null) preferencesScreen = new PreferencesView(this);
				this.setScreen(preferencesScreen);
				break;
			case GAME_SCREEN:
				if(gameScreen == null) gameScreen = new GameView(this);
				this.setScreen(gameScreen);
				break;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
		font.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AppPreferences getPreferences() {
		return preferences;
	}

	public BitmapFont getFont() {
		return font;
	}
}
