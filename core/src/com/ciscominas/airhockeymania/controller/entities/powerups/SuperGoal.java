package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.EntityBody;
import com.ciscominas.airhockeymania.controller.entities.LineBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.utils.Functions;

import java.util.ArrayList;

/**
 * Represents a powerUpType that increases a player's goal size.
 */
public class SuperGoal implements PowerUpType {
    /**
     * Refers to who was the last to touch the puck. This entity is referred as the powerUp owner.
     */
    private EntityModel.ModelType lastTouch;

    /**
     * Resize ratio applied once the powerUp is activated.
     */
    private static final float EFFECT_RATIO = 0.5f;
    /**
     * Resize ratio applied once the powerUp is deactivated.
     */
    private static final float RESET_RATIO = 2f;
    /**
     * Mask used to detect collision with the edges since this powerUp will create new Edges.
     */
    private static final short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;

    /**
     * This powerUp has the purpose of harming the player that wasn't the last to touch the puck.
     * According to the lastTouch attribute this function will increase the bot's goal size or the player's goal size.
     */
    @Override
    public void effect() {
        lastTouch = GameModel.getInstance().getLastTouch();

       if(lastTouch == EntityModel.ModelType.HANDLE)
            effectEdges(2,3, EFFECT_RATIO);
        else
            effectEdges(0,1, EFFECT_RATIO);
    }

    /**
     * Resizes bot or player goal back to it's default size according to the lastTouch attribute.
     */
    @Override
    public void reset() {

        if(lastTouch == EntityModel.ModelType.HANDLE)
            effectEdges(2,3, RESET_RATIO);
        else
            effectEdges(0,1, RESET_RATIO);
    }

    /**
     * Resizes both edges of the goal accoridng to a certain ratio.
     * @param which1 Edge of the goal.
     * @param which2 Edge of the goal.
     * @param ratio Ratio by which the current size will be multiplied.
     */
    private void effectEdges(int which1, int which2, float ratio)
    {
        Functions.destroyBody(GameController.getInstance().getEdges().get(which1).getBody());
        Functions.destroyBody(GameController.getInstance().getEdges().get(which2).getBody());

        GameModel.getInstance().setEdge(ratio, which1);
        GameModel.getInstance().setEdge(ratio, which2);

        ArrayList<LineModel> models =  GameModel.getInstance().getEdges();
        World world = GameController.getInstance().getWorld();
        GameController.getInstance().setLine(new LineBody(world, models.get(which1), BodyDef.BodyType.StaticBody, mask), which1);
        GameController.getInstance().setLine(new LineBody(world, models.get(which2), BodyDef.BodyType.StaticBody, mask), which2);
    }
}
