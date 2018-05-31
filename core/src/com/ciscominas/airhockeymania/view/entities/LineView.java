package com.ciscominas.airhockeymania.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.view.GameView;

import static com.ciscominas.airhockeymania.utils.Constants.EDGE_FILE;

/**
 * Represents an Edge on the screen.
 */
public class LineView extends EntityView {
    public LineView(AirHockeyMania game) {
        super(game);
    }

    @Override
    public Sprite createSprite(AirHockeyMania game) {
        Texture texture = game.getAssetManager().get(EDGE_FILE);

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void resize(EntityModel model) {
        sprite.setScale(model.getWidth()/(sprite.getWidth()* GameView.PIXEL_TO_METER), model.getHeight()/(sprite.getHeight()*GameView.PIXEL_TO_METER));
    }
}
