package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Random;

public class BodyUtils {

    public static Vector2 randPosition(int lowX, int lowY, int highX , int highY)
    {
        Random random = new Random();
        int x = random.nextInt(highX) + lowX;
        int y = random.nextInt(highY) + lowY;

        return new Vector2(x,y);
    }

    public static float randNumber(float low, float high)
    {
        Random random = new Random();
        return low + random.nextFloat() * (high - low);
    }

    public static boolean checkIntersection(Body b1, Body b2, double limit)
    {
        Vector2 pos1 = b1.getPosition();
        Vector2 pos2 = b2.getPosition();

        return Math.abs(pos1.x - pos2.x) <= limit && Math.abs(pos1.y - pos2.y) <= limit;
    }
}
