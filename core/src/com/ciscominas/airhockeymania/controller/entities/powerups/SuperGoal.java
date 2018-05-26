package com.ciscominas.airhockeymania.controller.entities.powerups;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.EntityBody;
import com.ciscominas.airhockeymania.controller.entities.LineBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.utils.WorldUtils;

import java.util.ArrayList;

public class SuperGoal implements PowerUpType {
    private String lastTouch;
    private static final float EFFECT_RATIO = 0.5f;
    private static final float RESET_RATIO = 2f;
    private static final short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;


    @Override
    public void effect() {
        lastTouch = GameController.getInstance().getLastTouch();

       if(lastTouch == "PLAYER")
            effectEdges(2,3, EFFECT_RATIO);
        else
            effectEdges(0,1, EFFECT_RATIO);
    }

    @Override
    public void reset() {

        if(lastTouch == "PLAYER")
            effectEdges(2,3, RESET_RATIO);
        else
            effectEdges(0,1, RESET_RATIO);
    }

    private void effectEdges(int which1, int which2, float ratio)
    {
        WorldUtils.destroyBody(GameController.getInstance().getEdges().get(which1).getBody());
        WorldUtils.destroyBody(GameController.getInstance().getEdges().get(which2).getBody());

        GameModel.getInstance().setEdge(ratio, which1);
        GameModel.getInstance().setEdge(ratio, which2);

        ArrayList<LineModel> models =  GameModel.getInstance().getEdges();
        World world = GameController.getInstance().getWorld();
        GameController.getInstance().setLine(new LineBody(world, models.get(which1), BodyDef.BodyType.StaticBody, mask), which1);
        GameController.getInstance().setLine(new LineBody(world, models.get(which2), BodyDef.BodyType.StaticBody, mask), which2);
    }

    @Override
    public boolean check() {
        return false;
    }
}
