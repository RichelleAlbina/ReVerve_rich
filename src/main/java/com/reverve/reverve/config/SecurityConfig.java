package com.reverve.reverve.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
                
                // Allow public endpoints
                String uri = request.getRequestURI();
                if (uri.equals("/") || 
                    uri.equals("/login") || 
                    uri.equals("/register") ||
                    uri.startsWith("/static/") ||
                    uri.startsWith("/css/") ||
                    uri.startsWith("/js/") ||
                    uri.startsWith("/images/")) {
                    return true;
                }
                
                // Check authentication for all other endpoints
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    response.sendRedirect("/login?redirect=" + uri);
                    return false;
                }
                return true;
            }
        }).addPathPatterns("/**");
    }
}