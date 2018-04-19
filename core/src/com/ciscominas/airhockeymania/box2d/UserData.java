package com.ciscominas.airhockeymania.box2d;

import com.ciscominas.airhockeymania.enums.UserDataType;

public abstract class UserData {

    protected UserDataType userDataType;

    public UserData()
    {

    }

    public UserDataType getUserDataType() {
        return userDataType;
    }
}
