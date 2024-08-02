package practice_project.project.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class AbcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Abc 放行前逻辑");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Abc 放行后逻辑");
    }
}
