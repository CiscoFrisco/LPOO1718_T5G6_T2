package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;

/**
 * The Pause Menu
 */
public class PauseView extends MenuView {

    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Label titleLabel;
    private TextButton resumeButton;
    private TextButton exitButton;
    private CheckBox musicCheckbox;
    private CheckBox soundEffectsCheckbox;

    /**
     * Creates a PauseView object
     * @param game the main game class
     */
    public PauseView(AirHockeyMania game)
    {
        super(game);
    }

    /**
     * Sets up the table elements: music and sound effects checkboxes, resume and exit buttons.
     */
    @Override
    protected void setUpElements() {
        musicOnOffLabel = new Label("Music", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);
        titleLabel = new Label("Pause", skin);

        resumeButton = new TextButton("Resume", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AirHockeyMania.Screen.GAME);
            }
        });


        exitButton = new TextButton("Exit", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameController.getInstance().reset();
                game.changeScreen(AirHockeyMania.Screen.MAIN);
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
    }

    /**
     * Sets up the table, adding the buttons to it in a formatted manner.
     * @param table the table
     */
    @Override
    protected void setUpTable(Table table) {

        setValues();

        table.add(titleLabel).colspan(2);
        table.row();
        table.add(soundOnOffLabel);
        table.add(soundEffectsCheckbox);
        table.row();
        table.add(musicOnOffLabel);
        table.add(musicCheckbox);
        table.row();
        table.add(resumeButton);
        table.row();
        table.add(exitButton);
    }

    /**
     * Sets the current values of the sound effects and music checkboxes, by fetching them
     * from the game preferences.
     */
    private void setValues()
    {
        soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
        musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());
    }
}
