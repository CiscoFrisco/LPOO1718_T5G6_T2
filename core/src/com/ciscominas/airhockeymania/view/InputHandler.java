package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.ciscominas.airhockeymania.controller.GameController;

public class InputHandler extends InputAdapter {

    private final GameView view;
    private Vector3 touchPoint;

    public InputHandler(GameView view)
    {
        this.view = view;
        touchPoint = new Vector3();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        view.getCamera().unproject(touchPoint.set(screenX, screenY, 0));

        float x = touchPoint.x*GameView.PIXEL_TO_METER;
        float y = touchPoint.y*GameView.PIXEL_TO_METER;

        if(checkLimits(x,y))
            GameController.getInstance().getHandle().move(x, y);

        return true;
    }

    public boolean checkLimits(float x, float y)
    {
        if(x <14.25f && x > 1.75f && y > 1.75f && y < GameController.ARENA_HEIGHT/2 - 0.75f)
            return true;

        return false;
    }

}
