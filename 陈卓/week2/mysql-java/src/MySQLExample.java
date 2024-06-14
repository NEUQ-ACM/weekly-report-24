import java.sql.*;

public class MySQLExample {

    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // 1. 注册MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 连接数据库
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // 3. 创建Statement对象
            Statement stmt = conn.createStatement();

            // 4. 执行增加数据操作
            String insertQuery = "INSERT INTO students (name, age) VALUES ('Alice', 20)";
            stmt.executeUpdate(insertQuery);
            System.out.println("数据添加成功！");

            // 5. 执行删除数据操作
            String deleteQuery = "DELETE FROM students WHERE id = 1";
            stmt.executeUpdate(deleteQuery);
            System.out.println("数据删除成功！");

            // 6. 执行修改数据操作
            String updateQuery = "UPDATE students SET age = 21 WHERE name = 'Alice'";
            stmt.executeUpdate(updateQuery);
            System.out.println("数据修改成功！");

            // 7. 执行查询数据操作
            String selectQuery = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }

            // 8. 关闭连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}