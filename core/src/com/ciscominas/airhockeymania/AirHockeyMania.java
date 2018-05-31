package com.ciscominas.airhockeymania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ciscominas.airhockeymania.database.Database;
import com.ciscominas.airhockeymania.view.GameView;
import com.ciscominas.airhockeymania.view.MainView;
import com.ciscominas.airhockeymania.view.PauseView;
import com.ciscominas.airhockeymania.view.PreferencesView;
import com.ciscominas.airhockeymania.view.ResultsView;
import com.ciscominas.airhockeymania.view.Splash;

import java.util.HashMap;


/**
 * Main game class
 */
public class AirHockeyMania extends Game {

    /**
	 * Screen IDs
	 */
	public enum Screen {SPLASH, MAIN, PREFERENCES, RESULTS, GAME, PAUSE}

	private Screen currentScreen;

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

	private HashMap<Screen, ScreenAdapter> screens;

	/**
	 * Game preferences
	 */
	private AppPreferences preferences;

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

		setUpScreens();
	}

	private void setUpScreens() {
		screens = new HashMap<Screen, ScreenAdapter>();
		screens.put(Screen.SPLASH, new Splash(this));
		screens.put(Screen.MAIN, new MainView(this));
		screens.put(Screen.PREFERENCES, new PreferencesView(this));
		screens.put(Screen.RESULTS, new ResultsView(this));
		screens.put(Screen.GAME, new GameView(this));
		screens.put(Screen.PAUSE, new PauseView(this));

		setScreen(screens.get(Screen.SPLASH));
	}


    /**
     * Changes the current screen to the desired one.
     * @param screen screen identifier
     */
	public void changeScreen(Screen screen){
	    currentScreen = screen;
		setScreen(screens.get(screen));
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
	 * Returns the ID of the current screen of the game.
	 * @return the ID of the current screen of the game.
	 */
	public Screen getCurrentScreen() {
		return currentScreen;
	}
}
