package codegym.tequila.fisioapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllersTimeLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllersTimeLogAspect.class);

    @Pointcut("within(codegym.tequila.fisioapp.controller.*)")
    public void allControllersMethods() {}


    @Around("allControllersMethods() || @annotation(codegym.tequila.fisioapp.aop.TimeLogAnotation)")
    public Object logTime(ProceedingJoinPoint jp) throws Throwable {

        long startTime = System.currentTimeMillis();

        logger.info("Inicia la ejecución del método <{}.{}, ARGS: {}>.", jp.getTarget().getClass().getSimpleName(), jp.getSignature().getName(), jp.getArgs());

        Object result = jp.proceed();

        logger.info("Finaliza la ejecución del método <{}.{}, {} ms>.", jp.getTarget().getClass().getSimpleName(), jp.getSignature().getName(), System.currentTimeMillis() - startTime);

        return result;
    }

    @AfterThrowing(pointcut = "allControllersMethods()  || @annotation(codegym.tequila.fisioapp.aop.TimeLogAnotation)", throwing = "exception")
    public void logFinalTimeWithException(JoinPoint jp, Exception exception) {

        logger.info("Finaliza <{}.{}> con exception <{}>.", jp.getTarget().getClass().getSimpleName(), jp.getSignature().getName(), exception.getMessage());
    }

}
