package com.ciscominas.airhockeymania.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Database {

    static final String database = "results.db";
    static final String table = "results";

    public Database()
    {
        createNewTable();
    }

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + database;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + database;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS results (\n" + "	id integer PRIMARY KEY,\n"
                + "	score1 integer NOT NULL,\n" + "	score2 integer NOT NULL,\n" + " date date NOT NULL\n" + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("createTable: " + e.getMessage());
        }
    }

    public void insert(GameResult result) {
        String sql = "INSERT INTO results(score1,score2, date) VALUES(?,?,?)";

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

    public ArrayList<GameResult> selectAll() {

        ArrayList<GameResult> results = new ArrayList<GameResult>();

        String sql = "SELECT score1, score2, date FROM results";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                results.add(new GameResult(rs.getInt("score1"), rs.getInt("score2"), rs.getDate("date")));
            }
        } catch (SQLException e) {
            System.out.println("select:" + e.getMessage());
        }

        return results;
    }

    public void showResults(ArrayList<GameResult> results)
    {
        for(GameResult result : results)
        {
            System.out.println(result.getScore1() + " - " + result.getScore2() + " " + result.getDate());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM results WHERE id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
