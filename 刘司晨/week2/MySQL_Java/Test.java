
import java.sql.*;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //加载驱动，抛出ClassNotFound的异常
        Class.forName("com.mysql.jdbc.Driver");

        //连接数据库 1）url/主机地址 2）用户名 3）密码
        //背过后面的一串参数【?useUnicode=true&characterEncoding=utf8&useSSL=true】
        String url = "jdbc:mysql://localhost:3306/owenliu";
        String username = "root";
        String password = "owen0409";
        Connection connection = DriverManager.getConnection(url, username, password); //获取数据库对象

        //获取执行SQL的对象
        Statement statement = connection.createStatement();

        //statement去执行SQL
        String sql = "SELECT * FROM users";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {  //游标遍历
            System.out.print("id ="+ resultSet.getObject("id")+"  ");
            System.out.print("姓名 ="+ resultSet.getObject("NAME")+"  ");
            System.out.print("密码 ="+ resultSet.getObject("PASSWORD")+"  ");
            System.out.print("邮箱 ="+ resultSet.getObject("email")+"  ");
            System.out.println("生日 ="+ resultSet.getObject("birthday")+"  ");
        }
        //释放连接
        resultSet.close();
        statement.close();
        connection.close();

        Connection connection = null;
        Statement statement =null;
        ResultSet resultSet = null;
        int num = 0;
        try {
            //建立数据库连接
            connection = jdbcUtils.getConnection();
            //执行sql
            statement = connection.createStatement();
            //String sql = "SELECT * FROM users";
            String sql = "UPDATE users SET `NAME`='1月29' WHERE `id`=1";
            num = statement.executeUpdate(sql);
            if(num>0){
                System.out.println("更新成功！");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {//关闭资源必须写在finally里
            //断开数据库连接，释放资源
            jdbcUtils.closeConnection(connection,statement,resultSet);
        }

    }
}