package com.yan.movielens.aspect;

import com.yan.movielens.annotation.Time;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class TimeAspect {

    @Pointcut("@annotation(com.yan.movielens.annotation.Time)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Time annotation = method.getAnnotation(Time.class);
        System.out.println("准备");
    }

    @After("annotationPointcut()")
    public void afterPointcut(JoinPoint joinPoint) {

        System.out.println("结束");
    }

}
