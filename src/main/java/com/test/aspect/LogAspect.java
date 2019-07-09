package com.test.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		final long start = System.currentTimeMillis();

		final Object proceed = joinPoint.proceed();

		final long executionTime = System.currentTimeMillis() - start;

		logger.info("Tiempo total de proceso de {}: {} ms", joinPoint.getSignature(), executionTime);

		return proceed;
	}

	@Before("execution(* com.test.controller.*.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("Inicio de ejecucion de metodo en controller: {}", joinPoint.toString());
		logger.info("Argumentos: {}", Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(pointcut = "execution(* com.test.controller.*.*(..))", returning = "val")
	public void logAfterReturning(Object val) {
		logger.info("Respuesta: {}", val);
	}

}