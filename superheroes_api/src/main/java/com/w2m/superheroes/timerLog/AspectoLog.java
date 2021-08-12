package com.w2m.superheroes.timerLog;

//import java.util.logging.FileHandler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectoLog {

	// Modificar el calificador único global de la anotación del temporizador
	@Pointcut("@annotation(LogExecutionTime)")
	private void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// Obtener el registrador de destino
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
//		Logger logger = Logger.getLogger(joinPoint.getTarget().getClass().getName());
//	    FileHandler fileHandler = new FileHandler("app.log", true);        
//	    logger.addHandler(fileHandler);

		// Obtener el nombre de la clase de destino
		String clazzName = joinPoint.getTarget().getClass().getName();

		// Obtener el nombre del método de la clase de destino
		String methodName = joinPoint.getSignature().getName();

		long start = System.currentTimeMillis();
		logger.info("{}: {}: start...", clazzName, methodName);
//		logger.info("Information message 111");
//		logger.info("{}: {}: start..." + clazzName + methodName);

		// llamar al método de destino
		Object result = joinPoint.proceed();

		long time = System.currentTimeMillis() - start;
//		logger.info("Information message");
//		logger.info("{}: {}: : end... cost time: {} ms"+ clazzName + methodName + time);
		logger.info("{}: {}: : end... cost time: {} ms", clazzName, methodName, time);

		return result;
	}
}
