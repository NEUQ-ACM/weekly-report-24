import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{

        String url = "jdbc:mysql://localhost:3306/week2?useSSL=false";
        String username = "root";
        String password = "123456";

        //2、获取连接

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("成功连接");
        } catch (SQLException e) {
            System.out.println("连接失败");
            System.out.println(e);
        }

        String sql = "select count(*) from stu_manage";

        PreparedStatement ptmt = conn.prepareStatement(sql);

        ResultSet rs = ptmt.executeQuery();

        Scanner sc = new Scanner(System.in);
        List<Student> stu = new ArrayList<Student>();
        Student stud = new Student();
        int  option;
        while(true)
        {
            option=sc.nextInt();
            //1:增加  2:修改  3:查找  4:删除
            if(option==1)
            {
                System.out.println("请输入序号：");
                int idd = sc.nextInt();
                System.out.println("请输入姓名");
                String nam = sc.next();
                System.out.println("请输入性别");
                String gend = sc.next();
                System.out.println("请输入学号");
                String idcar = sc.next();
                System.out.println("请输入年龄");
                int ol = sc.nextInt();

                sql = "insert into stu_manage(id, name, gender, idcard, old) values(?,?,?,?,?)";

                ptmt = conn.prepareStatement(sql);

                ptmt.setInt(1, idd);
                ptmt.setString(2, nam);
                ptmt.setString(3, gend);
                ptmt.setString(4, idcar);
                ptmt.setInt(5, ol);

                int cou = ptmt.executeUpdate();
                if(cou>0)
                System.out.println("添加成功");
            }
            else if(option==2)
            {
                System.out.println("请输入需要修改信息的序号：");
                int xid = sc.nextInt();
                System.out.println("请输入序号：");
                int idd = sc.nextInt();
                System.out.println("请输入姓名");
                String nam = sc.next();
                System.out.println("请输入性别");
                String gend = sc.next();
                System.out.println("请输入学号");
                String idcar = sc.next();
                System.out.println("请输入年龄");
                int ol = sc.nextInt();

                sql = "update stu_manage set " +
                        "id = ?" +
                        "    name = ?" +
                        "    gender = ?" +
                        "    idcard = ?" +
                        "    old = ?" +
                        "where id = ?";

                ptmt = conn.prepareStatement(sql);

                ptmt.setInt(1,idd);
                ptmt.setString(2,nam);
                ptmt.setString(3,gend);
                ptmt.setString(4,idcar);
                ptmt.setInt(5,ol);
                ptmt.setInt(6,xid);

                int cou = ptmt.executeUpdate();

                if(cou>0)
                    System.out.println("修改成功");
                else
                    System.out.println("修改失败");
            }
            else if(option==3)
            {
                sql = "select * from stu_manage";

                ptmt = conn.prepareStatement(sql);

                rs = ptmt.executeQuery();

                while(rs.next())
                {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String gender = rs.getString(3);
                    String idcard = rs.getString(4);
                    int old = rs.getInt(5);

                    stud = new Student();
                    stud.setId(id);
                    stud.setName(name);
                    stud.setGender(gender);
                    stud.setIdcard(idcard);
                    stud.setOld(old);

                    stu.add(stud);
                }
                System.out.println(stu);
            }
            else if (option == 4)
            {
                int del = sc.nextInt();
                sql = "delete from stu_manage where id = ?";
                ptmt = conn.prepareStatement(sql);
                ptmt.setInt(1, del);
                int cou = ptmt.executeUpdate();
                if(cou>0)
                    System.out.println("删除成功");
                else
                    System.out.println("删除失败");
            }
        }
    }
}