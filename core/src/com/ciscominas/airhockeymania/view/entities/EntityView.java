package com.ciscominas.airhockeymania.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.view.GameView;

import static com.ciscominas.airhockeymania.view.GameView.PIXEL_TO_METER;

public abstract class EntityView {
    Sprite sprite;

    EntityView(AirHockeyMania game) {
        sprite = createSprite(game);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public abstract Sprite createSprite(AirHockeyMania game);

    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
    }

    public abstract void resize(EntityModel model);
}
