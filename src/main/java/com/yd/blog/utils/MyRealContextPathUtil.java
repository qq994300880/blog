package com.yd.blog.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YoungDream
 * @since 2019/3/6 14:50
 */
public class MyRealContextPathUtil {
    public static String get(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String requestURI = request.getRequestURI();
        return requestURL.delete(requestURL.indexOf(requestURI), requestURL.length()).append(request.getContextPath()).toString();
    }
}
