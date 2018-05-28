package com.ciscominas.airhockeymania.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.model.GameModel;

import static com.ciscominas.airhockeymania.view.GameView.PIXEL_TO_METER;

/**
 * Handles user input
 */
public class InputHandler extends InputAdapter {

    private final GameView view;
    private Vector3 touchPoint;
    private float x;
    private float y;

    /**
     * Creates an InputHandler object for the view
     * @param view the game screen
     */
    public InputHandler(GameView view)
    {
        this.view = view;
        touchPoint = new Vector3();
    }

    /**
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @return true because it is implemented
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        convertCoordinates(screenX, screenY);

        if(checkLimits(x,y))
            GameController.getInstance().getHandle().move(x, y);

        return true;
    }

    /**
     * Converts screen to world coordinates.
     * @param screenX
     * @param screenY
     */
    private void convertCoordinates(int screenX, int screenY)
    {
        view.getCamera().unproject(touchPoint.set(screenX, screenY, 0));

        x = touchPoint.x* PIXEL_TO_METER;
        y = touchPoint.y* PIXEL_TO_METER;
    }

    /**
     * Checks if the user pressed the pause button, thus making the game view change to the pause view.
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return true because it is implemented
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(checkPause(screenX,screenY))
            view.pauseGame();

        return true;

    }

    /**
     * Checks if the mouse or touch coordinates are on the pause button.
     * @param x x-coordinate on the screen
     * @param y y-coordinate on the screen
     * @return true or false according to the touch position.
     */
    private boolean checkPause(float x, float y) {
        return x>=view.PAUSE_X && y>=view.PAUSE_Y;
    }

    /**
     * Checks if the user cursor is inside his half of the screen.
     * @param x x-coordinate in meters
     * @param y y-coordinate in meters
     * @return
     */
    public boolean checkLimits(float x, float y)
    {
        return x <GameController.ARENA_WIDTH - GameModel.getInstance().getHandle().getWidth() && x > GameModel.getInstance().getHandle().getWidth() && y > GameModel.getInstance().getHandle().getWidth() && y < GameController.ARENA_HEIGHT/2 - GameModel.getInstance().getHandle().getWidth();
    }

}
