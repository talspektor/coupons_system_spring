package app.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.session.Session;
import app.core.session.SessionContext;

@Component
@Aspect
public class TokenAspect {
	
	@Autowired
	SessionContext sessionContext;

	@Before("execution(* app.core.controllers.clients.*.*(..))")
	public void checkToken(JoinPoint jp) {
		Object [] jpObjects = jp.getArgs();
		for (Object jpObject : jpObjects) {
			System.out.println("arg: " + jpObject.toString());
		}
		Session session = sessionContext.getSession(jpObjects[jpObjects.length-1].toString());
		if (session == null) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "You need to login!");
		}
	}
}
