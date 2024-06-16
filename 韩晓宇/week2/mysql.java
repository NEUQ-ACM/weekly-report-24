 import java.sql.*;
 
public class JDBCUtils {
    public static Connection getConn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        //mysql用户名
        String userName = "root";
        //mysql密码
        String password = "password";
        //数据库URL
        String url = "jdbc:mysql://localhost:3306/test";
 
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
 
 //添加
    public static void AddDatastu(String name1,String password1,String qqaccount1){
        Connection conn = getConn();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "insert into stu(stuname,password,qq) values ('"+name1+"','"+password1+"','"+qqaccount1+"');";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//删除
    public static void DeleteData(String table,String col,String name){
        Connection conn = getConn();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "DELETE FROM "+table + " WHERE " + col + "='" + name+"'";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//更新
    public static void UpdateData(String table, String column, String name, String col , String newdata) {
        Connection conn = getConn();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE "+ table + " SET " + col + " = '"+newdata + "' WHERE " + column + " = '"+name+"'";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//查询
    public static String GetData(String table,String column,String name,String col){
        Connection conn = getConn();
        String a = null;
 
        Statement stmt;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM "+table + " WHERE " + column + "='" + name+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                a=rs.getString(col);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
}
 
​
 
​