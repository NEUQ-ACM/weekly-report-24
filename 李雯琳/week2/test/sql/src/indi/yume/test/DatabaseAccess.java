package indi.yume.test;

import java.sql.*;

public class DatabaseAccess implements AutoCloseable{
    private Connection connection;

    public void connect(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public ResultSet query(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
