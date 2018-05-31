package com.ciscominas.airhockeymania.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

import static com.ciscominas.airhockeymania.view.GameView.PIXEL_TO_METER;

/**
 * Represents an entity on the screen
 */
public abstract class EntityView {

    /**
     * This view's sprite, containing its texture
     */
    Sprite sprite;

    /**
     * Creates an EntityView object, setting up its sprite
     * @param game the main game class
     */
    EntityView(AirHockeyMania game) {
        sprite = createSprite(game);
    }

    /**
     * Draws this sprite to the game's batch
     * @param batch the main game's sprite batch
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public abstract Sprite createSprite(AirHockeyMania game);

    /**
     * Updates the sprite's center, according to the corresponding model's coordinates.
     * @param model the respective entity model
     */
    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
    }

    public abstract void resize(EntityModel model);
}
