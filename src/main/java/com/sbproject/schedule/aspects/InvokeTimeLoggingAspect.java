package com.sbproject.schedule.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.sbproject.schedule.utils.Markers;

@Component
@Aspect
public class InvokeTimeLoggingAspect {

	private static Logger logger = LogManager.getLogger(GlobalAspect.class);
	
	@Around("execution(* com.sbproject.schedule.services.implementations.*.*(..))")
	public Object logInvokeTimeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
	{
		StopWatch clock = new StopWatch(proceedingJoinPoint.toString());
        try
        {
            clock.start(proceedingJoinPoint.toShortString());
            return proceedingJoinPoint.proceed();
        } 
        finally 
        {
            clock.stop();
            logger.info(Markers.METHOD_INVOKE_TIME_MARKER, proceedingJoinPoint.getSignature() + "\t- Elapsed Time: " + clock.getTotalTimeMillis() + " ms");
        }
	}
	
}
