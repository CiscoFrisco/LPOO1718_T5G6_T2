package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.powerups.DuplicatePucks;
import com.ciscominas.airhockeymania.controller.entities.powerups.FreezeHandle;
import com.ciscominas.airhockeymania.controller.entities.powerups.PowerUpType;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperGoal;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperHandle;

import java.util.Random;

public class Functions {

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

    public static PowerUpType randPowerUp()
    {
        int number = (int) randNumber(0,4);
        PowerUpType powerUp = null;
        switch(number)
        {
            case 0:
                powerUp = new SuperHandle();
                break;
            case 1:
                powerUp = new SuperGoal();
                break;
            case 2:
                powerUp = new FreezeHandle();
                break;
            case 3:
                powerUp = new DuplicatePucks();
                break;
        }

        return powerUp;
    }

    public static void destroyBody(Body body)
    {
        GameController.getInstance().getWorld().destroyBody(body);
        body.setUserData(null);
    }
}
