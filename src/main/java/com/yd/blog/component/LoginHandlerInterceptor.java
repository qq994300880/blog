package com.yd.blog.component;

import com.yd.blog.bean.Admin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author YoungDream
 * @date 2019/2/17 2:00
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object admin = request.getSession().getAttribute("admin");
        if (admin instanceof Admin) {
            if (((Admin) admin).getUsername().equals("root")) {
                return true;
            }
        }
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
