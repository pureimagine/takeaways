package com.takeaways.takeaways.filter;

import com.alibaba.fastjson2.JSON;
import com.takeaways.takeaways.common.R;
import com.takeaways.takeaways.entity.Employee;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@Slf4j
@WebFilter(filterName="LoginCheckFilter",urlPatterns="/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER =new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
//        过滤器具体的处理逻辑如下
//        1、获取本次请求的URI
        String requestURI = httprequest.getRequestURI();
        log.info("拦截到的请求是{}",requestURI);
        String[] checks=new String[]{"/employee/login","/employee/loggout","/backend/**","/front/**"};
//        2、判断本次请求是否需要处理
        Boolean check = check(requestURI, checks);
//        3、如果不需要处理，则直接放行
        if (check)
        {
            log.info("本次请求不需要处理{}",requestURI);
            chain.doFilter(request,response);
            return;
        }
//        4、判断登录状态，如果已登录，则直接放行
        if(httprequest.getSession().getAttribute("employee")!=null){
            log.info("本次已经登录，ID是",((HttpServletRequest) request).getSession().getAttribute("employee"));
            chain.doFilter(request,response);
            return;
        }
        chain.doFilter(request,response);

//        5、如果未登录则返回未登录结果
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    public Boolean check(String URL,String[] checks)
    {
        for (String check : checks) {
            boolean match = PATH_MATCHER.match(check, URL);
            if (match)
                return true;
        }
        return false;
    }
}
