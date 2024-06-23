import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "user";
        String password = "passwordaaa";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to the database!");


                try (Statement stmt = conn.createStatement()) {

                    String sql = "SELECT * FROM your_table"; // 替换为你的表名
                    try (ResultSet rs = stmt.executeQuery(sql)) {

                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String name = rs.getString("name");


                            System.out.print("ID: " + id);
                            System.out.print(", Name: " + name);
                            System.out.println();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}