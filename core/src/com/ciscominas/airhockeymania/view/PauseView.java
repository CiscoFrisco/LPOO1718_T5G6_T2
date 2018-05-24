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

public class PauseView extends ScreenAdapter {

    private final AirHockeyMania game;
    private Stage stage;
    private Skin skin;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Label titleLabel;

    public PauseView(AirHockeyMania game)
    {
        this.game = game;

        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());

        skin = game.getAssetManager().get("skin/glassy-ui.json"); // new

        musicOnOffLabel = new Label("Music", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);
        titleLabel = new Label("Pause", skin);
    }

    @Override
    public void show()
    {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);


        final TextButton resumeButton = new TextButton("Resume", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(3);
            }
        });


        final TextButton exitButton = new TextButton("Exit", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameController.getInstance().reset();
                game.changeScreen(0);
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        //music
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

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
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
