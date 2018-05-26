package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.powerups.PowerUpType;
import com.ciscominas.airhockeymania.controller.entities.powerups.DuplicatePucks;
import com.ciscominas.airhockeymania.controller.entities.powerups.FreezeHandle;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperGoal;
import com.ciscominas.airhockeymania.controller.entities.powerups.SuperHandle;

import java.util.Random;

public class WorldUtils {
    
    public static PowerUpType randPowerUp()
    {
        Random random = new Random();
        int number = random.nextInt(4);
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
        body = null;
    }
}
