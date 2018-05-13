package com.ciscominas.airhockeymania.box2d;

import com.ciscominas.airhockeymania.enums.UserDataType;

public class PowerUpUserData extends UserData {

    private boolean isFlaggedForRemoval;

    public PowerUpUserData()
    {
        super();
        userDataType = UserDataType.POWERUP;
        isFlaggedForRemoval = false;
    }

    public boolean isFlaggedForRemoval() {
        return isFlaggedForRemoval;
    }

    public void setFlaggedForRemoval(boolean flag)
    {
        isFlaggedForRemoval = flag;
    }
}
