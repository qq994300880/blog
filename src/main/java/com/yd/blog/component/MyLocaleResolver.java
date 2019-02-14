package com.yd.blog.component;

import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @Author YoungDream
 * @date 2019/2/8 23:45
 */
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String lang = request.getParameter("lang");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(lang)) {
            String[] split = lang.split("_");
            locale = new Locale(split[0], split[1]);
            session.setAttribute("l", lang);
        } else if (session.getAttribute("l") != null) {
            Object l = session.getAttribute("l");
            if (l instanceof String) {
                String[] split = ((String) l).split("_");
                locale = new Locale(split[0], split[1]);
            }
        }
//        Logger logger = LoggerFactory.getLogger(MyLocaleResolver.class);
//        logger.info(locale.getLanguage() + "-" + locale.getCountry());
        return locale;
    }

    //该实现不支持动态更改语言环境
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
