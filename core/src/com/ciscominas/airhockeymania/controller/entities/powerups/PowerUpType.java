package com.ciscominas.airhockeymania.controller.entities.powerups;

/**
 * Interface to be part of powerUp implementation
 */
public interface PowerUpType {
    /**
     * This function activates the powerUp.
     */
    void effect();

    /**
     * Resets the current powerUp by deleting its changes and updates its values back to their respective default values.
     */
    void reset();
}
