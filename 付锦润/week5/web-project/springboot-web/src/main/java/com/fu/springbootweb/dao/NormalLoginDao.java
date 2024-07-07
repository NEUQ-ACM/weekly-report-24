package com.fu.springbootweb.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Primary
@Repository
public class NormalLoginDao implements LoginDao {
    // 不使用数据库，将密码存储在本地文件中
    private Properties properties = new Properties();
    String dbPath = "src/main/resources/db.properties";

    public NormalLoginDao() {
        File file = new File(dbPath);
        if (!file.exists()) {
            Path path = Paths.get(dbPath);
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("数据库文件创建失败，错误信息如下：");
                e.printStackTrace();
            }
        }
    }

    public void updateProperties() {
        try (FileInputStream in = new FileInputStream(dbPath)) {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("打开数据库文件失败，错误信息如下：");
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(String account, String password) {
        updateProperties();
        try (FileOutputStream out = new FileOutputStream(dbPath)) {
            properties.setProperty(account, password);
            properties.store(out, "Create account");
        } catch (IOException e) {
            System.out.println("打开数据库文件失败，错误信息如下：");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String account) {
        return false;
    }

    @Override
    public boolean update(String account, String password) {
        return false;
    }

    @Override
    public int query(String account, String password) {
        // 0：完全匹配
        // 1：密码错误
        // 2：不存在该账户
        updateProperties();
        if (!properties.contains(account)) {
            return 2;
        }
        if (!properties.getProperty(account).equals(password)) {
            return 1;
        }
        return 0;
    }
}
