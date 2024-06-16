import java.sql.*;

public class ToolOfTableEmp {
    String dataname,url,user,password;
    Connection connection;
    public void setInformation(){
        url="jdbc:mysql://localhost:3306/test";
        user="root";
        System.out.println("请输入您的用户密码");
        password=Main.scanner.nextLine();
    }

    public void connect(){
        boolean success=false;
        while(!success)
        {
            setInformation();
            try{
                connection= DriverManager.getConnection(url,user,password);
                success=true;
            }
            catch(SQLException e){
                System.out.println("信息输入错误请重新输入信息");
            }
        }
        System.out.println("连接成功！");
    }

    public void show() throws SQLException {
        PreparedStatement statement= connection.prepareStatement("select * from emp");
        ResultSet resultSet= statement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("id")
                    +" "+resultSet.getString("name")
                    +" "+resultSet.getString("gender")
                    +" "+resultSet.getString("age")
                    +" "+resultSet.getString("idcard"));
        }
    }

    public void update() throws SQLException{
        PreparedStatement statement= connection.prepareStatement("update emp set name=?,gender=?,age=? where id=?");
        System.out.println("请输入修改对象的id");
        statement.setObject(4,Main.scanner.nextInt());
        String name,gender;
        int age;
        System.out.println("请输入修改后的姓名");
        name=Main.scanner.nextLine();
        statement.setObject(1,name);
        Main.scanner.next();
        System.out.println("请输入修改后的性别");
        gender=Main.scanner.nextLine();
        statement.setObject(2,gender);
        System.out.println("请输入修改后的年龄");
        Main.scanner.next();
        age=Main.scanner.nextInt();
        statement.setObject(3,age);
        statement.executeUpdate();
    }

    public void delete() throws SQLException{
        Main.scanner.next();
        PreparedStatement statement= connection.prepareStatement("delete from emp where id=?");
        System.out.println("请输入你要删除的对象的id");
        statement.setObject(1,Main.scanner.nextInt());
        statement.executeUpdate();
    }

}