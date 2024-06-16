import java.sql.*;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "liuzuyi2004";
        Connection connection = DriverManager.getConnection(url, username, password);
        //判断
        if(connection == null){
            System.out.println("连接失败");
        }else{
            System.out.println("连接成功");

            Statement statement = connection.createStatement();

            String sql = "insert into student values(3,'小李','男');";
            //String sql = "delete from student where id = 1;";  删除//
            //String sql = "update student set name = '小张' where id = 1;";  修改//


            if (statement.executeUpdate(sql)>=1){
                System.out.println("更新成功");
            }else{
                System.out.println("更新失败");
            }

            String sql2 = "select * from student;";
            ResultSet resultSet = statement.executeQuery(sql2);
            while (resultSet.next()) {
                System.out.print(resultSet.getString("name") + "\t");
                System.out.print(resultSet.getString("sex"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        }

    }
}