package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {

    public final AssetManager manager = new AssetManager();

    public final String skin = "skin/glassy-ui.json";
    public final String hit = "hit.wav";

    public void queueAddSkin(){
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/glassy-ui.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueAddSounds(){
        manager.load(hit, Sound.class);
    }
}
