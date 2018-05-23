package com.ciscominas.airhockeymania.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ciscominas.airhockeymania.AirHockeyMania;

class BotView extends EntityView {
    public BotView(AirHockeyMania game) {
        super(game);
    }

    @Override
    public Sprite createSprite(AirHockeyMania game) {

        Texture texture = game.getAssetManager().get("puck.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());

    }
}
