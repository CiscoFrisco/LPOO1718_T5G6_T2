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

        GameController.getInstance().getHandle().move(touchPoint.x, touchPoint.y);

        return true;
    }

}
