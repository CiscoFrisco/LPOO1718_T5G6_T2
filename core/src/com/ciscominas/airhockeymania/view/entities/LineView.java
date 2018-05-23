package com.ciscominas.airhockeymania.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ciscominas.airhockeymania.AirHockeyMania;

public class LineView extends EntityView {
    public LineView(AirHockeyMania game) {
        super(game);
    }

    @Override
    public Sprite createSprite(AirHockeyMania game) {
        Texture texture = game.getAssetManager().get("line.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
