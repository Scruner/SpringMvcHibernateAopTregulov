package ru.vdv.tregulov.mvc_hibernate_aop.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyLoggingAspect {
    //в скобках говорится: этот Advice будет распространятся на метод с любым access modifier(*), на метод
// с любым return type(*), методы классов из пакета ru.vdv.tregulov.mvc_hibernate_aop.dao, на все классы этого
// пакета(*), на все методы всех классов(*) и пусть этот Advice работает с любым методом параметров(..)
    @Around("execution(* ru.vdv.tregulov.mvc_hibernate_aop.dao.*.*(..))")
    public Object aroundAllRepositoryMethodsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//чтобы узнать имя метода, мы можем использовать proceedingJoinPoint
        MethodSignature methodSignature =
                (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName();

        System.out.println("Begin of " + methodName);
//когда мы используем аннотацию Around, внутри эдвайса мы сами должны запустить метод, к которому
// относится этот эдвайс. Делаем мы это опять при помощи proceedingJoinPoint и метода proceed(). В
//targetMethodResult получим результат этого метода
        Object targetMethodResult = proceedingJoinPoint.proceed();

        System.out.println("End of " + methodName);
//и возвращаем этот результат
        return targetMethodResult;
    }
}
