package com.ciscominas.airhockeymania.database;

import com.ciscominas.airhockeymania.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;

import static com.ciscominas.airhockeymania.utils.Constants.DATABASE;
import static com.ciscominas.airhockeymania.utils.Constants.DATE_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.ID_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.LIMIT;
import static com.ciscominas.airhockeymania.utils.Constants.RESULTS_TABLE;
import static com.ciscominas.airhockeymania.utils.Constants.SCORE1_COLUMN;
import static com.ciscominas.airhockeymania.utils.Constants.SCORE2_COLUMN;

/**
 * Implements a database for the desktop version of the game, using jdbc library for SQLite.
 */
public class DesktopDatabase implements Database{

    /**
     * Creates a DesktopDatabase object and a new results table, if it doesn't exit already.
     */
    public DesktopDatabase()
    {
        createNewTable();
    }

    /**
     * Creates a connection to the database.
     * @return a database connection.
     */
    private static Connection connect() {
        String url = "jdbc:sqlite:" + DATABASE;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Creates a new results table, if it doesn't already exist.
     * The table will have the following columns: id, player score, bot score, and date, all stored as integers.
     */
    public void createNewTable() {
        String url = "jdbc:sqlite:" + DATABASE;

        String sql = "CREATE TABLE IF NOT EXISTS " + RESULTS_TABLE + "(\n" + "	" + ID_COLUMN + " integer PRIMARY KEY,\n"
                + "	" + SCORE1_COLUMN + " integer NOT NULL,\n" + "	" + SCORE2_COLUMN + " integer NOT NULL,\n" + "	" + DATE_COLUMN + " integer NOT NULL\n" + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("createTable: " + e.getMessage());
        }
    }

    /**
     * Inserts a result onto the results table.
     * @param result Result of a game, containing scores and date.
     */
    public void insert(GameResult result) {
        String sql = "INSERT INTO " +  RESULTS_TABLE + "(" + SCORE1_COLUMN +"," +
                SCORE2_COLUMN + "," + DATE_COLUMN +") VALUES(?,?,?)";

        try  {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, result.getScore1());
            pstmt.setInt(2, result.getScore2());
            pstmt.setDate(3, result.getDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert:" + e.getMessage());
        }
    }

    /**
     * Connects to the database and selects the latest results, ordered by date.
     * A limit can be specified through the constants class.
     *
     * @return an ArrayList containing the latest results.
     */
    public ArrayList<GameResult> selectAll() {

        ArrayList<GameResult> results = new ArrayList<GameResult>();

        String sql = "SELECT * FROM " + RESULTS_TABLE + " ORDER BY date DESC LIMIT " + LIMIT;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
                results.add(new GameResult(rs.getInt(SCORE1_COLUMN), rs.getInt(SCORE2_COLUMN), rs.getLong(DATE_COLUMN)));
        } catch (SQLException e) {
            System.out.println("select:" + e.getMessage());
        }

        return results;
    }

    /**
     * Eliminates the last added row from the results table.
     */
    public void pop() {

        String sql = "DELETE FROM " + Constants.RESULTS_TABLE + " WHERE id = (SELECT MAX(id) FROM " +  Constants.RESULTS_TABLE + ")";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
