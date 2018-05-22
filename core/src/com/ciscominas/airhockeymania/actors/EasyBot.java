package com.ciscominas.airhockeymania.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.stages.GameStage;

public class EasyBot extends Bot {
    public EasyBot(Body body) {
        super(body);
    }

    @Override
    public void move(GameStage game) {

    }

    @Override
    public void attack(Body puck) {

    }

    @Override
    public void defend(Body puck, Vector2 final_pos) {

    }
}
