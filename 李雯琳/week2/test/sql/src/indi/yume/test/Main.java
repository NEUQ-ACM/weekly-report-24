package indi.yume.test;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String dbPath = "resources/test.db";
        try (DatabaseAccess dbAccess = new DatabaseAccess()){
            dbAccess.connect("jdbc:sqlite:" + dbPath);
            ResultSet rs = dbAccess.query("SELECT * FROM test_table;");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
