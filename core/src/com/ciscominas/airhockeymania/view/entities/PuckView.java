package com.ciscominas.airhockeymania.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.view.GameView;

public class PuckView extends EntityView {
    public PuckView(AirHockeyMania game) {
        super(game);
    }

    @Override
    public Sprite createSprite(AirHockeyMania game) {
        Texture texture = game.getAssetManager().get("puck.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void resize(EntityModel model) {
        sprite.setScale(model.getWidth()/((sprite.getWidth()/2)* GameView.PIXEL_TO_METER), model.getWidth()/((sprite.getWidth()/2)* GameView.PIXEL_TO_METER));
    }
}
