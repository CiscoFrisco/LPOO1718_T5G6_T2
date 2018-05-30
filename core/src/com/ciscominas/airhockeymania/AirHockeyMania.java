package com.ciscominas.airhockeymania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ciscominas.airhockeymania.database.Database;
import com.ciscominas.airhockeymania.view.GameView;
import com.ciscominas.airhockeymania.view.MainView;
import com.ciscominas.airhockeymania.view.PauseView;
import com.ciscominas.airhockeymania.view.PreferencesView;
import com.ciscominas.airhockeymania.view.ResultsView;

import static com.ciscominas.airhockeymania.utils.Constants.MENU_MUSIC;
import static com.ciscominas.airhockeymania.utils.Constants.MENU_SKIN;

/**
 * Main game class
 */
public class AirHockeyMania extends Game {

	/**
	 * Screen IDs
	 */
	public enum Screen {MAIN, PREFERENCES, RESULTS, GAME, PAUSE}


	/**
	 * Database to store game results
	 */
	private Database database;

	/**
	 * SpriteBatch to draw the elements
	 */
	private SpriteBatch batch;

	/**
	 * Manage sounds, music and textures
	 */
	private AssetManager assetManager;

	/**
	 * Main menu
	 */
	private MainView mainMenu;

	/**
	 * Game screen
	 */
	private GameView gameScreen;

	/**
	 * Preferences screen
	 */
	private PreferencesView preferencesScreen;

	/**
	 * Pause screen
	 */
	private PauseView pauseScreen;

	/**
	 * Results screen
	 */
	private ResultsView resultsScreen;

	/**
	 * Game preferences
	 */
	private AppPreferences preferences;

	/**
	 * Music to be played in the menus
	 */
	private Music menuMusic;

	/**
	 * Builds an AirHockeyMania object. Necessary here in order to correctly initialize the database
	 * according to the device.
	 * @param database desktop or android database
	 */
	public AirHockeyMania(Database database)
	{
		this.database = database;
	}

    /**
     * Initializes the main game assets, like preferences and assets, and sets the current menu
     * to the main menu.
     */
	@Override
	public void create () {
		preferences = new AppPreferences();
		assetManager = new AssetManager();
		batch = new SpriteBatch();

		loadMusic();
		menuMusic = assetManager.get(MENU_MUSIC);
		menuMusic.setLooping(true);

		assetManager.load("menu.png", Texture.class);

		SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/glassy-ui.atlas");
		assetManager.load(MENU_SKIN, Skin.class, params);
		assetManager.finishLoading();

		changeScreen(Screen.MAIN);
	}

    /**
     * Loads the menu music.
     */
	public void loadMusic()
	{
		assetManager.load(MENU_MUSIC, Music.class);
		assetManager.finishLoading();
	}

    /**
     * Changes the current screen to the desired one.
     * @param screen screen identifier
     */
	public void changeScreen(Screen screen){
		switch(screen){
			case MAIN:
				if(mainMenu == null) mainMenu = new MainView(this);
				this.setScreen(mainMenu);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesView(this);
				this.setScreen(preferencesScreen);
				break;
			case RESULTS:
				if(resultsScreen == null) resultsScreen = new ResultsView(this);
				this.setScreen(resultsScreen);
				break;
			case GAME:
				if(gameScreen == null) gameScreen = new GameView(this);
				this.setScreen(gameScreen);
				break;
			case PAUSE:
				if(pauseScreen == null) pauseScreen = new PauseView(this);
				this.setScreen(pauseScreen);
				break;
		}
	}

    /**
     * Disposes the game's assets.
     */
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    /**
     * Returns the asset manager.
     * @return the asset manager.
     */
	public AssetManager getAssetManager() {
		return assetManager;
	}

    /**
     * Returns the sprite batch.
     * @return the sprite batch.
     */
	public SpriteBatch getBatch() {
		return batch;
	}

    /**
     * Returns the game's preferences.
     * @return the game's preferences.
     */
	public AppPreferences getPreferences() {
		return preferences;
	}

    /**
     * Returns the game's database.
     * @return the game's database.
     */
	public Database getDatabase() {
		return database;
	}

    /**
     * Returns the menu music.
     * @return the menu music.
     */
	public Music getMenuMusic() {
		return menuMusic;
	}
}
