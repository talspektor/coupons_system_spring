package app.core.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author taltalspektor
 *
 */
@Component
@Aspect
public class LoggerAspect {
	
	// ******* Login ************

	@Before("app.core.aspects.MyPointcuts.loginController()")
	public void beforeLogin(JoinPoint joinPoint) {
		System.out.println("======= " + joinPoint.getSignature().getName() + " ========");
		for (Object object : joinPoint.getArgs()) {
			System.out.println("=== " + object.toString());
		}
		System.out.println("========================");
	}
	
	@AfterReturning(pointcut = "app.core.aspects.MyPointcuts.loginController()", returning = "token")
	public void loginSuccess(JoinPoint joinPoint, String token) {
		if (token != null) {
			System.out.println("===== Login success :)");
			System.out.println("======= token: " + token);
		}		
	}
	
	@AfterThrowing(pointcut = "app.core.aspects.MyPointcuts.loginController()", throwing = "t")
	public void loginFailed() {
		System.out.println("====== Login failed :(");
	}
	
	// ********* Services and Controllers and SessionContext
	
	@Before("app.core.aspects.MyPointcuts.servicesOrClientControllerOrSessionContextOrDailyJob()")
	public void controllerAndServiseLog(JoinPoint joinPoint) {
		logClassAndMethod(joinPoint);
	}
	
	@AfterReturning(pointcut = "app.core.aspects.MyPointcuts.servicesOrClientControllerOrSessionContext()", returning = "obj")
	public void serviceSucces(JoinPoint joinPoint, Object obj) {
		if (obj != null) {
			System.out.println("======= Success: " + obj.toString());
		}
	}
	
	@AfterThrowing(pointcut = "app.core.aspects.MyPointcuts.servicesOrClientControllerOrSessionContext()", throwing = "t")
	public void sevicesFailed() {
		System.out.println("======= Failed");
	}
	
	// ********** Private Method ***********
	
	private void logClassAndMethod(JoinPoint joinPoint) {
		System.out.println("========  " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " - " + joinPoint.getSignature().getName());
	}
	
}
