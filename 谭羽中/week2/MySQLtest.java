import java.sql.*;
import java.util.Scanner;

public class MySQLtest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            System.out.println(e);
        }

        Connection connection = null;
        Statement sql = null;
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "19283746-+";
        ResultSet rs;

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功");
        }catch (SQLException e){
            System.out.println("连接失败");
            System.out.println(e);
        }

        int operation = 0;
        while(true) {
            System.out.println("查询：1 添加：2 删除：3 退出：4");
            Scanner reader = new Scanner(System.in);
            //reader.hasNextInt();
            operation = reader.nextInt();
            if(operation == 1) {
                try {
                    sql = connection.createStatement();
                    rs = sql.executeQuery("SELECT * FROM student");
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        int age = rs.getInt(3);
                        System.out.printf("%d, %s, %d\n", id, name, age);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }

            } else if (operation == 2) {
                System.out.println("请输入id");
                int id = reader.nextInt();
                System.out.println("请输入昵称");
                reader.nextLine();
                String nickname = reader.nextLine();
                System.out.println("请输入年龄");
                int age = reader.nextInt();
                String addRecord = STR."insert into student values(\{id},'\{nickname}', \{age})";
                try {
                    int ok = sql.executeUpdate(addRecord);
                }catch (SQLException e){
                    System.out.println(e);
                }
            } else if (operation == 3) {
                System.out.println("请输入id");
                int id = reader.nextInt();
                String deleteRecord = STR."delete from student where id = \{id}";
                try {
                    int ok = sql.executeUpdate(deleteRecord);
                }catch (SQLException e){
                    System.out.println(e);
                }
            } else if (operation == 4) {
                connection.close();
                break;
            }
        }
    }
}




