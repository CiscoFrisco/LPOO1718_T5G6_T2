package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.box2d.UserData;
import com.ciscominas.airhockeymania.enums.UserDataType;

public class BodyUtils {

    public static boolean bodyIsPuck(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.PUCK;
    }

    public static boolean bodyIsHandle(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.HANDLE;
    }

    public static boolean bodyIsEdge(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.EDGE;
    }
}
