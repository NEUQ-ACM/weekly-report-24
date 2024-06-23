package info.zpss.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class JDBCDemo {
    static String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test";   // jdbc_test为数据库名
    static String username = "root";   // 数据库用户名
    static String password = "doudou050813";   // 数据库密码

    private static class Product {  // 产品POJO
        private int id;
        private String name;
        private double price;

        public Product() {
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // 加载MySQL驱动程序(新版MySQL驱动程序可省略此步骤)
            Class.forName("com.alibaba.druid.pool.DruidDataSource");    // 加载Druid数据库连接池
            System.out.println("成功加载MySQL驱动程序");
        } catch (ClassNotFoundException e) {
            System.err.println("找不到驱动程序类，加载驱动失败！");
            e.printStackTrace();
        }
        testIntro();
        testTransAction();
        testDML();
        testDDL();
        testDQL();
        testPOJO();
        testInject();
        testPrepStmt();
        testDataSource();
    }

    // 测试JDBC的基本使用
    private static void testIntro() {
        System.out.println("----------testIntro()函数开始执行----------");
        String sql = "UPDATE `product` SET `price` = 1000 WHERE `id` = 1";  // SQL语句
        Connection conn;    // 数据库连接
        Statement stmt;     // 数据库操作
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功");
            stmt = conn.createStatement();
            int count = stmt.executeUpdate(sql);    // 执行SQL语句，返回受影响的行数，若失败则抛出异常SQLException
            System.out.println("SQL语句：\"" + sql + "\"已执行！");
            System.out.println("受影响的行数：" + count);
            stmt.close();
            conn.close();
            System.out.println("数据库连接已关闭");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("----------testIntro()函数结束执行----------");
    }

    // 测试事务
    private static void testTransAction() {
        System.out.println("----------testTransAction()函数开始执行----------");
        String[] sql_arr = {
                "UPDATE `product` SET `price` = 1000 WHERE `id` = 1",
                "UPDATE `product` SET `price` = 500 WHERE `id` = 2",
                "UPDATE `product` SET `price` = 800 WHERE `id` = 3",
                "UPDATE `product` SET `price` = 600 WHERE `id` = 4",
                "UPDATE `product` SET `price` = 500 WHERE `id` = 5",
        };  // 依次执行数组中的SQL语句，若有一条SQL语句执行失败，则回滚事务

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功");
            conn.setAutoCommit(false);  // 设置手动提交，开启事务
            Statement stmt = conn.createStatement();
            try {
                System.out.println("开始执行事务");
                for (String sql : sql_arr) {
                    int count = stmt.executeUpdate(sql);    // 执行SQL语句，若失败则抛出异常
                    System.out.println("SQL语句：\"" + sql + "\" 影响了" + count + "行");
                }
                conn.commit();      // 若全部SQL语句执行成功，则提交事务
                System.out.println("全部SQL语句执行成功，事务已提交！");
            } catch (SQLException e) {
                conn.rollback();    // 若有SQL语句执行失败，则回滚事务
                System.out.println("有SQL语句执行失败，事务已回滚！");
                e.printStackTrace();
            }
            stmt.close();
            conn.close();
            System.out.println("数据库连接已关闭");
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
        }
        System.out.println("----------testTransAction()函数结束执行----------");
    }

    // 测试DML语句 (Data Manipulation Language)
    private static void testDML() {
        System.out.println("----------testDML()函数开始执行----------");
        String sql = "UPDATE `product` SET `price` = 500 WHERE `id` = 2";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功");
            try (Statement stmt = conn.createStatement()) {
                int count = stmt.executeUpdate(sql);
                System.out.println("执行DML语句：\"" + sql + "\"");
                if (count > 0)
                    System.out.println("DML语句执行成功！");
                else
                    System.out.println("DML语句执行失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn.close();
            System.out.println("数据库连接已关闭");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("----------testDML()函数结束执行----------");
    }

    // 测试DDL语句 (Data Definition Language)
    private static void testDDL() {
        System.out.println("----------testDDL()函数开始执行----------");
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功");
            try (Statement stmt = conn.createStatement()) {
                String sql_1 = "CREATE DATABASE IF NOT EXISTS `test`";
                int count_1 = stmt.executeUpdate(sql_1);
                System.out.println("执行DDL语句：\"" + sql_1 + "\", 影响了" + count_1 + "行");
                String sql_2 = "DROP DATABASE IF EXISTS `test`";
                int count_2 = stmt.executeUpdate(sql_2);
                System.out.println("执行DDL语句：\"" + sql_2 + "\", 影响了" + count_2 + "行");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn.close();
            System.out.println("数据库连接已关闭");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("----------testDDL()函数结束执行----------");
    }

    // 测试DQL语句 (Data Query Language)
    private static void testDQL() {
        System.out.println("----------testDQL()函数开始执行----------");
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("数据库连接成功");
            try (Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM `product`";
                ResultSet resultSet = stmt.executeQuery(sql);
                System.out.println("执行DQL语句：\"" + sql + "\"");
                System.out.println("查询结果如下：");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    System.out.println("id = " + id + ", name = " + name + ", price = " + price);
                }
                resultSet.close();  // 关闭结果集
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("数据库连接已关闭");
        System.out.println("----------testDQL()函数结束执行----------");
    }

    // 测试用JDBC生成POJO对象 (Plain Ordinary Java Object)
    private static void testPOJO() {
        System.out.println("----------testPOJO()函数开始执行----------");
        ArrayList<Product> products = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("数据库连接成功");
            try (Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM `product`";
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    Product p = new Product();
                    p.setId(resultSet.getInt("id"));
                    p.setName(resultSet.getString("name"));
                    p.setPrice(resultSet.getDouble("price"));
                    products.add(p);
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Product p : products)  // 遍历打印集合中的元素
            System.out.println(p);
        System.out.println("----------testPOJO()函数结束执行----------");
    }

    // 测试SQL注入
    private static void testInject() {
        System.out.println("----------testInject()函数开始执行----------");
        String u_name = "someRanChar";
        String u_password = "someRanChar";
        String inject = "' OR '1' = '1";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("数据库连接成功");
            try (Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM tb_user WHERE username = '" + u_name
                        + "' AND password = '" + u_password + "'";
                System.out.println("执行SQL语句：" + sql);
                if (stmt.executeQuery(sql).next())
                    System.out.println("登录成功！");
                else
                    System.out.println("登录失败！");

                u_password += inject;   // 尝试注入
                String sql_injected = "SELECT * FROM tb_user WHERE username = '" + u_name
                        + "' AND password = '" + u_password + "'";
                System.out.println("执行SQL语句：" + sql_injected);
                if (stmt.executeQuery(sql_injected).next())
                    System.out.println("登录成功！");
                else
                    System.out.println("登录失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("----------testInject()函数结束执行----------");
    }

    // 测试预编译语句 (用途：防止SQL注入、预编译提高性能)
    private static void testPrepStmt() {
        System.out.println("----------testPreparedStatement()函数开始执行----------");
        String u_name = "someRanChar";
        String u_password = "someRanChar" + "' OR '1' = '1";    // 尝试注入
        try (Connection conn = DriverManager.getConnection(url + "?useServerPrepStmts=true",
                username, password)) {  // 添加参数，开启数据库预编译功能
            System.out.println("数据库连接成功");
            String sql = "SELECT * FROM tb_user WHERE username = ? AND password = ?"; // 预编译语句, ?为占位符
            try (PreparedStatement prep_stmt = conn.prepareStatement(sql)) {
                prep_stmt.setString(1, u_name);     // 按索引设置用户名 (索引从1开始)
                prep_stmt.setString(2, u_password); // 按索引设置密码
                ResultSet resultSet = prep_stmt.executeQuery(); // 执行查询（不需要传递SQL语句参数）
                if (resultSet.next())
                    System.out.println("登录成功！");
                else
                    System.out.println("登录失败！");
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("----------testPreparedStatement()函数结束执行----------");
    }

    // 测试数据库连接池（实现：Druid）
    private static void testDataSource() {
        System.out.println("----------testDataSource()函数开始执行----------");

        try {
            Properties druidProp = new Properties();    // 读取配置文件，创建配置对象
            druidProp.load(Files.newInputStream(Paths.get("src/druid.properties")));

            DataSource dataSource = DruidDataSourceFactory.createDataSource(druidProp); // 工厂模式通过配置对象创建数据源

            try (Connection conn = dataSource.getConnection()) {    // 从连接池中获取连接，避免频繁创建连接
                System.out.println("数据库连接成功");
                try (Statement stmt = conn.createStatement()) {
                    String sql = "SELECT * FROM `product`";
                    ResultSet resultSet = stmt.executeQuery(sql);
                    System.out.println("执行SQL语句：\"" + sql + "\"");
                    System.out.println("查询结果如下：");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        double price = resultSet.getDouble("price");
                        System.out.println("id = " + id + ", name = " + name + ", price = " + price);
                    }
                    resultSet.close();  // 关闭结果集
                } catch (SQLException e) {
                    if (e instanceof SQLTimeoutException)
                        System.err.println("查询超时！");
                    else
                        System.err.println("查询失败！");
                }
            } catch (SQLException e) {
                System.err.println("获取Druid连接池连接失败！");
            }
        } catch (IOException e) {
            System.err.println("读取Druid配置文件失败！");
        } catch (Exception e) {
            System.err.println("创建Druid连接池失败！");
        }


        System.out.println("----------testDataSource()函数结束执行----------");
    }
}
