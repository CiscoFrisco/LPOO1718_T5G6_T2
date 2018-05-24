package com.ciscominas.airhockeymania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ciscominas.airhockeymania.database.Database;
import com.ciscominas.airhockeymania.view.GameView;
import com.ciscominas.airhockeymania.view.MainView;
import com.ciscominas.airhockeymania.view.PauseView;
import com.ciscominas.airhockeymania.view.PreferencesView;
import com.ciscominas.airhockeymania.view.ResultsView;

public class AirHockeyMania extends Game {
	public static final int MAIN_MENU = 0;
	public static final int PREFERENCES_MENU = 1;
	public static final int RESULTS_SCREEN = 2;
	public static final int GAME_SCREEN = 3;
	public static final int PAUSE_SCREEN = 4;

	private Database database;
	private SpriteBatch batch;
	private BitmapFont font;
	private AssetManager assetManager;

	private MainView mainMenu;
	private GameView gameScreen;
	private PreferencesView preferencesScreen;
	private PauseView pauseScreen;
	private ResultsView resultsScreen;

	private Music menu_music;

	private AppPreferences preferences;

	public AirHockeyMania(Database database)
	{
		this.database = database;
	}

	@Override
	public void create () {
		preferences = new AppPreferences();
		assetManager = new AssetManager();
		mainMenu = new MainView(this);
		batch = new SpriteBatch();
		font = new BitmapFont();

		loadMusic();
		menu_music = assetManager.get("menu.mp3");
		menu_music.setLooping(true);

		changeScreen(MAIN_MENU);

	}

	public void loadMusic()
	{
		assetManager.load("menu.mp3", Music.class);
		assetManager.finishLoading();
	}

	public void changeScreen(int screen){
		switch(screen){
			case MAIN_MENU:
				if(mainMenu == null) mainMenu = new MainView(this);
				this.setScreen(mainMenu);
				menu_music.play();
				break;
			case PREFERENCES_MENU:
				if(preferencesScreen == null) preferencesScreen = new PreferencesView(this);
				this.setScreen(preferencesScreen);
				break;
			case RESULTS_SCREEN:
				if(resultsScreen == null) resultsScreen = new ResultsView(this);
				this.setScreen(resultsScreen);
				break;
			case GAME_SCREEN:
				if(gameScreen == null) gameScreen = new GameView(this);
				this.setScreen(gameScreen);
				menu_music.stop();
				break;
			case PAUSE_SCREEN:
				if(pauseScreen == null) pauseScreen = new PauseView(this);
				this.setScreen(pauseScreen);
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

	public Database getDatabase() {
		return database;
	}
}
