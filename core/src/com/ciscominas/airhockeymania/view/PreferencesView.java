package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.ciscominas.airhockeymania.AirHockeyMania;

/**
 * The Preferences Menu
 */
public class PreferencesView extends MenuView {

    /**
     * Identifies the difficulty selector
     */
    private Label difficultyLabel;

    /**
     * Identifies the title of the screen
     */
    private Label titleLabel;

    /**
     * Identifies the music volume slider
     */
    private Label volumeMusicLabel;

    /**
     * Identifies the sound volume slider
     */
    private Label volumeSoundLabel;

    /**
     * Identifies the music checkbox
     */
    private Label musicOnOffLabel;

    /**
     * Identifies the sound checkbox
     */
    private Label soundOnOffLabel;

    /**
     * Allows the user to change bot difficulty
     */
    private SelectBox<String> difficultySelect;

    /**
     * Allows the user to change the volume of the music
     */
    private Slider volumeMusicSlider;

    /**
     * Allows the user to change the volume of the sound
     */
    private Slider soundMusicSlider;

    /**
     * Allows the user to enable/disable music
     */
    private CheckBox musicCheckbox;

    /**
     * Allows the user to enable/disable sound effects
     */
    private CheckBox soundEffectsCheckbox;

    /**
     * Button that leads to the main menu
     */
    private TextButton backButton;


    /**
     * Creates a PreferencesView object.
     * @param game the main game class
     */
    public PreferencesView(AirHockeyMania game)
    {
        super(game);
    }

    /**
     * Sets up the labels.
     */
    private void setUpLabels()
    {
        difficultyLabel = new Label("SP Difficulty", skin);
        titleLabel = new Label("Preferences", skin);
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeSoundLabel = new Label("Sound Volume", skin);
        musicOnOffLabel = new Label("Music", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);
    }

    /**
     * Sets up the table's elements: labels, checkboxes and back button.
     */
    protected void setUpElements()
    {
        setUpLabels();

        difficultySelect = new SelectBox<String>(skin);
        String options[] = {"Easy","Medium", "Hard"};
        difficultySelect.setItems(options);
        difficultySelect.addListener(new EventListener() {
                                         @Override
                                         public boolean handle(Event event) {
                                             game.getPreferences().setDifficulty(difficultySelect.getSelected());
                                             return true;
                                         }
                                     });

        volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });

        musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        backButton = new TextButton("Back", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.Screen.MAIN);
            }
        });
    }

    /**
     * Sets the current values of the settings by fetching them from the game's preferences.
     */
    private void setValues()
    {
        difficultySelect.setSelected(game.getPreferences().getDifficulty());

        volumeMusicSlider.setValue(game.getPreferences().getMusicVolume());

        soundMusicSlider.setValue(game.getPreferences().getSoundVolume());

        musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());

        soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
    }

    /**
     * Sets up the table, adding the labels, checkboxes, selectbox and back button.
     * @param table the table
     */
    protected void setUpTable(Table table)
    {
        setValues();

        table.add(titleLabel).colspan(2);
        table.row().pad(10, 0, 0, 10);

        table.add(difficultyLabel).left();
        table.add(difficultySelect);
        table.row().pad(10, 0, 0, 10);

        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10, 0, 0, 10);

        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10, 0, 0, 10);

        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(10, 0, 0, 10);

        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10, 0, 0, 10);

        table.add(backButton).colspan(2);
    }
}
