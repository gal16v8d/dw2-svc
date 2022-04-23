package com.gsdd.dw2.config;

import com.gsdd.dw2.Dw2Application;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RequestLogger {

    @Around("execution(* " + Dw2Application.BASE_PACKAGE + "controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object o = null;
        String nombreJP = joinPoint.getSignature().getName();
        long startTime = System.nanoTime();
        log.info("Before: {}", nombreJP);
        try {
            o = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("After: {}", nombreJP);
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info(
                    "Execution of {} tooks {} ms",
                    nombreJP,
                    TimeUnit.MILLISECONDS.convert(timeTaken, TimeUnit.NANOSECONDS));
        }
        return o;
    }
}
