package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;

public class PauseView extends MenuView {

    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Label titleLabel;
    private TextButton resumeButton;
    private TextButton exitButton;
    private CheckBox musicCheckbox;
    private CheckBox soundEffectsCheckbox;

    public PauseView(AirHockeyMania game)
    {
        super(game);
    }

    @Override
    protected void setUpElements() {
        musicOnOffLabel = new Label("Music", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);
        titleLabel = new Label("Pause", skin);

        resumeButton = new TextButton("Resume", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(3);
            }
        });


        exitButton = new TextButton("Exit", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameController.getInstance().reset();
                game.changeScreen(0);
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

        //music
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

    private void setValues()
    {
        soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
        musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());
    }
}
