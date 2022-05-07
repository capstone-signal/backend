package com.hidiscuss.backend.config.interceptor;

import com.hidiscuss.backend.utils.GithubContext;
import org.kohsuke.github.GHRateLimit;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GithubInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        GithubContext.clear();
    }


    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response,
            Object obj, Exception e)
            throws Exception {
    }
}
