package app.core.aspects;

import org.aspectj.lang.annotation.Pointcut;


public class MyPointcuts {

	@Pointcut("execution(* get*(..))")
	public void getter() {
		
	}
	
	@Pointcut("execution(* delete*(..))")
	public void delete() {
		
	}
	
	@Pointcut("execution(* add*(..))")
	public void add() {
		
	}
	
	@Pointcut("execution(* update*(..))")
	public void update() {
		
	}
	
	@Pointcut("add() && update() && delete() && getter()")
	public void action() {
		
	}
	
	@Pointcut("action() && services()")
	public void servicesActions() {
		
	}
	
	@Pointcut("execution(public * app.core.controllers.login.*.*(..))")
	public void loginController() {
		
	}
	
	@Pointcut("execution(public * app.core.controllers.clients.*.*(..))")
	public void clientController() {
		
	}
	
	@Pointcut("execution(public * app.core.services.*.*(..))")
	public void services() {
		
	}
	
	@Pointcut("services() || clientController() || sessionContext()")
	public void servicesOrClientControllerOrSessionContext() {
		
	}
	
	@Pointcut("services() || clientController() || sessionContext() || dailyJob()")
	public void servicesOrClientControllerOrSessionContextOrDailyJob() {
		
	}
	
	@Pointcut("execution(public * app.core.session.SessionContext.*(..))")
	public void sessionContext() {
		
	}
	
	@Pointcut("execution(public * app.core.job.DailyJob.*(..))")
	public void dailyJob() {
		
	}
}
