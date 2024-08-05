package practice_project.project.interceptor;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import practice_project.project.pojo.Result;
import practice_project.project.utils.JwtUtils;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor{
    @Override//目标资源方法运行前运行，返回true：放行，返回false：不放行
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        System.out.println("start preHandle");
        //获取请求头中的令牌
        String jwt = req.getHeader("token");
        //判断令牌是否存在，如果不存在，则返回错误信息
        if(!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象-->json   阿里巴巴fastJson
            String notLogin = JSON.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
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
            return false;
        }
        //放行
        log.info("令牌合法，放行");
        return true;
    }

    @Override//目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override//试图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
