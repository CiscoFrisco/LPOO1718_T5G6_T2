package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.ciscominas.airhockeymania.controller.GameController;

import static com.ciscominas.airhockeymania.view.GameView.PIXEL_TO_METER;

public class InputHandler extends InputAdapter {

    private final GameView view;
    private Vector3 touchPoint;
    private float x;
    private float y;

    public InputHandler(GameView view)
    {
        this.view = view;
        touchPoint = new Vector3();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        convertCoordinates(screenX, screenY);

        if(checkLimits(x,y))
            GameController.getInstance().getHandle().move(x, y);

        return true;
    }

    private void convertCoordinates(int screenX, int screenY)
    {
        view.getCamera().unproject(touchPoint.set(screenX, screenY, 0));

        x = touchPoint.x* PIXEL_TO_METER;
        y = touchPoint.y* PIXEL_TO_METER;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        convertCoordinates(screenX, screenY);

        if(checkPause(x,y))
            view.pauseGame();

        return true;

    }

    private boolean checkPause(float x, float y) {
        return x/PIXEL_TO_METER>=view.PAUSE_X && y/PIXEL_TO_METER>=view.PAUSE_Y;
    }

    public boolean checkLimits(float x, float y)
    {
        return x <14.25f && x > 1.75f && y > 1.75f && y < GameController.ARENA_HEIGHT/2 - 0.75f;
    }

}
