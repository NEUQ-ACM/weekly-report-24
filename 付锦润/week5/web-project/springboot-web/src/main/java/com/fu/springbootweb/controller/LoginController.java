package com.fu.springbootweb.controller;

import com.fu.springbootweb.Response;

abstract public interface LoginController {
    abstract public Response login(String username, String password);
}
