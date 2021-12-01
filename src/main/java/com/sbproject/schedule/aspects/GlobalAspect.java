package com.sbproject.schedule.aspects;

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

    @AfterReturning(returning = "returnObject", value = "execution(* com.sbproject.schedule.services.implementations.*.get*(*))")
    public void getObjectAfterAdvice(JoinPoint joinPoint, Object returnObject){
        logger.info(Markers.GET_METHOD_INVOKED_MARKER, "GET Method " + joinPoint.getSignature() + " invoked with arguments: "
                + Arrays.toString(joinPoint.getArgs())
                +". The returned value is: " +
                returnObject);
    }

    @AfterReturning(returning = "returnObject", value = "execution(* com.sbproject.schedule.services.implementations.*.add*(..))")
    public void addObjectAfterAdvice(JoinPoint joinPoint, Object returnObject){
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,"ADD Method "+joinPoint.getSignature() + " invoked with arguments: ");
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,Arrays.toString(joinPoint.getArgs()));
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,"The returned value is: ");
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,returnObject.toString());
    }

    @AfterReturning(returning = "returnObject", value = "execution(* com.sbproject.schedule.services.implementations.*.update*(..))")
    public void updateObjectAfterAdvice(JoinPoint joinPoint, Object returnObject){
        logger.info(Markers.UPDATE_METHOD_INVOKED_MARKER, "UPDATE Method " + joinPoint.getSignature() + " invoked with arguments: "
                + Arrays.toString(joinPoint.getArgs())
                +". The returned value is: " +
                returnObject.toString());
    }
    @AfterReturning(returning = "returnObject", value = "execution(* com.sbproject.schedule.services.implementations.*.delete*(*))")
    public void deleteObjectByIdAfterAdvice(JoinPoint joinPoint, Object returnObject){
        logger.info(Markers.DELETE_METHOD_INVOKED_MARKER, "DELETE Method " + joinPoint.getSignature() + " invoked with arguments: "
                + Arrays.toString(joinPoint.getArgs())
                +". The returned value is: " +
                returnObject);
    }

}
