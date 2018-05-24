package com.ciscominas.airhockeymania.database;

import java.sql.Date;

public class GameResult {

    private int score1;
    private int score2;
    private long dateMillis;

    public GameResult(int score1, int score2, long date) {
        this.score1 = score1;
        this.score2 = score2;
        this.dateMillis = date;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public Date getDate() {
        return new Date(dateMillis);
    }

    public long getDateMillis(){
        return dateMillis;
    }

}
