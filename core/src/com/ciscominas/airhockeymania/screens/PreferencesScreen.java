package com.ciscominas.airhockeymania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ciscominas.airhockeymania.AirHockeyMania;

public class PreferencesScreen extends ScreenAdapter
{
        private Skin skin;
        private AirHockeyMania myGame;
        private Stage stage;

        private Label difficultyLabel;
        private Label titleLabel;
        private Label volumeMusicLabel;
        private Label volumeSoundLabel;
        private Label musicOnOffLabel;
        private Label soundOnOffLabel;

        public PreferencesScreen(AirHockeyMania g)
        {
            myGame = g;

            /// create stage and set it as input processor
            stage = new Stage(new ScreenViewport());

           /* myGame.assetManager.queueAddSkin();  //new
            myGame.assetManager.manager.finishLoading(); // new
            skin = myGame.assetManager.manager.get("skin/glassy-ui.json"); // new*/
        }

        @Override
        public void show() {
            stage.clear();
// Create a table that fills the screen. Everything else will go inside this table.
            Gdx.input.setInputProcessor(stage);

// Create a table that fills the screen. Everything else will go inside this table.
            Table table = new Table();
            table.setFillParent(true);
            table.setDebug(true);
            stage.addActor(table);

            Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

            difficultyLabel = new Label("SP Difficulty", skin);
            titleLabel = new Label("Preferences", skin);
            volumeMusicLabel = new Label("Music Volume", skin);
            volumeSoundLabel = new Label("Sound Volume", skin);
            musicOnOffLabel = new Label("Music", skin);
            soundOnOffLabel = new Label("Sound Effect", skin);

            final SelectBox<String> difficultySelect = new SelectBox<String>(skin);
            String options[] = {"Easy","Medium", "Hard"};
            difficultySelect.setItems(options);
            difficultySelect.setSelected(options[2]);
            difficultySelect.addListener(new EventListener() {
                                             @Override
                                             public boolean handle(Event event) {
                                                 myGame.getPreferences().setDifficulty(difficultySelect.getSelected());
                                                 return true;
                                             }
                                         }

            );

            //volume
            final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
            volumeMusicSlider.setValue(myGame.getPreferences().getMusicVolume());
            volumeMusicSlider.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    myGame.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                    return false;
                }
            });

            //volume
            final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
            soundMusicSlider.setValue(myGame.getPreferences().getSoundVolume());
            soundMusicSlider.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    myGame.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                    return false;
                }
            });


            //music
            final CheckBox musicCheckbox = new CheckBox(null, skin);
            musicCheckbox.setChecked(myGame.getPreferences().isMusicEnabled());
            musicCheckbox.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    boolean enabled = musicCheckbox.isChecked();
                    myGame.getPreferences().setMusicEnabled(enabled);
                    return false;
                }
            });

            //music
            final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
            soundEffectsCheckbox.setChecked(myGame.getPreferences().isSoundEffectsEnabled());
            soundEffectsCheckbox.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    boolean enabled = soundEffectsCheckbox.isChecked();
                    myGame.getPreferences().setSoundEffectsEnabled(enabled);
                    return false;
                }
            });

            // return to main screen button
            final TextButton backButton = new TextButton("Back", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
            backButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    myGame.changeScreen(0);
                }
            });

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
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            stage.dispose();
        }
}
