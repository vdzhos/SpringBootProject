package com.sbproject.schedule.aspects;

import com.sbproject.schedule.controllers.LessonController;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.utils.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class GlobalAspect {


    private static Logger logger = LogManager.getLogger(GlobalAspect.class);


//    //Example
//    @Around("execution(* com.sbproject.schedule.services.implementations.*.get*(*))")
//    public Object getObjectByIdAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
//        System.out.println("Before invoking getObject(Long id) method");
//        Object value = null;
//        try {
//            value = proceedingJoinPoint.proceed();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        System.out.println("After invoking getObject(Long id) method. Return value= " + value);
//        return value;
//    }


    @AfterReturning(returning = "returnObject", value = "execution(* com.sbproject.schedule.services.implementations.*.add*(..))")
    public void addSpecialtyAfterAdvice(JoinPoint joinPoint, Object returnObject){
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,"ADD Method "
                +joinPoint.getSignature() + " invoked with arguments: "
                + Arrays.toString(joinPoint.getArgs())
                +" The returned value is: "
                +returnObject.toString());
    }






}
