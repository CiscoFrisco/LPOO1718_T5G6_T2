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
    private boolean active;

    @Override
    public void effect() {
        lastTouch = GameController.getInstance().getLastTouch();


        //aumentar baliza do bot
       if(lastTouch == "PLAYER")
        {
            //destruir linhas
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(0).getBody());
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(1).getBody());
            float goalWidth = 0, goalHeight = 0;
            short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;

            GameModel.getInstance().setEdge(new LineModel(0,0, goalWidth, goalHeight, ""),0);
            GameModel.getInstance().setEdge(new LineModel(0,0, goalWidth, goalHeight,""),1);

            ArrayList<LineModel> models =  GameModel.getInstance().getEdges();
            World world = GameController.getInstance().getWorld();

            GameController.getInstance().setLine(new LineBody(world, models.get(0), BodyDef.BodyType.StaticBody, mask), 0);
            GameController.getInstance().setLine(new LineBody(world, models.get(1), BodyDef.BodyType.StaticBody,  mask), 1);
        }
        else
        {
            //destuir linhas
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(2).getBody());
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(3).getBody());
            float goalWidth = 0, goalHeight = 0;
            short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;

            GameModel.getInstance().setEdge(new LineModel(0,0, goalWidth, goalHeight,""),2);
            GameModel.getInstance().setEdge(new LineModel(0,0, goalWidth, goalHeight,""),3);

            ArrayList<LineModel> models =  GameModel.getInstance().getEdges();
            World world = GameController.getInstance().getWorld();
            GameController.getInstance().setLine(new LineBody(world, models.get(2), BodyDef.BodyType.StaticBody, mask), 2);
            GameController.getInstance().setLine(new LineBody(world, models.get(3), BodyDef.BodyType.StaticBody, mask), 3);
        }

        active = true;
    }

    @Override
    public void reset() {

        float goalWidth = 0, goalHeight = 0;
        short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;

        if(lastTouch == "PLAYER")
        {
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(0).getBody());
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(1).getBody());
            ArrayList<LineModel> models =  GameModel.getInstance().getEdges();
            World world = GameController.getInstance().getWorld();

            GameModel.getInstance().setEdge(new LineModel(0,0, goalWidth, goalHeight,""),1);
            GameModel.getInstance().setEdge(new LineModel(0,0, goalWidth, goalHeight,""),2);

            GameController.getInstance().setLine(new LineBody(world, models.get(0), BodyDef.BodyType.StaticBody, mask), 0);
            GameController.getInstance().setLine(new LineBody(world, models.get(1), BodyDef.BodyType.StaticBody, mask), 1);
        }
        else
        {
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(2).getBody());
            WorldUtils.destroyBody(GameController.getInstance().getEdges().get(3).getBody());
            ArrayList<LineModel> models =  GameModel.getInstance().getEdges();
            World world = GameController.getInstance().getWorld();

            GameModel.getInstance().setEdge(new LineModel(0,0,goalWidth, goalHeight, ""),2);
            GameModel.getInstance().setEdge(new LineModel(0,0, goalWidth, goalHeight,""),3);

            GameController.getInstance().setLine(new LineBody(world, models.get(2), BodyDef.BodyType.StaticBody, mask), 2);
            GameController.getInstance().setLine(new LineBody(world, models.get(3), BodyDef.BodyType.StaticBody, mask), 3);
        }

        active = false;
    }

    @Override
    public boolean check() {
        return false;
    }
}
