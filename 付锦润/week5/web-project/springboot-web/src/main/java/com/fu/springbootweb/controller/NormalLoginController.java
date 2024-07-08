package com.fu.springbootweb.controller;

import com.fu.springbootweb.Response;
import com.fu.springbootweb.service.NormalLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class NormalLoginController implements LoginController {
    @Autowired
    private NormalLoginService normalLoginService;

    @RequestMapping("/login")
    public Response login(String account, String password) {
        return Response.success(normalLoginService.login(account, password));
    }
}
