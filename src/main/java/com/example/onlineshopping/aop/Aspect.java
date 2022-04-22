package com.example.onlineshopping.aop;


import com.example.onlineshopping.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configuration
@org.aspectj.lang.annotation.Aspect
@Slf4j
public class Aspect {

    @Around(value = "Pointcuts.allGetByIDMethods()")
    public Object aroundGetById(ProceedingJoinPoint joinPoint){
        Object result=null;
        HttpServletRequest valueParameter =(HttpServletRequest) getParameter(joinPoint,"request");
        log.info("Method: "+ joinPoint.getSignature().getName());
        log.info("Attempting get class object by id: {} ",joinPoint.getArgs());
        log.info( HttpUtils.getRequestDetails(valueParameter));
        try {
            long start = System.nanoTime();
            result=joinPoint.proceed();
            long end = System.nanoTime();
            log.info("Execution took " + TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
        } catch (Throwable e) {
            e.printStackTrace();
            log.info("Error while getting");
        }
        return result;
    }

    @AfterThrowing(value = "Pointcuts.allGetByIDMethods()")
    public void logExceptions(JoinPoint joinPoint) {
        HttpServletRequest valueParameter =(HttpServletRequest) getParameter(joinPoint,"request");
        log.info("Method: "+ joinPoint.getSignature().getName());
        log.info("Exception thrown in {} Method={}",joinPoint.getSignature().getName(),joinPoint.toString());
        log.info(HttpUtils.getRequestDetails(valueParameter));
    }

    @Before(value = "Pointcuts.allGetAllMethods()")
    public void beforeGetAll(JoinPoint joinPoint){
        log.info("Method: "+ joinPoint.getSignature().getName());
        log.info("Attempting get all data class {}  ",joinPoint.getClass().getName());
        HttpServletRequest valueParameter =(HttpServletRequest) getParameter(joinPoint,"request");
        log.info( HttpUtils.getRequestDetails(valueParameter));
    }

    @AfterReturning(value="Pointcuts.allGetAllMethods()",returning="returnSize")
    public void returnSize(JoinPoint joinPoint,int returnSize){
        log.info("{} method executed. Returned list size: {}",joinPoint.getSignature().getName(),returnSize);
    }
    @After(value = "Pointcuts.allSaveMethods()")
    public void afterSave(JoinPoint joinPoint){
        HttpServletRequest valueParameter =(HttpServletRequest) getParameter(joinPoint,"request");
        log.info("Running After Advice for {} method. Passed arguments :{}",joinPoint.getSignature().getName(),joinPoint.getArgs());
        log.info(HttpUtils.getRequestDetails(valueParameter));
    }
//    @Around(value = "Pointcuts.allGetControllerMethods()")
//    public Object aroundGetAll(ProceedingJoinPoint joinPoint){
//        Object result=null;
//        HttpServletRequest valueParameter =(HttpServletRequest) getParameter(joinPoint,"request");
//        log.info("Method: "+ joinPoint.getSignature().getName());
//        log.info("Attempting get class {} objects {} ",joinPoint.getClass().getName(),joinPoint.getArgs());
//        log.info("Ip address: {}", HttpUtils.getRequestIP(valueParameter));
//        try {
//            long start = System.nanoTime();
//            result=joinPoint.proceed();
//            long end = System.nanoTime();
//            log.info("Execution took " + TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
//        } catch (Throwable e) {
//            e.printStackTrace();
//            log.info("Error while getting");
//        }
//        return result;
//    }


    static Object getParameter(JoinPoint joinPoint,String parameterName){
        Object valueParameter = null;
        if (Objects.nonNull(joinPoint) && joinPoint.getSignature() instanceof MethodSignature
                && Objects.nonNull(parameterName) ) {
            MethodSignature method = (MethodSignature)joinPoint.getSignature();
            String[] parameters = method.getParameterNames();
            for (int t = 0; t< parameters.length; t++) {
                if( Objects.nonNull(parameters[t]) && parameters[t].equals(parameterName)) {
                    Object[] obj = joinPoint.getArgs();
                    valueParameter = obj[t];
                }
            }
        }return valueParameter;
    }
}
