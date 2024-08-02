package practice_project.project.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
//@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {
    @Override//拦截到请求之后调用，调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Demo 拦截到了请求");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("Demo 放行后逻辑");
    }

    @Override//销毁方法，只调用一次
    public void destroy() {
        Filter.super.destroy();
        System.out.println("destroy 销毁方法执行完毕");
    }

    @Override//初始化方法，调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("init 初始化方法执行完毕");
    }
}
