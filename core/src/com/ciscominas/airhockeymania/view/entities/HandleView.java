package com.ciscominas.airhockeymania.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

public class HandleView extends EntityView {
    public HandleView(AirHockeyMania game) {
        super(game);
    }

    @Override
    public Sprite createSprite(AirHockeyMania game) {
        Texture texture = game.getAssetManager().get("puck.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void resize(EntityModel model) {
        sprite.setScale(model.getWidth(), model.getWidth());

    }
}
