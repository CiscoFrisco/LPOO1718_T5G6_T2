package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.box2d.UserData;
import com.ciscominas.airhockeymania.enums.UserDataType;

import java.util.Random;

public class BodyUtils {

    public static boolean bodyIsPuck(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.PUCK;
    }

    public static boolean bodyIsPowerUp(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.POWERUP;
    }

    public static boolean bodyIsHandle(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.HANDLE;
    }

    public static boolean bodyIsEdge(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.EDGE;
    }

    public static Vector2 randPosition(int lowX, int lowY, int highX , int highY)
    {
        Random random = new Random();
        int x = random.nextInt(highX) + lowX;
        int y = random.nextInt(highY) + lowY;

        return new Vector2(x,y);
    }

    public static boolean checkIntersection(Body b1, Body b2, double limit)
    {
        Vector2 pos1 = b1.getPosition();
        Vector2 pos2 = b2.getPosition();

        return Math.abs(pos1.x - pos2.x) <= limit && Math.abs(pos1.y - pos2.y) <= limit;
    }
}
