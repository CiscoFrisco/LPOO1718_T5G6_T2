package com.ciscominas.airhockeymania.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.AppPreferences;
import com.ciscominas.airhockeymania.controller.GameController;
import com.ciscominas.airhockeymania.controller.entities.powerups.*;

import java.util.Random;

/**
 * Useful function. Mainly generate random stuff, like numbers, vectors positions, and powerups.
 */
public class Functions {

    /**
     * Generate a random position between the given limits.
     * @param lowX Lower x-coordinate, in meters
     * @param lowY Lower y-coordinate, in meters
     * @param highX Higher x-coordinate, in meters
     * @param highY Higher y-coordinate, in meters
     * @return a random position stored in a Vector2
     */
    public static Vector2 randPosition(int lowX, int lowY, int highX , int highY)
    {
        Random random = new Random();
        int x = random.nextInt(highX) + lowX;
        int y = random.nextInt(highY) + lowY;

        return new Vector2(x,y);
    }

    /**
     * Generate a random number (float) between the given limits.
     * @param low lower limit
     * @param high higher limit
     * @return a random number
     */
    public static float randNumber(float low, float high)
    {
        Random random = new Random();
        return low + random.nextFloat() * (high - low);
    }

    /**
     * Checks if the given bodies intersect (have similar positions up to a given limit)
     * @param b1 first body
     * @param b2 second body
     * @param limit limit distance
     * @return true if they intersect, false otherwise
     */
    public static boolean checkIntersection(Body b1, Body b2, double limit)
    {
        Vector2 pos1 = b1.getPosition();
        Vector2 pos2 = b2.getPosition();

        return Math.abs(pos1.x - pos2.x) <= limit && Math.abs(pos1.y - pos2.y) <= limit;
    }

    /**
     * Generate a random power up type.
     * @return a random power up type.
     */
    public static PowerUpType randPowerUp()
    {
        int number = (int) randNumber(0,4);
        PowerUpType powerUp = null;
        switch(number)
        {
            case 0:
                powerUp = new SuperGoal();
                break;
            case 1:
                powerUp = new SuperHandle();
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

    /**
     * Destroy a body from the world, and delete its user data.
     * @param body the body to be destroyed.
     */
    public static void destroyBody(Body body)
    {
        GameController.getInstance().getWorld().destroyBody(body);
        body.setUserData(null);
    }

    /**
     * Checks if music playback is enabled, if so plays the given music with volume
     * set by the user (or default).
     * Else, stops.
     * @param preferences game's preferences
     * @param music music to play
     */
    public static void checkMusic(AppPreferences preferences, Music music)
    {
        if(preferences.isMusicEnabled())
        {
            music.setVolume(preferences.getMusicVolume());
            music.play();
        }
        else
            music.stop();
    }

    /**
     * Returns the distance between two vectors
     * @param vector1
     * @param vector2
     * @return the distance between two vectors
     */
    public static float getDistance(Vector2 vector1, Vector2 vector2){

        return new Vector2(vector2.x - vector1.x, vector2.y - vector1.y).len();
    }
}
