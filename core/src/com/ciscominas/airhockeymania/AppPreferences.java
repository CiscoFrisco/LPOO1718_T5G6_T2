package com.ciscominas.airhockeymania;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.utils.Constants;

/**
 * Provides access to current settings of the game, which include music, sound and bot difficulty.
 * These settings are stored in a local file, thus maintaining their state upon game restart.
 */
public class AppPreferences {

    /**
     * Identifier for the music volume slider setting
     */
    private static final String PREF_MUSIC_VOLUME = "volume";

    /**
     * Identifier for the music checkbox setting
     */
    private static final String PREF_MUSIC_ENABLED = "music.enabled";

    /**
     * Identifier for the sound checkbox setting
     */
    private static final String PREF_SOUND_ENABLED = "sound.enabled";

    /**
     * Identifier for the sound volume slider setting
     */
    private static final String PREF_SOUND_VOL = "sound";

    /**
     * Identifier for the game's preferences file
     */
    private static final String PREFS_NAME = "AirHockeyMania";

    /**
     * Identifier for the bot difficulty dropdown list setting
     */
    private static final String PREF_DIFFICULTY = "difficulty";

    /**
     * Returns the game's preferences
     * @return the game's preferences
     */
    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    /**
     * Checks if the sound effects are enabled.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    /**
     * Activates or deactivates sound according to the parameter.
     * @param soundEffectsEnabled new value
     */
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    /**
     * Checks if the music is enabled.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    /**
     * Activates or deactivates music according to the parameter.
     * @param musicEnabled new value
     */
    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    /**
     * Returns the music volume
     * @return the music volume
     */
    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    /**
     * Sets the music volume
     * @param volume new value, between 0 and 1.
     */
    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    /**
     * Returns the sound volume
     * @return the sound volume
     */
    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    /**
     * Sets the sound volume
     * @param volume new value, between 0 and 1.
     */
    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    /**
     * Sets the bot difficulty
     * @param difficulty the new difficulty
     */
    public void setDifficulty(String difficulty) {
        getPrefs().putString(PREF_DIFFICULTY, difficulty);
        getPrefs().flush();
    }

    /**
     * Returns the bot's difficulty
     * @return the bot's difficulty
     */
    public String getDifficulty() {
        return getPrefs().getString(PREF_DIFFICULTY, Constants.DEFAULT_DIFFICULTY);
    }
}
