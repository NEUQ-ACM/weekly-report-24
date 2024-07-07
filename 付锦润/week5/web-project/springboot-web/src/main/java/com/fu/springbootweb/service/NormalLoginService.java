package com.fu.springbootweb.service;

import com.fu.springbootweb.dao.NormalLoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class NormalLoginService implements LoginService {
    @Autowired
    private NormalLoginDao normalLoginDao;

    public String login(String account, String password) {
        int status = normalLoginDao.query(account, password);
        if (status == 1) {
            return "Wrong password";
        }
        if (status == 2) {
            normalLoginDao.create(account, password);
        }
        return "Login successful";
    }
}
