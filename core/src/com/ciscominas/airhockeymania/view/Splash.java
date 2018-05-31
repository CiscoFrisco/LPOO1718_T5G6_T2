package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.ciscominas.airhockeymania.AirHockeyMania;


/**
 * The first screen of the game, presenting the logo and developers
 */
public class Splash extends ScreenAdapter {

    /**
     * Logo of the game
     */
    private Texture logo;

    /**
     * The main game class
     */
    private AirHockeyMania game;

    /**
     * Font to display the authors
     */
    private BitmapFont font;

    /**
     * File path for the logo
     */
    private final String LOGO = "logo.png";

    /**
     * Width of the specific device
     */
    public static final int GRAPHICS_WIDTH = Gdx.graphics.getWidth();

    /**
     * Height of the specific device
     */
    public static final int GRAPHICS_HEIGHT = Gdx.graphics.getHeight();

    /**
     * Creates a Splash screen, initializing the font and loading the logo.
     * @param game
     */
    public Splash(AirHockeyMania game)
    {
        this.game = game;
        font = new BitmapFont();
        loadLogo();
    }

    /**
     * Loads the logo from asset manager.
     */
    private void loadLogo()
    {
        AssetManager assetManager = game.getAssetManager();
        assetManager.load(LOGO, Texture.class);
        assetManager.finishLoading();
        logo = assetManager.get(LOGO);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(logo, 0, GRAPHICS_HEIGHT / 3, GRAPHICS_WIDTH, GRAPHICS_HEIGHT / 3);
        font.draw(game.getBatch(), "By zephyrminas and CiscoFrisco", GRAPHICS_WIDTH / 4, GRAPHICS_HEIGHT / 6);
        game.getBatch().end();

        if (Gdx.input.justTouched())
            game.changeScreen(AirHockeyMania.Screen.MAIN);
    }
}
