package com.yd.blog.config;

import com.yd.blog.component.LoginHandlerInterceptor;
import com.yd.blog.component.MyLocaleResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author YoungDream
 * @date 2019/2/8 21:48
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport {

    //配置视图控制器
//    @Override
//    protected void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("forward:/blogs");
//        registry.addViewController("/blogs.html").setViewName("forward:/blogs");
//    }

    //配置资源文件路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico");
        //静态资源文件
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //使用webjars
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        //使用绝对路径文件夹
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/upload/");
    }

    //设置区域解析器
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        ArrayList<String> excludePath = new ArrayList<>(10);
//        excludePath.add("/index/**");
//        excludePath.add("/blog/**");
//        excludePath.add("/login");
//        excludePath.add("/logout");
//        excludePath.add("/static/**");
//        excludePath.add("/webjars/**");
//        excludePath.add("/upload/**");
//        //拦截所有请求，SpringBoot2.0之后需要自己手动排除静态资源文件的拦截
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns(excludePath);
        //简单一点就拦截manager下的所有请求
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/manager/**");
    }

    //添加hiddenHttpMethodFilter,方法一
    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilterFilterRegistrationBean() {
        FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HiddenHttpMethodFilter());
        registrationBean.setName("myHidHttpMethodFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}