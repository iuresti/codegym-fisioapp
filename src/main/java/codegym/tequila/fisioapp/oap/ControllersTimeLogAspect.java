package codegym.tequila.fisioapp.oap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllersTimeLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllersTimeLogAspect.class);

    @Pointcut("within(codegym.tequila.fisioapp.controller.*)")
    public void allControllers() {}

    @Before("allControllers()")
    public void beforeAllControllers() {
        logger.info("Inicia la ejecución del método ------------------------.");
    }

    @AfterReturning(pointcut = "allControllers()", returning = "result")
    public void afterAllControllers(JoinPoint joinPoint, Object result) {
        logger.info("Finaliza la ejecución del método ----------------------.");
    }
}
