package com.ciscominas.airhockeymania.database;

import java.util.ArrayList;

public interface Database {

   ArrayList<GameResult> selectAll();

   void insert(GameResult result);

}
