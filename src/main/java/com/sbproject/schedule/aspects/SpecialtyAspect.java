package com.sbproject.schedule.aspects;


import com.sbproject.schedule.models.Specialty;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SpecialtyAspect {


    @Before(value = "args(name,year) && execution(public * addSpecialty(..)) && within(com.sbproject.schedule.services.implementations.SpecialtyServiceImpl)", argNames = "joinPoint,name,year")
    public void addSpecialtyBeforeAdvice( JoinPoint joinPoint, String name, int year){
        System.out.println("ALERT: "+joinPoint.getSignature() +" invoked!!!");
        System.out.println("Arguments: "+name+", "+year);
    }

    @AfterReturning(returning = "returnObject", argNames = "joinPoint,returnObject,name,year", value = "args(name,year) && execution(public * addSpecialty(..)) && within(com.sbproject.schedule.services.implementations.SpecialtyServiceImpl)")
    public void addSpecialtyAfterAdvice(JoinPoint joinPoint, Specialty returnObject, String name, int year){
        System.out.println("ALERT: "+joinPoint.getSignature() +" after invoked!!!");
        System.out.println("Returned object = "+returnObject);
    }



//    @Before("execution(com.sbproject.schedule.services.interfaces.SpecialtyService.addSpecialty())")
//    public void addSpecialtyAdvice(){
//        System.out.println("ALERT: doSome invoked!!!");
//    }




}
