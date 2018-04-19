package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        // your touch down code here
        return true; // return true to indicate the event was handled
    }

    public boolean touchDragged (int x, int y, int pointer, int button) {
        // your touch down code here
        return true; // return true to indicate the event was handled
    }

}
