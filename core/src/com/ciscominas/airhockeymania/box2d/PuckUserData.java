package com.ciscominas.airhockeymania.box2d;

import com.ciscominas.airhockeymania.enums.UserDataType;

public class PuckUserData extends UserData {

    private int wallBounce;

    public PuckUserData()
    {
        super();
        userDataType = UserDataType.PUCK;
        wallBounce = 0;
    }

    public void resetWallBounce() {
        wallBounce = 0;
    }

    public int getWallBounce() {
        return wallBounce;
    }

    public void incWallBounce()
    {
        wallBounce++;
    }
}
