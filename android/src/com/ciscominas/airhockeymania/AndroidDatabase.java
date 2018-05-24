package com.ciscominas.airhockeymania;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ciscominas.airhockeymania.database.Database;
import com.ciscominas.airhockeymania.database.GameResult;

import java.util.ArrayList;

import static com.ciscominas.airhockeymania.utils.Constants.DATABASE;
import static com.ciscominas.airhockeymania.utils.Constants.DATE_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.ID_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.LIMIT;
import static com.ciscominas.airhockeymania.utils.Constants.RESULTS_TABLE;
import static com.ciscominas.airhockeymania.utils.Constants.SCORE1_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.SCORE1_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.SCORE1_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.SCORE1_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.SCORE2_COLUMN;

public class AndroidDatabase extends SQLiteOpenHelper implements Database{

    public AndroidDatabase(Context context)
    {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + RESULTS_TABLE + "(\n" + "	" + ID_COLUMN + " integer PRIMARY KEY,\n"
                        + "	" + SCORE1_COLUMN + " integer NOT NULL,\n" + "	" + SCORE2_COLUMN + " integer NOT NULL,\n" + "	" + DATE_COLUMN + " integer NOT NULL\n" + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RESULTS_TABLE);
        onCreate(db);
    }


    @Override
    public ArrayList<GameResult> selectAll() {
        ArrayList<GameResult> results = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + RESULTS_TABLE + " ORDER BY date DESC LIMIT " + LIMIT , null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            results.add(new GameResult(res.getInt(res.getColumnIndex(SCORE1_COLUMN)),
                    res.getInt(res.getColumnIndex(SCORE2_COLUMN)),
                    res.getLong(res.getColumnIndex(DATE_COLUMN))));
            res.moveToNext();
        }

        return results;
    }

    @Override
    public void insert(GameResult result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCORE1_COLUMN, result.getScore1());
        contentValues.put(SCORE2_COLUMN, result.getScore2());
        contentValues.put(DATE_COLUMN, result.getDateMillis());
        db.insert(RESULTS_TABLE, null, contentValues);
    }
}
