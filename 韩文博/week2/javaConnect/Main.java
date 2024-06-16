
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //设置驱动，连接等
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/java_connect_sql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username="root";
        String password="okokook110";
        Statement statement = null;
        Connection  connection = DriverManager.getConnection(url,username,password);
        if(connection != null){
            System.out.println("数据库连接成功");
            statement = connection.createStatement();
            int n = 0;
            String sql = null;
            PreparedStatement preparedStatement = null;
            while(n!=5){
                System.out.println("数据库查询系统\n请输入对应序号：\n1-添加\n2-删除\n3-更改\n4-查询\n5-退出");
                n = scanner.nextInt();
                if(n==1){//添加
                    sql = "INSERT INTO student(student_number, name, sex, score, comment) values(?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(sql);
                    System.out.println("按次序分别输学号，姓名，性别，分数，备注");

                    String student_number = scanner.next();
                    String name = scanner.next();
                    String sex = scanner.next();
                    int score = scanner.nextInt();
                    String comment = scanner.next();

                    preparedStatement.setString(1,student_number);
                    preparedStatement.setString(2,name);
                    preparedStatement.setString(3,sex);
                    preparedStatement.setInt(4,score);
                    preparedStatement.setString(5,comment);

                    int rowAffected = preparedStatement.executeUpdate();
                    if(rowAffected>0){
                        System.out.println("插入成功");
                    }else{
                        System.out.println("插入失败");
                    }

                }else if(n==2){//删除
                    sql = "DELETE FROM student WHERE student_number = ? and name = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    System.out.println("输入需要删除的成绩的学生学号和姓名");
                    String studentNumber = scanner.next();
                    String name = scanner.next();
                    preparedStatement.setString(1,studentNumber);
                    preparedStatement.setString(2,name);

                    int rowAffected = preparedStatement.executeUpdate();
                    if(rowAffected>0){
                        System.out.println("删除成功");
                    }else{
                        System.out.println("删除失败，可能不存在该学生");
                    }
                }else if(n==3){//更改
                    sql = "UPDATE student set score = ?, comment = ? WHERE student_number = ? and name = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    System.out.println("输入需要修改的学生学号和姓名");
                    String studentNumber = scanner.next();
                    String name = scanner.next();
                    preparedStatement.setString(3,studentNumber);
                    preparedStatement.setString(4,name);

                    System.out.println("输入需要修改的成绩和备注");
                    int score = scanner.nextInt();
                    String comment = scanner.next();
                    preparedStatement.setInt(1,score);
                    preparedStatement.setString(2,comment);

                    int rowAffected = preparedStatement.executeUpdate();
                    if(rowAffected>0){
                        System.out.println("修改成功");
                    }else{
                        System.out.println("修改失败，可能不存在该学生");
                    }
                }else if(n==4){//查询

                    int queryNum = 0;
                    System.out.println("请输入数字：1代表查询全部，2代表查询某人");
                    queryNum = scanner.nextInt();

                    if(queryNum == 1){//查询全部
                        sql = "SELECT * FROM student";
                        ResultSet resultSet = statement.executeQuery(sql);

                        while (resultSet.next()){
                            System.out.print(resultSet.getInt(1) + "\t");
                            System.out.print(resultSet.getString(2) + "\t");
                            System.out.print(resultSet.getString(3) + "\t");
                            System.out.print(resultSet.getString(4) + "\t");
                            System.out.print(resultSet.getInt(5) + "\t");
                            System.out.print(resultSet.getString(6) + "\t");
                            System.out.println();
                        }
                    }else if(queryNum == 2){//查某项
                        sql = "SELECT * FROM student WHERE name = ?";
                        preparedStatement = connection.prepareStatement(sql);


                        System.out.println("请输入查询的姓名");
                        String name = scanner.next();
                        preparedStatement.setString(1,name);

                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()){
                            System.out.print(resultSet.getInt(1) + "\t");
                            System.out.print(resultSet.getString(2) + "\t");
                            System.out.print(resultSet.getString(3) + "\t");
                            System.out.print(resultSet.getString(4) + "\t");
                            System.out.print(resultSet.getInt(5) + "\t");
                            System.out.print(resultSet.getString(6) + "\t");
                            System.out.println();
                        }
                    }
                }else{
                    System.out.println("退出系统");
                }
            }
            statement.close();
            connection.close();
        }
        else{
            System.out.println("数据库连接失败");
        }
    }
}