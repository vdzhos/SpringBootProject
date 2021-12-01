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

    @Around("execution(* com.sbproject.schedule.services.implementations.*.get*(*))")
    public Object getObjectByIdAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        logger.info(Markers.GET_METHOD_INVOKED_MARKER, "GET Method " + proceedingJoinPoint.getSignature() + " invoked with arguments: ");
        logger.info(Markers.GET_METHOD_INVOKED_MARKER,Arrays.toString(proceedingJoinPoint.getArgs()));
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        logger.info(Markers.GET_METHOD_INVOKED_MARKER,"The returned value is: ");
        logger.info(Markers.GET_METHOD_INVOKED_MARKER, (value == null) ? null : value.toString());
        return value;
    }

    @AfterReturning(returning = "returnObject", value = "execution(* com.sbproject.schedule.services.implementations.*.add*(..))")
    public void addSpecialtyAfterAdvice(JoinPoint joinPoint, Object returnObject){
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,"ADD Method "+joinPoint.getSignature() + " invoked with arguments: ");
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,Arrays.toString(joinPoint.getArgs()));
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,"The returned value is: ");
        logger.info(Markers.ADD_METHOD_INVOKED_MARKER,returnObject.toString());
    }






}
