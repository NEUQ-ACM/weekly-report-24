import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcDemo {
    public static void main(String[] args) {
        // URL 用户名 密码
        String url = "http://localhost:8080/students";
        String user = "kashark";
        String password = "ly124111zyp726910";

        // 加载JDBC驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("500");
            }
            else {
                Statement stmt = conn.createStatement();
                // 内容
                ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int studentId = rs.getInt("student_id");
                    String name = rs.getString("name");
                    String gender = rs.getString("gender");
                    Date birthday = rs.getInt("birthday");
                    int enrollmentYear = rs.getInt("enrollment_year");
                    String college = rs.getString("college");
                    String major = rs.getString("major");
                    String clazz = rs.getString("class");
                    int clazzId = rs.getInt("class_id");
                    System.out.println("Id: " + id + ", StudentId: " + studentId+ ", Name: " + name
                            + ", Gender: " + gender+ ", Birthday: " + birthday+ ", EnrollmentYear: " + enrollmentYear+
                            ", College: " + college + ", Major: " + major+ ", Class: " + clazz+ ", ClassId: " + clazzId);
                }

                // 结束
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
