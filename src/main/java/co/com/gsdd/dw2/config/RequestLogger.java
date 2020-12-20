package co.com.gsdd.dw2.config;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.Dw2Application;
import lombok.extern.slf4j.Slf4j;

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
		o = joinPoint.proceed();
		log.info("After: {}", nombreJP);
		long timeTaken = System.nanoTime() - startTime;
		log.info("request {} took {} ms", nombreJP, TimeUnit.MILLISECONDS.convert(timeTaken, TimeUnit.NANOSECONDS));
		return o;
	}
	
}
