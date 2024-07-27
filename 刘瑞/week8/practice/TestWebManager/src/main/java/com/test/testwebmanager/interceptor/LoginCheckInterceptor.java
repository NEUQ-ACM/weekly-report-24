package com.test.testwebmanager.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.test.testwebmanager.pojo.Result;
import com.test.testwebmanager.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("interceptor begin");
        String requestURI = request.getRequestURI();
        if(requestURI.contains("/login")){
            log.info("登录操作，放行");
            return true;
        }
        String jwt = request.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空");
            Result error = Result.error("NOT_LOGIN");
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return false;
        }
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失效，返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return false;
        }
        log.info("令牌合法");
        return true;
        //return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
