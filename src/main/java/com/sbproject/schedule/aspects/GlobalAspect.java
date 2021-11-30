package com.sbproject.schedule.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class GlobalAspect {

    //Example
    @Around("execution(* com.sbproject.schedule.services.implementations.*.get*(*))")
    public Object getObjectByIdAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("Before invoking getObject(Long id) method");
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("After invoking getObject(Long id) method. Return value= " + value);
        return value;
    }

}
