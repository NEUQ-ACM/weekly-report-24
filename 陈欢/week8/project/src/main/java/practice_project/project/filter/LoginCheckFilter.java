package practice_project.project.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import practice_project.project.pojo.Result;
import practice_project.project.utils.JwtUtils;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoginCheckFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //获取url
        String url = req.getRequestURL().toString();
        log.info("获得url：{}",url);
        //判断请求中是否包含login，如果包含，说明是登录操作，放行
        if (url.contains("login")) {
            log.info("登录操作，放行！");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //获取请求头中的令牌
        String jwt = req.getHeader("token");
        //判断令牌是否存在，如果不存在，则返回错误信息
        if(!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象-->json   阿里巴巴fastJson
            String notLogin = JSON.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        //解析token，如果解析失败，返回错误结果
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象-->json   阿里巴巴fastJson
            String notLogin = JSON.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        //放行
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
