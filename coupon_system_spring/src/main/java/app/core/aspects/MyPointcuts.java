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
	
	@Pointcut("execution(* app.core.controllers.login.*.*(..))")
	public void loginController() {
		
	}
	
	@Pointcut("execution(* app.core.controllers.clients.*.*(..))")
	public void clientController() {
		
	}
	
	@Pointcut("execution(* app.core.services.*.*(..))")
	public void services() {
		
	}
	
	@Pointcut("services() || clientController()")
	public void servicesOrClientController() {
		
	}
}
