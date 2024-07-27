package com.test.testwebmanager.filter;

import com.alibaba.fastjson.JSONObject;
import com.test.testwebmanager.pojo.Result;
import com.test.testwebmanager.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if(requestURI.contains("/login")){
            log.info("登录操作，放行");
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = request.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空");
            Result error = Result.error("NOT_LOGIN");
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return;
        }
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失效，返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return;
        }
        log.info("令牌合法");
        filterChain.doFilter(request, response);
    }
}
