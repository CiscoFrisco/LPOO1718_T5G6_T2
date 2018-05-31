package com.ciscominas.airhockeymania.database;

import java.util.ArrayList;

/**
 * To be implemented by classes that represent a database in Desktop or Android.
 */
public interface Database {

    /**
     * Select the latest results from the table and return them in an ArrayList.
     *
     * @return an ArrayList containing the last results stored on the table.
     */
    ArrayList<GameResult> selectAll();

    /**
     * Insert a row onto a results table, from a result object.
     *
     * @param result Result of a game, containing scores and date.
     */
    void insert(GameResult result);
}
