package com.yd.blog.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;

/**
 * @author YoungDream
 * @since 2019/3/5 19:38
 */
@Slf4j
@Aspect
@Component
public class RequestHandlerAopLog {
    //以 controller 包下定义的所有请求为切入点
    @Pointcut("execution(public * com.yd.blog.controller..*.*(..))")
    public void aopPointcut() {
    }

    //前置通知
    @Before("aopPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        //获取Request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        log.info("Request Args   : {}", joinPoint.getArgs());
    }

    //环绕通知
    @Around("aopPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Instant startTime = Instant.now();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        log.info("Response Args  : {}", result);
        // 执行耗时
        Instant endTime = Instant.now();
        log.info("Time-Consuming : {} ms", Duration.between(startTime, endTime).toMillis());
        return result;
    }

    @After("aopPointcut()")
    public void doAfter() {
        log.info("=========================================== End ===========================================");
        // 每个请求之间空一行
        log.info("");
    }
}
